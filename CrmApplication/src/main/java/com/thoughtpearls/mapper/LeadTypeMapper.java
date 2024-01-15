package com.thoughtpearls.mapper;

import com.thoughtpearls.dto.requestdto.LeadTypeRequestDto;
import com.thoughtpearls.dto.responsedto.LeadTypeResponseDto;
import com.thoughtpearls.model.LeadType;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface LeadTypeMapper {
    LeadType dtoToEntity(LeadTypeRequestDto leadTypeRequestDto);
    LeadTypeResponseDto entityToDto(LeadType leadType);
}
