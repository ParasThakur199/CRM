package com.thoughtpearls.mapper;

import com.thoughtpearls.dto.requestdto.TechnologyRequestDto;
import com.thoughtpearls.dto.responsedto.TechnologyResponseDto;
import com.thoughtpearls.model.Technology;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface TechnologyMapper {
    Technology dtoToEntity(TechnologyRequestDto technologyRequestDto);

    TechnologyResponseDto entityToDto(Technology technology);
}
