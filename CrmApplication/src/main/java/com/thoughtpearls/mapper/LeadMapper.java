package com.thoughtpearls.mapper;

import com.thoughtpearls.dto.requestdto.LeadRequestDto;
import com.thoughtpearls.dto.responsedto.LeadResponseDto;
import com.thoughtpearls.model.Lead;
import com.thoughtpearls.repository.LeadRepository;
import com.thoughtpearls.repository.LeadTypeRepository;
import com.thoughtpearls.repository.StatusRepository;
import org.mapstruct.*;


@Mapper(componentModel = "spring",uses = {LeadRepository.class, LeadTypeRepository.class, StatusRepository.class})
public interface LeadMapper {

    @Mapping(source="leadDescription",target="description")
    @Mapping(source="reminderDate",target="reminderDate",dateFormat = "yyyy-MMM-dd")
    @Mapping(target = "status", source = "statusId", ignore = true)
    @Mapping(target = "leadType", source = "leadTypeId", ignore = true)
    Lead dtoToEntity(LeadRequestDto leadRequestDto);

    @Mapping(source="description",target="leadDescription")
    @Mapping(source="reminderDate",target="reminderDate",dateFormat = "yyyy-MMM-dd")
    @Mapping(source="createdBy",target="createdBy" ,ignore = true)
    @Mapping(source="updatedBy",target="updatedBy" ,ignore = true)
    LeadResponseDto entityToDto(Lead lead);

    @Mapping(source = "leadDescription", target = "description")
    @Mapping(source = "reminderDate", target = "reminderDate", dateFormat = "yyyy-MMM-dd")
    Lead updateEntityFromDto(LeadRequestDto leadRequestDto, @MappingTarget Lead lead);

}
