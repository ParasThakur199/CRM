package com.thoughtpearls.service.impl;

import com.thoughtpearls.dto.SimplePage;
import com.thoughtpearls.dto.requestdto.StatusRequestDto;
import com.thoughtpearls.dto.responsedto.StatusResponseDto;
import com.thoughtpearls.exception.ExceptionMessage;
import com.thoughtpearls.exception.StatusNotFoundException;
import com.thoughtpearls.mapper.StatusMapper;
import com.thoughtpearls.model.Status;
import com.thoughtpearls.repository.StatusRepository;
import com.thoughtpearls.service.StatusService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.Objects;
import java.util.stream.Collectors;

@Service
@Slf4j
public class StatusServiceImpl implements StatusService {

    @Autowired
    private StatusRepository statusRepository;

    @Autowired
    private StatusMapper statusMapper;

    @Override
    public StatusResponseDto createStatus(StatusRequestDto statusRequestDto) {
        Status status = statusMapper.dtoToEntity(statusRequestDto);
        return statusMapper.entityToDto(statusRepository.save(status));
    }

    @Override
    public SimplePage<StatusResponseDto> getAllStatus(Integer pageNo, Integer pageSize, String sortBy) {
        Pageable pageable = PageRequest.of(pageNo, pageSize, Sort.by(sortBy).descending());
        Page<Status> pagedResult = statusRepository.findAll(pageable);
        return new SimplePage<>(pagedResult.getContent().stream()
                .map(status -> statusMapper.entityToDto(status)).filter(Objects::nonNull)
                .collect(Collectors.toList()), pagedResult.getTotalElements(), pageable);
    }

    @Override
    public void deleteStatus(Long id) {
        Status status = findById(id);
        statusRepository.delete(status);
    }

    @Override
    public Status findById(Long statusId) {
        return statusRepository.findById(statusId).orElseThrow(() -> new StatusNotFoundException(ExceptionMessage.statusNotFound));
    }
}
