package com.thoughtpearls.service;


import com.thoughtpearls.dto.SimplePage;
import com.thoughtpearls.dto.requestdto.LeadTypeRequestDto;
import com.thoughtpearls.dto.responsedto.LeadTypeResponseDto;
import com.thoughtpearls.model.LeadType;

public interface LeadTypeService {
    LeadTypeResponseDto createLeadType(LeadTypeRequestDto leadTypeRequestDto);

    SimplePage<LeadTypeResponseDto> getAllLeadTypes(Integer pageNo, Integer pageSize, String sortBy);

    void deleteLeadType(Long id);

    LeadType findById(Long leadTypeId);
}
