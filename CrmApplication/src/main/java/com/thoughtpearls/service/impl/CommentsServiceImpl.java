package com.thoughtpearls.service.impl;

import com.thoughtpearls.config.JwtService;
import com.thoughtpearls.dto.CommentsRequestDto;
import com.thoughtpearls.dto.CommentsResponseDto;
import com.thoughtpearls.mapper.CommentsMapper;
import com.thoughtpearls.model.Comments;
import com.thoughtpearls.model.User;
import com.thoughtpearls.repository.CommentsRepository;
import com.thoughtpearls.service.CommentsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@Service
public class CommentsServiceImpl implements CommentsService {
    @Autowired
    private CommentsRepository commentsRepository;

    @Autowired
    private LeadServiceImpl leadService;

    @Autowired
    private CommentsMapper commentsMapper;

    @Autowired
    private JwtService jwtService;

    public CommentsResponseDto createComment(CommentsRequestDto commentsRequestDto, long leadId,String token) {
        User user=jwtService.getUserDetailsFromToken(token);
        Comments comments = commentsMapper.dtoToEntity(commentsRequestDto);
        comments.setLead(leadService.findLeadById(leadId));
        comments.setCreatedBy(user.getId());
        comments.setCreatedOn(LocalDateTime.now());
        Comments savedComment = commentsRepository.save(comments);
        return commentsMapper.entityToDto(savedComment);
    }

    public void deleteCommentById(long commentId){
        commentsRepository.deleteById(commentId);
    }


}
