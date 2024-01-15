package com.thoughtpearls.servicetest;

import static org.junit.Assert.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.lenient;
import static org.mockito.Mockito.verify;

import java.time.LocalDateTime;
import java.util.List;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;

import com.thoughtpearls.dto.SimplePage;
import com.thoughtpearls.dto.responsedto.LeadStatusHistoryResponseDto;
import com.thoughtpearls.mapper.LeadStatusHistoryMapper;
import com.thoughtpearls.model.Lead;
import com.thoughtpearls.model.LeadStatusHistory;
import com.thoughtpearls.model.User;
import com.thoughtpearls.repository.LeadStatusHistoryRepository;
import com.thoughtpearls.service.impl.LeadStatusHistoryServiceImpl;

@ExtendWith(MockitoExtension.class)
public class LeadStatusHistoryServiceTest {
	@Mock
    private LeadStatusHistoryRepository leadStatusHistoryRepository;
	
	@InjectMocks
    private LeadStatusHistoryServiceImpl leadStatusHistoryService;
	
	@Mock
    private LeadStatusHistoryMapper leadStatusHistoryMapper;

	
	@Test
    void testGetAllHistoryByLeadId() {
        Long leadId = 1L;
        Integer pageNo = 0;
        Integer pageSize = 10;
        String sortBy = "id";
        
        Pageable pageable = PageRequest.of(pageNo, pageSize, Sort.by(sortBy).descending());
        
        LeadStatusHistory leadStatusHistory = new LeadStatusHistory();
        leadStatusHistory.setId(1L);
        leadStatusHistory.setPreviousStatus("LINKEDIN");
        leadStatusHistory.setUpdatedStatus("UPGRADE");
        leadStatusHistory.setDateTime(LocalDateTime.now());
        Lead lead = new Lead();
        lead.setId(1L);
        leadStatusHistory.setLead(lead);
        User user = new User();
        user.setId(1L);
        leadStatusHistory.setUser(user);
        
        List<LeadStatusHistory> expectedList = List.of(leadStatusHistory);
        Page<LeadStatusHistory> pagedResult = new PageImpl<>(expectedList,pageable,pageable.getPageSize());
        
        lenient().when(leadStatusHistoryRepository.findByLeadId(leadId, pageable)).thenReturn(pagedResult);
        lenient().when(leadStatusHistoryMapper.entityToDto(eq(leadStatusHistory))).thenReturn(new LeadStatusHistoryResponseDto()); 

        SimplePage<LeadStatusHistoryResponseDto> actualPage = leadStatusHistoryService.getAllHistoryByLeadId(leadId, pageNo, pageSize, sortBy);
        assertNotNull(actualPage);
        
        verify(leadStatusHistoryRepository).findByLeadId(leadId,pageable);
	}
	
	
}
