package com.thoughtpearls.service;

import com.thoughtpearls.config.JwtService;
import com.thoughtpearls.dto.CommentsRequestDto;
import com.thoughtpearls.dto.CommentsResponseDto;
import com.thoughtpearls.mapper.CommentsMapper;
import com.thoughtpearls.model.Comments;
import com.thoughtpearls.model.User;
import com.thoughtpearls.repository.CommentsRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Service
public class CommentsService {
    @Autowired
    private CommentsRepository commentsRepository;

    @Autowired
    private LeadService leadService;

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

    public List<CommentsResponseDto> getAllComments(Integer pageNo, Integer pageSize, String sortBy) {
        Pageable paging= PageRequest.of(pageNo, pageSize, Sort.by(sortBy));
        Page<Comments> pagedResult=commentsRepository.findAll(paging);
        if(pagedResult.hasContent()) {
            return commentsMapper.listOfEntitiesToListOfDto(pagedResult.getContent());
        } else {
            return new ArrayList<CommentsResponseDto>();
        }
    }
}
