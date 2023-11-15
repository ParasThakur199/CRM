package com.thoughtpearls.controller;

import com.thoughtpearls.dto.CommentsRequestDto;
import com.thoughtpearls.dto.CommentsResponseDto;
import com.thoughtpearls.service.CommentsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
public class CommentsController {

    @Autowired
    private CommentsService commentsService;

    @PostMapping("/create_comment")
    public ResponseEntity<CommentsResponseDto> createComment(@RequestBody CommentsRequestDto commentsRequestDto,@RequestParam long leadId) {
        CommentsResponseDto createdComment = commentsService.createComment(commentsRequestDto, leadId);
        return new ResponseEntity<>(createdComment, HttpStatus.CREATED);
    }

    @PutMapping("/update_comment/{commentId}")
    public ResponseEntity<CommentsResponseDto> updateComment(@PathVariable long commentId, @RequestBody CommentsRequestDto commentsRequestDto) {
        CommentsResponseDto updatedComment = commentsService.updateComment(commentId, commentsRequestDto);
        return new ResponseEntity<>(updatedComment, HttpStatus.OK);
    }

    @DeleteMapping("/delete_comment/{commentId}")
    public ResponseEntity<String> deleteComment(@PathVariable long commentId) {
        commentsService.deleteCommentById(commentId);
        return new ResponseEntity<>("Comment deleted successfully", HttpStatus.OK);
    }

    @GetMapping("/getAll")
    public ResponseEntity<List<CommentsResponseDto>> getAllComments() {
        List<CommentsResponseDto> comments = commentsService.getAllComments();
        return new ResponseEntity<>(comments, HttpStatus.OK);
    }

}
