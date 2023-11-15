package com.thoughtpearls.mapper;

import com.thoughtpearls.dto.CommentsRequestDto;
import com.thoughtpearls.dto.CommentsResponseDto;
import com.thoughtpearls.model.Comments;
import org.mapstruct.Mapper;
import org.mapstruct.MappingTarget;

@Mapper(componentModel = "spring")
public interface CommentsMapper {

    Comments dtoToEntity(CommentsRequestDto commentsRequestDto);

    CommentsResponseDto entityToDto(Comments comments);

    void updateEntityFromDto(CommentsRequestDto commentsRequestDto, @MappingTarget Comments comments);
}
