package com.thoughtpearls.service.Impl;

import com.thoughtpearls.config.JwtService;
import com.thoughtpearls.dto.LeadRequestDto;
import com.thoughtpearls.dto.LeadResponseDto;
import com.thoughtpearls.dto.SearchParametersDto;
import com.thoughtpearls.exception.LeadNotFoundException;
import com.thoughtpearls.exception.TokenException;
import com.thoughtpearls.mapper.LeadMapper;
import com.thoughtpearls.model.Lead;
import com.thoughtpearls.model.User;
import com.thoughtpearls.repository.LeadRepository;
import com.thoughtpearls.sendemail.EmailService;
import com.thoughtpearls.service.LeadService;
import com.thoughtpearls.specification.LeadSpecification;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.*;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import static org.springframework.data.domain.Sort.Direction.ASC;

@Service
public class LeadServiceImpl implements LeadService {

    @Autowired
    private LeadRepository leadRepository;

    @Autowired
    private LeadMapper leadMapper;

    @Autowired
    private JwtService jwtService;

    public LeadResponseDto createLead(LeadRequestDto leadRequestDto,String token) {
        User user=jwtService.getUserDetailsFromToken(token);
        if(user==null){
            throw new TokenException("unable to retrieve user details from the provided token");
        }
        Lead lead = leadMapper.dtoToEntity(leadRequestDto);
        lead.setCreatedBy(user.getId());
        lead.setCreatedOn(LocalDateTime.now());
        lead.setUser(user);
        Lead savedLead = leadRepository.save(lead);
        return leadMapper.entityToDto(savedLead);
    }

    public LeadResponseDto updateLead(long leadId, LeadRequestDto leadRequestDto,String token) {
        User user=jwtService.getUserDetailsFromToken(token);
        if(user==null){
            throw new TokenException("unable to retrieve user details from the provided token");
        }
        return leadRepository.findById(leadId)
                .map(lead -> {
                    leadMapper.updateEntityFromDto(leadRequestDto, lead);
                    lead.setUpdatedOn(LocalDateTime.now());
                    lead.setUpdatedBy(user.getId());
                    Lead updatedLead = leadRepository.save(lead);
                    return leadMapper.entityToDto(updatedLead);
                })
                .orElseThrow(()->new LeadNotFoundException("Lead Not Found With Id"+leadId));
    }

    public void deleteLead(long leadId) {
        leadRepository.deleteById(leadId);
    }

    public Lead findLeadById(long leadId){
        Optional<Lead> optionalLead =leadRepository.findById(leadId);
        return optionalLead.orElseThrow(()->new LeadNotFoundException("Lead not present with Id"+leadId));
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
                .orElseThrow(() -> new LeadNotFoundException("Lead not found with id: " + leadId));
    }

    @Autowired
    private EmailService emailService;

    @Scheduled(cron = "0 0/1 * 1/1 * ?")
    public void sendReminderEmails() {
        Date currentDate = new Date();
        List<Lead> leadsWithUpcomingReminders = leadRepository.findByReminderDate(currentDate);
    System.out.println("executed");
        for (Lead lead : leadsWithUpcomingReminders) {
            String to = lead.getUser().getEmail(); // Assuming User has an email property
            String subject = "Reminder About your Lead: " + lead.getLeadName();
            String body = "Reminder Topic: " + lead.getReminderTopic();
            emailService.sendReminderEmail(to, subject, body);
        }
    }
}
