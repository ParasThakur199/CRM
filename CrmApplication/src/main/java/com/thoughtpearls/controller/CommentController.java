package com.thoughtpearls.controller;

import com.thoughtpearls.dto.SimplePage;
import com.thoughtpearls.dto.requestdto.CommentRequestDto;
import com.thoughtpearls.dto.responsedto.CommentResponseDto;
import com.thoughtpearls.service.CommentService;
import com.thoughtpearls.util.CommonMethod;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/comment")
public class CommentController {

    @Autowired
    private CommentService commentService;

    @PostMapping("/{leadId}")
    public ResponseEntity<CommentResponseDto> createComment(@RequestHeader(HttpHeaders.AUTHORIZATION) String authorizationHeader, @PathVariable Long leadId, @RequestBody CommentRequestDto commentRequestDto) {
        CommonMethod.validateHeader(authorizationHeader);
        return new ResponseEntity<>(commentService.createComment(commentRequestDto, leadId, authorizationHeader), HttpStatus.CREATED);
    }

    @GetMapping("/{leadId}")
    public ResponseEntity<SimplePage<CommentResponseDto>> getAllCommentsByLead(@PathVariable Long leadId,
                                                                               @RequestParam(defaultValue = "${pageNo}") Integer pageNo,
                                                                               @RequestParam(defaultValue = "${pageSize}") Integer pageSize,
                                                                               @RequestParam(defaultValue = "${sortByCreatedOn}") String sortBy) {
        return new ResponseEntity<>(commentService.getAllCommentsByLeadId(pageNo, pageSize, sortBy, leadId), HttpStatus.OK);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteComment(@PathVariable Long id) {
        commentService.deleteCommentById(id);
        return new ResponseEntity<>(HttpStatus.OK);
    }

}