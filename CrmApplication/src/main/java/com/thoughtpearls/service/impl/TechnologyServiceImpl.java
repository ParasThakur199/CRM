package com.thoughtpearls.service.impl;

import com.thoughtpearls.dto.SimplePage;
import com.thoughtpearls.dto.requestdto.TechnologyRequestDto;
import com.thoughtpearls.dto.responsedto.TechnologyResponseDto;
import com.thoughtpearls.mapper.TechnologyMapper;
import com.thoughtpearls.model.Technology;
import com.thoughtpearls.repository.TechnologyRepository;
import com.thoughtpearls.service.TechnologyService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

@Service
@Slf4j
public class TechnologyServiceImpl implements TechnologyService {

    @Autowired
    private TechnologyRepository technologyRepository;

    @Autowired
    private TechnologyMapper technologyMapper;

    @Override
    public TechnologyResponseDto createTechnology(TechnologyRequestDto technologyRequestDto) {
        Technology technology = technologyMapper.dtoToEntity(technologyRequestDto);
        return technologyMapper.entityToDto(technologyRepository.save(technology));
    }

    @Override
    public SimplePage<TechnologyResponseDto> findAllTechnology(Integer pageNo, Integer pageSize, String sortBy) {
        Pageable pageable = PageRequest.of(pageNo, pageSize, Sort.by(sortBy).descending());
        Page<Technology> pagedResult = technologyRepository.findAll(pageable);
        SimplePage<TechnologyResponseDto> simplePage = new SimplePage<>(pagedResult.stream()
                .map(technologyMapper::entityToDto)
                .filter(Objects::nonNull)
                .collect(Collectors.toList()), pagedResult.getTotalElements(), pageable);
        return simplePage;
    }

    @Override
    public List<Technology> findAllByIdIn(List<Long> technologiesId) {
        return technologyRepository.findAllByIdIn(technologiesId);
    }
}
