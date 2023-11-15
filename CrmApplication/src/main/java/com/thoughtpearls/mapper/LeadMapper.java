package com.thoughtpearls.mapper;

import com.thoughtpearls.dto.LeadRequestDto;
import com.thoughtpearls.dto.LeadResponseDto;
import com.thoughtpearls.model.Lead;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface LeadMapper {
    @Mapping(source="leadDescription",target="description")
    @Mapping(source="leadStatus",target="status")
    Lead dtoToEntity(LeadRequestDto leadRequestDto);
    @Mapping(source="description",target="leadDescription")
    @Mapping(source="status",target="leadStatus")
    LeadResponseDto entityToDto(Lead lead);
}
