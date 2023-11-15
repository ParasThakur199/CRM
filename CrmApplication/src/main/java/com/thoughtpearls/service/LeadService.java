package com.thoughtpearls.service;

import com.thoughtpearls.dto.LeadRequestDto;
import com.thoughtpearls.dto.LeadResponseDto;
import com.thoughtpearls.mapper.LeadMapper;
import com.thoughtpearls.model.Lead;
import com.thoughtpearls.repository.LeadRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

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

    public List<LeadResponseDto> getAllLeads(){
        List<Lead> leads = leadRepository.findAll();
        return leadMapper.entityToDto(leads);
    }

    public void deleteLead(long leadId) {
        leadRepository.deleteById(leadId);
    }

    public Lead findLeadById(long leadId){
        Optional<Lead> optionalLead =leadRepository.findById(leadId);
        return optionalLead.orElseThrow(()->new RuntimeException("Lead not present"));
    }
}
