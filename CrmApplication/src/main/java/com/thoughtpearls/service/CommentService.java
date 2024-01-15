package com.thoughtpearls.service;

import com.thoughtpearls.dto.SimplePage;
import com.thoughtpearls.dto.requestdto.CommentRequestDto;
import com.thoughtpearls.dto.responsedto.CommentResponseDto;

public interface CommentService {
    public CommentResponseDto createComment(CommentRequestDto commentRequestDto, Long leadId, String token);

    public SimplePage<CommentResponseDto> getAllCommentsByLeadId(Integer pageNo, Integer pageSize, String sortBy, Long id);

    public void deleteCommentById(Long commentId);

}
