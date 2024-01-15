package com.thoughtpearls.servicetest;

import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import com.thoughtpearls.dto.requestdto.RejectionRequestDto;
import com.thoughtpearls.dto.responsedto.RejectionResponseDto;
import com.thoughtpearls.mapper.RejectionMapper;
import com.thoughtpearls.model.Lead;
import com.thoughtpearls.model.Rejection;
import com.thoughtpearls.model.Technology;
import com.thoughtpearls.repository.RejectionRepository;
import com.thoughtpearls.service.LeadService;
import com.thoughtpearls.service.TechnologyService;
import com.thoughtpearls.service.impl.RejectionServiceImpl;

@ExtendWith(MockitoExtension.class)
public class RejectionServiceTest {
	
	@Mock
    private LeadService leadService;
	
	@Mock
	private RejectionRepository rejectionRepository;
	
	@Mock
	private RejectionMapper rejectionMapper;
	
	@Mock
    private TechnologyService technologyService;

    @InjectMocks
    private RejectionServiceImpl rejectionService;
    
    @Test
    void testCreateRejection() {
    	Long leadId = 1L;
    	RejectionRequestDto requestDto = new RejectionRequestDto();
    	requestDto.setReason("Test Reason");
        List<Long> technologiesId = new ArrayList<>();
        technologiesId.add(1L);
        technologiesId.add(2L);
        requestDto.setTechnologies(technologiesId);

        Lead lead = new Lead();
        lead.setId(leadId);
        
        List<Technology> technologiesEntities = new ArrayList<>();
        Technology tech1 = new Technology();
        tech1.setId(1L);
        technologiesEntities.add(tech1);
        
        when(leadService.findById(leadId)).thenReturn(lead);
        when(technologyService.findAllByIdIn(technologiesId)).thenReturn(technologiesEntities);

        Rejection rejection = new Rejection();
        rejection.setId(1L);
        rejection.setDate(LocalDate.now());
        rejection.setLead(lead);
        rejection.setTechnologies(technologiesEntities);

        when(rejectionMapper.dtoToEntity(requestDto)).thenReturn(rejection);
        when(rejectionRepository.save(rejection)).thenReturn(rejection);
        
        RejectionResponseDto expectedResponseDto = new RejectionResponseDto();
        expectedResponseDto.setId(1L);
        when(rejectionMapper.entityToDto(rejection)).thenReturn(expectedResponseDto);
        
        RejectionResponseDto responseDto = rejectionService.createRejection(requestDto, leadId);
        
        verify(leadService, times(1)).findById(leadId);
        verify(technologyService, times(1)).findAllByIdIn(technologiesId);
        verify(rejectionMapper, times(1)).dtoToEntity(requestDto);
        verify(rejectionRepository, times(1)).save(rejection);
        verify(rejectionMapper, times(1)).entityToDto(rejection);
    }

}
