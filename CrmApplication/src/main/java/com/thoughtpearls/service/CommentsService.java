package com.thoughtpearls.service;

import com.thoughtpearls.dto.CommentsRequestDto;
import com.thoughtpearls.dto.CommentsResponseDto;
import com.thoughtpearls.mapper.CommentsMapper;
import com.thoughtpearls.model.Comments;
import com.thoughtpearls.repository.CommentsRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class CommentsService {
    @Autowired
    private CommentsRepository commentsRepository;

    @Autowired
    private LeadService leadService;

    @Autowired
    private CommentsMapper commentsMapper;

    public CommentsResponseDto createComment(CommentsRequestDto commentsRequestDto, long leadId) {
        Comments comments = commentsMapper.dtoToEntity(commentsRequestDto);
        comments.setLead(leadService.findLeadById(leadId));
        Comments savedComment = commentsRepository.save(comments);
        return commentsMapper.entityToDto(savedComment);
    }

    public CommentsResponseDto updateComment(long commentId, CommentsRequestDto commentsRequestDto) {
        return commentsRepository.findById(commentId)
                .map(existingComment -> {
                    commentsMapper.updateEntityFromDto(commentsRequestDto, existingComment);
                    Comments updatedComment = commentsRepository.save(existingComment);
                    return commentsMapper.entityToDto(updatedComment);
                })
                .orElseThrow(()->new RuntimeException("No Comments present"));
    }

    public void deleteCommentById(long commentId){
        commentsRepository.deleteById(commentId);
    }
}
