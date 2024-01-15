package com.thoughtpearls.service;

import com.thoughtpearls.dto.SimplePage;
import com.thoughtpearls.dto.requestdto.StatusRequestDto;
import com.thoughtpearls.dto.responsedto.StatusResponseDto;
import com.thoughtpearls.model.Status;

public interface StatusService {
    StatusResponseDto createStatus(StatusRequestDto statusRequestDto);

    SimplePage<StatusResponseDto> getAllStatus(Integer pageNo, Integer pageSize, String sortBy);

    void deleteStatus(Long id);
    Status findById(Long statusId);
}
