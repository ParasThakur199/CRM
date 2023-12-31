package com.thoughtpearls.mapper;

import com.thoughtpearls.dto.LeadRequestDto;
import com.thoughtpearls.dto.LeadResponseDto;
import com.thoughtpearls.model.Lead;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;

import java.util.List;

@Mapper(componentModel = "spring")
public interface LeadMapper {
    @Mapping(source="leadDescription",target="description")
    @Mapping(source="leadStatus",target="status")
    @Mapping(source="reminderDate",target="reminderDate",dateFormat = "yyyy-MMM-dd")
    Lead dtoToEntity(LeadRequestDto leadRequestDto);
    @Mapping(source="description",target="leadDescription")
    @Mapping(source="status",target="leadStatus")
    @Mapping(source="reminderDate",target="reminderDate",dateFormat = "yyyy-MMM-dd")
    LeadResponseDto entityToDto(Lead lead);

    @Mapping(source = "leadDescription", target = "description")
    @Mapping(source = "leadStatus", target = "status")
    @Mapping(source = "reminderDate", target = "reminderDate", dateFormat = "yyyy-MMM-dd")
    Lead updateEntityFromDto(LeadRequestDto leadRequestDto, @MappingTarget Lead lead);

    List<LeadResponseDto> entityToDto(List<Lead> leads);

    List<Lead> dtoToEntity(List<LeadResponseDto> leadResponseDtos);

}
