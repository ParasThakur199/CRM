package com.thoughtpearls.service;

import com.thoughtpearls.dto.LeadRequestDto;
import com.thoughtpearls.dto.LeadResponseDto;
import com.thoughtpearls.dto.SearchParametersDto;
import com.thoughtpearls.model.Lead;
import org.springframework.data.domain.Page;

import java.util.List;

public interface LeadService {
    public LeadResponseDto createLead(LeadRequestDto leadRequestDto, String token);

    public LeadResponseDto updateLead(long leadId, LeadRequestDto leadRequestDto,String token);

    public void deleteLead(long leadId);

    public Lead findLeadById(long leadId);

    public List<LeadResponseDto> getAllLeads(Integer pageNo, Integer pageSize, String sortBy);

    public Page<LeadResponseDto> searchAndFilterInLead(final SearchParametersDto searchParametersDto);

    public LeadResponseDto getLeadById(long leadId);
}
