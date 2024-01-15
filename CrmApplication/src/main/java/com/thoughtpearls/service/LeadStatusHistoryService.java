package com.thoughtpearls.service;

import com.thoughtpearls.dto.requestdto.LeadRequestDto;
import com.thoughtpearls.dto.responsedto.LeadStatusHistoryResponseDto;
import com.thoughtpearls.model.Lead;
import com.thoughtpearls.dto.SimplePage;
import com.thoughtpearls.model.User;

import java.time.LocalDateTime;

public interface LeadStatusHistoryService {

    public void createLeadStatusHistory(String previousStatus, String updatedStatus, User user, Lead lead);
    public SimplePage<LeadStatusHistoryResponseDto> getAllHistoryByLeadId(Long leadId, Integer pageNo, Integer pageSize, String sortBy);
}
