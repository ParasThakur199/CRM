package com.thoughtpearls.service.Impl;

import com.thoughtpearls.config.JwtService;
import com.thoughtpearls.dto.CommentsRequestDto;
import com.thoughtpearls.dto.CommentsResponseDto;
import com.thoughtpearls.exception.CommentCreationException;
import com.thoughtpearls.exception.CommentsNotFoundException;
import com.thoughtpearls.exception.TokenException;
import com.thoughtpearls.mapper.CommentsMapper;
import com.thoughtpearls.model.Comments;
import com.thoughtpearls.model.User;
import com.thoughtpearls.repository.CommentsRepository;
import com.thoughtpearls.service.CommentsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.ArrayList;
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
        try {
            User user = jwtService.getUserDetailsFromToken(token);
            if(user==null){
                throw new TokenException("Unable to retrieve user details from the provided token");
            }

            Comments comments = commentsMapper.dtoToEntity(commentsRequestDto);
            comments.setLead(leadService.findLeadById(leadId));
            comments.setCreatedBy(user.getId());
            comments.setCreatedOn(LocalDateTime.now());
            Comments savedComment = commentsRepository.save(comments);
            return commentsMapper.entityToDto(savedComment);
        }catch (CommentCreationException e){
            throw new CommentCreationException("Comment not created :"+e.getMessage());
        }
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
            throw new CommentsNotFoundException("No comments Found ");
        }
    }
}
