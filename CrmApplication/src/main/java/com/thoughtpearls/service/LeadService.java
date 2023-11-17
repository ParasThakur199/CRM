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
//    public List<LeadResponseDto> sortLeads(Integer pageNo, Integer pageSize, String sortBy){
//        Pageable paging= PageRequest.of(pageNo, pageSize, Sort.by(sortBy));
//        Page<Lead> pagedResult=leadRepository.findAll(paging);
//    public List<LeadResponseDto> findLeadsWithFiltering(String leadName, Status leadStatus, LeadType leadType){
//        Specification<Lead> specification = Specification.where(null);
//        if(leadName!=null && !leadName.isEmpty()){
//            specification = specification.and((root,query,builder)->
//                    builder.like(builder.lower(root.get("leadName")),"%"+leadName.toLowerCase()+"%"));
//        }
//        if(leadStatus!=null){
//            specification = specification.and((root,query,builder)->
//                    builder.equal(root.get("status"), leadStatus));
//        }
//        if(leadType!=null){
//            specification=specification.and((root,query,builder)->
//                    builder.equal(root.get("leadType"), leadType));
//        }
//        List<Lead> leads = leadRepository.findAll(specification);
//        return leadMapper.entityToDto(leads);
//    }
//    public List<LeadResponseDto> sortLeads(Integer pageNo, Integer pageSize, String sortBy){
//        Pageable paging= PageRequest.of(pageNo, pageSize, Sort.by(sortBy));
//        Page<Lead> pagedResult=leadRepository.findAll(paging);
//
//        if(pagedResult.hasContent()) {
//            return leadMapper.entityToDto(pagedResult.getContent());
//        } else {
//            return new ArrayList<LeadResponseDto>();
//        }
//    }

//        public List<LeadResponseDto> findAndSortLeads(String input, Integer pageNo, Integer pageSize, String sortBy) {
//            try{
//            Specification<Lead> specification = Specification.where(null);
//
//            if (input != null && !input.isEmpty()) {
//                if (!isEnumValueValid(LeadType.class, input)) {
//                    throw new IllegalArgumentException("Invalid input for leadType: " + input);
//                }
//
//                specification = specification.and((root, query, builder) ->
////                        builder.and(
//                        builder.or(
//                                builder.like(builder.lower(root.get("leadName")), "%" + input.toLowerCase() + "%"),
//                                builder.like(builder.lower(root.get("description")),  "%" + input.toLowerCase() + "%"),
//                                builder.equal(root.get("id"),  Long.parseLong(input)),
//                                builder.like(builder.lower(root.get("reminderTopic")),  "%" + input.toLowerCase() + "%"),
//                                builder.equal(root.get("leadType"), LeadType.valueOf(input))
//                        )
//
////                                        builder.equal(root.get("id"), Long.parseLong(input))
////                                builder.like(builder.lower(root.get("leadType")),LeadType.valueOf(input) + "%")
////                                builder.equal(root.get("leadType"), inputLeadType)
////                        )
//
//                );
//            }
//
////            else if (input != null) {
////                specification = specification.and((root, query, builder) ->
////                        builder.equal(root.get("leadType"), input));
////            }
//            Pageable paging = PageRequest.of(pageNo, pageSize, Sort.by(sortBy));
//            Page<Lead> pagedResult = leadRepository.findAll(specification, paging);
//
//            if (pagedResult.hasContent()) {
//                return leadMapper.entityToDto(pagedResult.getContent());
//            } else {
//                return new ArrayList<>();
//            }
//        }catch(NumberFormatException e){
//            throw new RuntimeException("Invalid input for leadID :"+input);
//            }
//    }

//    private <E extends Enum<E>> boolean isEnumValueValid(Class<E> enumClass, String value) {
//        try {
//            Enum.valueOf(enumClass, value.toUpperCase());
//            return true;
//        } catch (IllegalArgumentException ex) {
//            return false;
//        }
//    }

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
            if (searchParametersDto.getSortOrder().equalsIgnoreCase(String.valueOf(ASC))) {
                sort = Sort.by(searchParametersDto.getSortBy()).ascending().and(Sort.by("reminderDate")).descending();
            } else {
                sort = Sort.by(searchParametersDto.getSortBy()).descending().and(Sort.by("reminderDate")).descending();
            }

            Specification<Lead> spec = LeadSpecification.getSearchSpecification(searchParametersDto);
            Page<Lead> leadsPage = leadRepository.findAll(spec, PageRequest.of(searchParametersDto.getPageNo(), searchParametersDto.getPageSize(), sort));
            List<Lead> leads = leadsPage.getContent();
            List<LeadResponseDto> leadResponseDtos = leads.parallelStream()
                    .map(lead -> leadMapper.entityToDto(lead))
                    .collect(Collectors.toList());

            return new PageImpl<>(leadResponseDtos, PageRequest.of(searchParametersDto.getPageNo(), searchParametersDto.getPageSize(), sort), leadsPage.getTotalElements());
        } catch (NumberFormatException e) {
            throw new RuntimeException("Invalid input for lead parameters", e);
        }
    }
}
