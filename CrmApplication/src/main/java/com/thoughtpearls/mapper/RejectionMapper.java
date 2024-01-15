package com.thoughtpearls.mapper;
        
import com.thoughtpearls.dto.requestdto.RejectionRequestDto;
import com.thoughtpearls.dto.responsedto.RejectionResponseDto;
import com.thoughtpearls.model.Rejection;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface RejectionMapper {
    @Mapping(source = "technologies",target = "technologies",ignore = true)
    Rejection dtoToEntity(RejectionRequestDto rejectionRequestDto);
    RejectionResponseDto entityToDto(Rejection rejection);
}