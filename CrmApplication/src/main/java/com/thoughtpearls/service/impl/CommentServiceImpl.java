package com.thoughtpearls.service.impl;

import com.thoughtpearls.config.JwtService;
import com.thoughtpearls.dto.SimplePage;
import com.thoughtpearls.dto.requestdto.CommentRequestDto;
import com.thoughtpearls.dto.responsedto.CommentResponseDto;
import com.thoughtpearls.exception.CommentCreationException;
import com.thoughtpearls.exception.CommentNotFoundException;
import com.thoughtpearls.exception.ExceptionMessage;
import com.thoughtpearls.mapper.CommentMapper;
import com.thoughtpearls.mapper.UserMapper;
import com.thoughtpearls.model.Comment;
import com.thoughtpearls.model.Lead;
import com.thoughtpearls.model.User;
import com.thoughtpearls.repository.CommentRepository;
import com.thoughtpearls.service.CommentService;
import com.thoughtpearls.service.LeadService;
import com.thoughtpearls.service.UserService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.Objects;
import java.util.stream.Collectors;

@Service
@Slf4j
public class CommentServiceImpl implements CommentService {
    @Autowired
    private CommentRepository commentRepository;

    @Autowired
    private LeadService leadService;

    @Autowired
    private CommentMapper commentMapper;

    @Autowired
    private JwtService jwtService;

    @Autowired
    private UserService userService;

    @Autowired
    private UserMapper userMapper;

    public CommentResponseDto createComment(CommentRequestDto commentRequestDto, Long leadId, String token) {
        try {
            Lead lead = leadService.findById(leadId);
            User user = jwtService.getUserDetailsFromToken(token);
            Comment comment = commentMapper.dtoToEntity(commentRequestDto);
            comment.setLead(lead);
            comment.setCreatedBy(user.getId());
            comment.setCreatedOn(LocalDateTime.now());
            CommentResponseDto responseDto = commentMapper.entityToDto(commentRepository.save(comment));
            responseDto.setCreatedBy(userMapper.entityToDto(user));
            log.info("Comment created successfully for Lead ID: {}, Comment ID: {}", leadId, responseDto.getId());
            return responseDto;
        } catch (CommentCreationException e) {
            log.error("Failed to create comment for Lead ID: {}", leadId, e.getMessage());
            throw new CommentCreationException(ExceptionMessage.commentNotCreated + e.getMessage());
        }
    }

    @Override
    public SimplePage<CommentResponseDto> getAllCommentsByLeadId(Integer pageNo, Integer pageSize, String sortBy, Long leadId) {
        leadService.existById(leadId);
        Pageable pageable = PageRequest.of(pageNo, pageSize, Sort.by(sortBy).descending());
        Page<Comment> pagedResult = commentRepository.findAllByLeadId(pageable, leadId);
        SimplePage<CommentResponseDto> result = new SimplePage<>(
                pagedResult.stream()
                        .map(comments -> userService.findById(comments.getCreatedBy())
                                .map(user -> {
                                    CommentResponseDto responseDto = commentMapper.entityToDto(comments);
                                    responseDto.setCreatedBy(userMapper.entityToDto(user));
                                    return responseDto;
                                })
                                .orElse(null))
                        .filter(Objects::nonNull)
                        .collect(Collectors.toList()),
                pagedResult.getTotalElements(),
                pageable
        );
        log.info("Fetched {} comments for Lead ID: {}", result.getContent().size(), leadId);
        return result;
    }

    public void deleteCommentById(Long commentId) {
        Comment comment = commentRepository.findById(commentId).orElseThrow(
                () -> new CommentNotFoundException(ExceptionMessage.commentNotFound));
        commentRepository.delete(comment);
    }
}
