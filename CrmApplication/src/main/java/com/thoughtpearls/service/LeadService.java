package com.thoughtpearls.service;

import com.thoughtpearls.dto.SimplePage;
import com.thoughtpearls.dto.requestdto.LeadRequestDto;
import com.thoughtpearls.dto.requestdto.SearchParameterRequestDto;
import com.thoughtpearls.dto.responsedto.LeadResponseDto;
import com.thoughtpearls.model.Lead;
import org.springframework.data.domain.Page;

public interface LeadService {
    public LeadResponseDto createLead(LeadRequestDto leadRequestDto, String token);

    public SimplePage<LeadResponseDto> getAllLeads(Integer pageNo, Integer pageSize, String sortBy);

    public LeadResponseDto getLeadById(Long leadId);

    public LeadResponseDto updateLead(Long leadId, LeadRequestDto leadRequestDto, String token);

    public void deleteLead(Long leadId);

    public Page<LeadResponseDto> searchAndFilterInLead(final SearchParameterRequestDto searchParameterRequestDto, String token);

    public Lead findById(Long id);

    public void existById(Long id);
}
