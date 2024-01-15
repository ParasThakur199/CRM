package com.thoughtpearls.service.impl;

import com.thoughtpearls.config.JwtService;
import com.thoughtpearls.dto.SimplePage;
import com.thoughtpearls.dto.requestdto.LeadRequestDto;
import com.thoughtpearls.dto.requestdto.SearchParameterRequestDto;
import com.thoughtpearls.dto.responsedto.LeadResponseDto;
import com.thoughtpearls.exception.ExceptionMessage;
import com.thoughtpearls.exception.LeadNotFoundException;
import com.thoughtpearls.mapper.LeadMapper;
import com.thoughtpearls.mapper.UserMapper;
import com.thoughtpearls.model.Lead;
import com.thoughtpearls.model.LeadType;
import com.thoughtpearls.model.Status;
import com.thoughtpearls.model.User;
import com.thoughtpearls.repository.LeadRepository;
import com.thoughtpearls.service.*;
import com.thoughtpearls.specification.LeadSpecification;
import jakarta.transaction.Transactional;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.*;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

import static org.springframework.data.domain.Sort.Direction.ASC;

@Service
@Slf4j
public class LeadServiceImpl implements LeadService {

    @Autowired
    private LeadRepository leadRepository;

    @Autowired
    private LeadMapper leadMapper;

    @Autowired
    private JwtService jwtService;

    @Autowired
    private UserService userService;

    @Autowired
    private UserMapper userMapper;

    @Autowired
    private LeadStatusHistoryService leadStatusHistoryService;

    @Autowired
    private StatusService statusService;

    @Autowired
    private LeadTypeService leadTypeService;

    @Transactional
    public LeadResponseDto createLead(LeadRequestDto leadRequestDto, String token) {
        try {
            User user = jwtService.getUserDetailsFromToken(token);
            Lead lead = leadMapper.dtoToEntity(leadRequestDto);
            Status status = statusService.findById(leadRequestDto.getStatusId());
            LeadType leadType = leadTypeService.findById(leadRequestDto.getLeadTypeId());
            lead.setStatus(status);
            lead.setLeadType(leadType);
            lead.setCreatedBy(user.getId());
            lead.setCreatedOn(LocalDateTime.now());
            lead.setUser(user);
            Lead savedLead = leadRepository.save(lead);
            leadStatusHistoryService.createLeadStatusHistory(null, status.getCode(), user, savedLead);
            LeadResponseDto responseDto = leadMapper.entityToDto(savedLead);
            responseDto.setCreatedBy(userMapper.entityToDto(user));
            log.info("Lead created successfully with Lead ID: {}", responseDto.getId());
            return responseDto;
        } catch (Exception e) {
            log.error("Failed to create lead", e);
            throw e;
        }
    }


    public SimplePage<LeadResponseDto> getAllLeads(Integer pageNo, Integer pageSize, String sortBy) {
        Pageable pageable = PageRequest.of(pageNo, pageSize, Sort.by(sortBy).descending());
        Page<Lead> pagedResult = leadRepository.findAll(pageable);
        return new SimplePage<>(pagedResult.stream()
                .map(lead -> {
                    LeadResponseDto responseDto = leadMapper.entityToDto(lead);
                    responseDto.setCreatedBy(userMapper.entityToDto(lead.getUser()));
                    if (lead.getUpdatedBy() != null) {
                        setUpdatedBy(lead.getUpdatedBy(), responseDto);
                    }
                    return responseDto;
                })
                .collect(Collectors.toList()), pagedResult.getTotalElements(), pageable);
    }

    public LeadResponseDto getLeadById(Long leadId) {
        Lead lead = findById(leadId);
        LeadResponseDto responseDto = leadMapper.entityToDto(lead);
        responseDto.setCreatedBy(userMapper.entityToDto(lead.getUser()));
        if (lead.getUpdatedBy() != null) {
            setUpdatedBy(lead.getUpdatedBy(), responseDto);
        }
        return responseDto;
    }

    public LeadResponseDto updateLead(Long leadId, LeadRequestDto leadRequestDto, String token) {
        User user = jwtService.getUserDetailsFromToken(token);
        Lead lead = findById(leadId);
        String previousStatus = lead.getStatus().getCode();
        leadMapper.updateEntityFromDto(leadRequestDto, lead);
        lead.setUpdatedOn(LocalDateTime.now());
        lead.setUpdatedBy(user.getId());
        Status status = statusService.findById(leadRequestDto.getStatusId());
        LeadType leadType = leadTypeService.findById(leadRequestDto.getLeadTypeId());
        lead.setLeadType(leadType);
        lead.setStatus(status);
        Lead updatedLead = leadRepository.save(lead);
        if (!previousStatus.equals(status.getCode())) {
            leadStatusHistoryService.createLeadStatusHistory(previousStatus, status.getCode(), user, lead);
        }
        LeadResponseDto responseDto = leadMapper.entityToDto(updatedLead);
        responseDto.setCreatedBy(userMapper.entityToDto(lead.getUser()));
        responseDto.setUpdatedBy(userMapper.entityToDto(user));
        return responseDto;
    }

    public void deleteLead(Long leadId) {
        leadRepository.delete(findById(leadId));
    }

    public Page<LeadResponseDto> searchAndFilterInLead(SearchParameterRequestDto searchParameterRequestDto, String token) {
        Sort sort = Sort.by(
                Sort.Order.desc("user.id").ignoreCase().nullsLast(),
                searchParameterRequestDto.getSortOrder().equalsIgnoreCase(String.valueOf(ASC)) ?
                        Sort.Order.asc(searchParameterRequestDto.getSortBy()) :
                        Sort.Order.desc(searchParameterRequestDto.getSortBy())
        ).and(Sort.by(Sort.Order.desc("createdOn")));
        Specification<Lead> spec = LeadSpecification.getSearchSpecification(searchParameterRequestDto);
        Pageable pageable = PageRequest.of(searchParameterRequestDto.getPageNo(), searchParameterRequestDto.getPageSize(), sort);
        Page<Lead> leadsPage = leadRepository.findAll(spec, pageable);
        List<LeadResponseDto> leadResponseDtos = leadsPage.getContent().parallelStream()
                .map(lead -> {
                    LeadResponseDto responseDto = leadMapper.entityToDto(lead);
                    responseDto.setCreatedBy(userMapper.entityToDto(lead.getUser()));
                    if (lead.getUpdatedBy() != null) {
                        setUpdatedBy(lead.getUpdatedBy(), responseDto);
                    }
                    return responseDto;
                })
                .collect(Collectors.toList());
        return new PageImpl<>(leadResponseDtos, PageRequest.of(searchParameterRequestDto.getPageNo(), searchParameterRequestDto.getPageSize(), sort), leadsPage.getTotalElements());
    }

    @Override
    public Lead findById(Long id) {
        return leadRepository.findById(id).orElseThrow(() -> new LeadNotFoundException(ExceptionMessage.leadNotFound));
    }

    @Override
    public void existById(Long id) {
        if (!leadRepository.existsById(id)) {
            throw new LeadNotFoundException(ExceptionMessage.leadNotFound);
        }
    }

    public void setUpdatedBy(Long id, LeadResponseDto responseDto) {
        responseDto.setUpdatedBy(userService.findById(id)
                .map(userMapper::entityToDto)
                .orElseThrow(() -> new UsernameNotFoundException(ExceptionMessage.userNotFound)));
    }
}
