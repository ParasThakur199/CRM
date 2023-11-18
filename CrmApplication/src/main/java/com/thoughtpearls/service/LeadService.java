package com.thoughtpearls.service;

import com.thoughtpearls.dto.LeadRequestDto;
import com.thoughtpearls.dto.LeadResponseDto;
import com.thoughtpearls.dto.SearchParametersDto;
import com.thoughtpearls.enums.LeadType;
import com.thoughtpearls.enums.Status;
import com.thoughtpearls.mapper.LeadMapper;
import com.thoughtpearls.model.Lead;
import com.thoughtpearls.repository.LeadRepository;
import com.thoughtpearls.specification.LeadSpecification;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.*;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import static org.springframework.data.domain.Sort.Direction.ASC;

@Service
public class LeadService {

    @Autowired
    private LeadRepository leadRepository;

    @Autowired
    private LeadMapper leadMapper;

    public LeadResponseDto createLead(LeadRequestDto leadRequestDto) {
        Lead lead = leadMapper.dtoToEntity(leadRequestDto);
        Lead savedLead = leadRepository.save(lead);
        return leadMapper.entityToDto(savedLead);
    }

    public LeadResponseDto updateLead(long leadId, LeadRequestDto leadRequestDto) {
        return leadRepository.findById(leadId)
                .map(lead -> {
                    leadMapper.updateEntityFromDto(leadRequestDto, lead);
                    Lead updatedLead = leadRepository.save(lead);
                    return leadMapper.entityToDto(updatedLead);
                })
                .orElse(null);
    }

    public void deleteLead(long leadId) {
        leadRepository.deleteById(leadId);
    }

    public Lead findLeadById(long leadId){
        Optional<Lead> optionalLead =leadRepository.findById(leadId);
        return optionalLead.orElseThrow(()->new RuntimeException("Lead not present"));
    }

public List<LeadResponseDto> getAllLeads(Integer pageNo, Integer pageSize, String sortBy){
    Pageable paging= PageRequest.of(pageNo, pageSize, Sort.by(sortBy));
    Page<Lead> pagedResult=leadRepository.findAll(paging);

    if(pagedResult.hasContent()) {
        return leadMapper.entityToDto(pagedResult.getContent());
    } else {
        return new ArrayList<LeadResponseDto>();
    }
}

    public Page<LeadResponseDto> searchAndFilterInLead(final SearchParametersDto searchParametersDto) {
        try {
            Sort sort;
            sort = Sort.by(
                    searchParametersDto.getSortOrder().equalsIgnoreCase(String.valueOf(ASC)) ?
                            Sort.Order.asc(searchParametersDto.getSortBy()) :
                            Sort.Order.desc(searchParametersDto.getSortBy())
            ).and(Sort.by(Sort.Order.desc("reminderDate")));

            Specification<Lead> spec = LeadSpecification.getSearchSpecification(searchParametersDto);
            Pageable pageable=PageRequest.of(searchParametersDto.getPageNo(), searchParametersDto.getPageSize(),sort);
            Page<Lead> leadsPage = leadRepository.findAll(spec, pageable);
            List<Lead> leads = leadsPage.getContent();
            List<LeadResponseDto> leadResponseDtos = leads.parallelStream()
                    .map(lead -> leadMapper.entityToDto(lead))
                    .collect(Collectors.toList());

            return new PageImpl<>(leadResponseDtos, PageRequest.of(searchParametersDto.getPageNo(), searchParametersDto.getPageSize(), sort), leadsPage.getTotalElements());
        } catch (NumberFormatException e) {
            throw new RuntimeException("Invalid input for lead parameters", e);
        }
    }

    public LeadResponseDto getLeadById(long leadId) {
        return leadRepository.findById(leadId)
                .map(leadMapper::entityToDto)
                .orElseThrow(() -> new RuntimeException("Lead not found with id: " + leadId));
    }
}
