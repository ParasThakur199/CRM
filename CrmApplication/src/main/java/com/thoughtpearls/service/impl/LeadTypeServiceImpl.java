package com.thoughtpearls.service.impl;

import com.thoughtpearls.dto.SimplePage;
import com.thoughtpearls.dto.requestdto.LeadTypeRequestDto;
import com.thoughtpearls.dto.responsedto.LeadTypeResponseDto;
import com.thoughtpearls.exception.ExceptionMessage;
import com.thoughtpearls.exception.LeadTypeNotFoundException;
import com.thoughtpearls.mapper.LeadTypeMapper;
import com.thoughtpearls.model.LeadType;
import com.thoughtpearls.repository.LeadTypeRepository;
import com.thoughtpearls.service.LeadTypeService;
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
public class LeadTypeServiceImpl implements LeadTypeService {

    @Autowired
    private LeadTypeRepository leadTypeRepository;

    @Autowired
    private LeadTypeMapper leadTypeMapper;

    @Override
    public LeadTypeResponseDto createLeadType(LeadTypeRequestDto leadTypeRequestDto) {
        LeadType leadType = leadTypeMapper.dtoToEntity(leadTypeRequestDto);
        return leadTypeMapper.entityToDto(leadTypeRepository.save(leadType));
    }

    @Override
    public SimplePage<LeadTypeResponseDto> getAllLeadTypes(Integer pageNo, Integer pageSize, String sortBy) {
        Pageable pageable = PageRequest.of(pageNo, pageSize, Sort.by(sortBy).descending());
        Page<LeadType> pagedResult = leadTypeRepository.findAll(pageable);
        return new SimplePage<>(
                pagedResult.getContent().stream()
                        .map(leadType -> leadTypeMapper.entityToDto(leadType))
                        .filter(Objects::nonNull)
                        .collect(Collectors.toList()), pagedResult.getTotalElements(), pageable);
    }

    @Override
    public void deleteLeadType(Long id) {
        leadTypeRepository.delete(findById(id));
    }

    @Override
    public LeadType findById(Long leadTypeId) {
        return leadTypeRepository.findById(leadTypeId).orElseThrow(
                () -> new LeadTypeNotFoundException(ExceptionMessage.leadTypeNotFound));
    }
}
