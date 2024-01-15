package com.thoughtpearls.mapper;

import com.thoughtpearls.dto.requestdto.CommentRequestDto;
import com.thoughtpearls.dto.responsedto.CommentResponseDto;
import com.thoughtpearls.model.Comment;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface CommentMapper {


    Comment dtoToEntity(CommentRequestDto commentRequestDto);

    @Mapping(source = "createdBy", target = "createdBy", ignore = true)
    CommentResponseDto entityToDto(Comment comment);
}
