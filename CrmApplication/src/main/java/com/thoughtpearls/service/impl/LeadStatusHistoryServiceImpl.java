package com.thoughtpearls.service.impl;

import com.thoughtpearls.dto.SimplePage;
import com.thoughtpearls.dto.responsedto.LeadStatusHistoryResponseDto;
import com.thoughtpearls.mapper.LeadStatusHistoryMapper;
import com.thoughtpearls.model.Lead;
import com.thoughtpearls.model.LeadStatusHistory;
import com.thoughtpearls.model.User;
import com.thoughtpearls.repository.LeadStatusHistoryRepository;
import com.thoughtpearls.service.LeadStatusHistoryService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.Objects;
import java.util.stream.Collectors;

@Service
@Slf4j
public class LeadStatusHistoryServiceImpl implements LeadStatusHistoryService {
    @Autowired
    private LeadStatusHistoryRepository leadStatusHistoryRepository;

    @Autowired
    private LeadStatusHistoryMapper leadStatusHistoryMapper;

    public void createLeadStatusHistory(String previousStatus, String updatedStatus, User user, Lead lead) {
        leadStatusHistoryRepository.save(
                LeadStatusHistory.builder()
                        .dateTime(LocalDateTime.now())
                        .previousStatus(previousStatus)
                        .updatedStatus(updatedStatus)
                        .user(user)
                        .lead(lead).build()
        );
    }

    @Override
    public SimplePage<LeadStatusHistoryResponseDto> getAllHistoryByLeadId(Long leadId, Integer pageNo, Integer pageSize, String sortBy) {
        Pageable pageable = PageRequest.of(pageNo, pageSize, Sort.by(sortBy).descending());
        Page<LeadStatusHistory> pagedResult = leadStatusHistoryRepository.findByLeadId(leadId, pageable);
        log.info("Found {} lead status history entries for lead ID: {}", pagedResult.getTotalElements(), leadId);
        return new SimplePage<>(pagedResult.getContent().stream()
                .map(leadStatusHistoryMapper::entityToDto)
                .filter(Objects::nonNull)
                .collect(Collectors.toList())
                , pagedResult.getTotalElements(), pageable);
    }
}
