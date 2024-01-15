package com.thoughtpearls.service;

import com.thoughtpearls.dto.requestdto.RejectionRequestDto;
import com.thoughtpearls.dto.responsedto.RejectionResponseDto;

public interface RejectionService {

    RejectionResponseDto createRejection(RejectionRequestDto rejectionRequestDto, Long leadId);
}
