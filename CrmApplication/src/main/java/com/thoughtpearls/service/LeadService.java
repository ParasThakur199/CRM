package com.thoughtpearls.service;

import com.thoughtpearls.dto.LeadRequestDto;
import com.thoughtpearls.dto.LeadResponseDto;
import com.thoughtpearls.enums.LeadType;
import com.thoughtpearls.enums.Status;
import com.thoughtpearls.mapper.LeadMapper;
import com.thoughtpearls.model.Lead;
import com.thoughtpearls.repository.LeadRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
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

    public List<LeadResponseDto> findLeadsWithFiltering(String leadName, Status leadStatus, LeadType leadType){
        Specification<Lead> specification = Specification.where(null);
        if(leadName!=null && !leadName.isEmpty()){
            specification = specification.and((root,query,builder)->
                    builder.like(builder.lower(root.get("leadName")),"%"+leadName.toLowerCase()+"%"));
        }
        if(leadStatus!=null){
            specification = specification.and((root,query,builder)->
                    builder.equal(root.get("status"), leadStatus));
        }
        if(leadType!=null){
            specification = specification.and((root,query,builder)->
                    builder.equal(root.get("leadType"), leadType));
        }
        List<Lead> leads = leadRepository.findAll(specification);
        return leadMapper.entityToDto(leads);
    }
    public List<LeadResponseDto> sortLeads(Integer pageNo, Integer pageSize, String sortBy){
        Pageable paging= PageRequest.of(pageNo, pageSize, Sort.by(sortBy));
        Page<Lead> pagedResult=leadRepository.findAll(paging);

        if(pagedResult.hasContent()) {
            return leadMapper.entityToDto(pagedResult.getContent());
        } else {
            return new ArrayList<LeadResponseDto>();
        }
    }
}
