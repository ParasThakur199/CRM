package com.thoughtpearls.service.impl;

import com.thoughtpearls.dto.requestdto.RejectionRequestDto;
import com.thoughtpearls.dto.responsedto.RejectionResponseDto;
import com.thoughtpearls.mapper.RejectionMapper;
import com.thoughtpearls.model.Lead;
import com.thoughtpearls.model.Rejection;
import com.thoughtpearls.model.Technology;
import com.thoughtpearls.repository.RejectionRepository;
import com.thoughtpearls.service.LeadService;
import com.thoughtpearls.service.RejectionService;
import com.thoughtpearls.service.TechnologyService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;

@Service
@Slf4j
public class RejectionServiceImpl implements RejectionService {

    @Autowired
    private LeadService leadService;

    @Autowired
    private RejectionRepository rejectionRepository;

    @Autowired
    private RejectionMapper rejectionMapper;

    @Autowired
    private TechnologyService technologyService;

    @Override
    public RejectionResponseDto createRejection(RejectionRequestDto rejectionRequestDto, Long leadId) {
        List<Long> technologiesId = rejectionRequestDto.getTechnologies();
        List<Technology> technologiesEntities = technologyService.findAllByIdIn(technologiesId);
        Lead lead = leadService.findById(leadId);
        Rejection rejection = rejectionMapper.dtoToEntity(rejectionRequestDto);
        rejection.setLead(lead);
        rejection.setDate(LocalDate.now());
        rejection.setTechnologies(technologiesEntities);
        rejectionRepository.save(rejection);
        return (rejectionMapper.entityToDto(rejection));
    }
}
