package com.thoughtpearls.service;

import com.thoughtpearls.dto.CommentsRequestDto;
import com.thoughtpearls.dto.CommentsResponseDto;

import java.util.List;

public interface CommentsService {
    public CommentsResponseDto createComment(CommentsRequestDto commentsRequestDto, long leadId, String token);

    public void deleteCommentById(long commentId);


    public List<CommentsResponseDto> getAllComments(Integer pageNo, Integer pageSize, String sortBy);

}
