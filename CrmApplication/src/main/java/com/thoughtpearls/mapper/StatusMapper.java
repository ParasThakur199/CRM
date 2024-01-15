package com.thoughtpearls.mapper;

import com.thoughtpearls.dto.requestdto.StatusRequestDto;
import com.thoughtpearls.dto.responsedto.StatusResponseDto;
import com.thoughtpearls.model.Status;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface StatusMapper {
    Status dtoToEntity(StatusRequestDto statusRequestDto);
    StatusResponseDto entityToDto(Status status);
}
