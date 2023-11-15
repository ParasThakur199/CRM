package com.thoughtpearls.mapper;

import com.thoughtpearls.dto.CommentsRequestDto;
import com.thoughtpearls.dto.CommentsResponseDto;
import com.thoughtpearls.dto.LeadResponseDto;
import com.thoughtpearls.model.Comments;
import com.thoughtpearls.model.Lead;
import org.mapstruct.Mapper;
import org.mapstruct.MappingTarget;

import java.util.List;

@Mapper(componentModel = "spring")
public interface CommentsMapper {

    Comments dtoToEntity(CommentsRequestDto commentsRequestDto);

    CommentsResponseDto entityToDto(Comments comments);

    void updateEntityFromDto(CommentsRequestDto commentsRequestDto, @MappingTarget Comments comments);

    List<CommentsResponseDto> listOfEntitiesToListOfDto(List<Comments> commentsList);

    List<Comments> dtoToEntity(List<CommentsResponseDto> commentsResponseDtos);
}
