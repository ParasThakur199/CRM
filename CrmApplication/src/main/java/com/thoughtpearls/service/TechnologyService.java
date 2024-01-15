package com.thoughtpearls.service;

import com.thoughtpearls.dto.SimplePage;
import com.thoughtpearls.dto.requestdto.TechnologyRequestDto;
import com.thoughtpearls.dto.responsedto.TechnologyResponseDto;
import com.thoughtpearls.model.Technology;

import java.util.List;

public interface TechnologyService {
    TechnologyResponseDto createTechnology(TechnologyRequestDto technologyRequestDto);

    SimplePage<TechnologyResponseDto> findAllTechnology(Integer pageNo, Integer pageSize, String sortBy);

    List<Technology> findAllByIdIn(List<Long> technologiesId);

//    List<Technology> findAllById(List<Long> technologiesId);
}