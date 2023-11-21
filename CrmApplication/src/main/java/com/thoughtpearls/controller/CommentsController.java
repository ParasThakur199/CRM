package com.thoughtpearls.controller;

import com.thoughtpearls.dto.CommentsRequestDto;
import com.thoughtpearls.dto.CommentsResponseDto;
import com.thoughtpearls.service.CommentsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/comments")
public class CommentsController {

    @Autowired
    private CommentsService commentsService;

    @PostMapping("/create/{leadId}")
    public ResponseEntity<CommentsResponseDto> createComment(@RequestBody CommentsRequestDto commentsRequestDto,@PathVariable long leadId,@RequestParam String token) {
        CommentsResponseDto createdComment = commentsService.createComment(commentsRequestDto, leadId,token);
        return new ResponseEntity<>(createdComment, HttpStatus.CREATED);
    }

//    @PutMapping("/update/{Id}")
//    public ResponseEntity<CommentsResponseDto> updateComment(@PathVariable long Id, @RequestBody CommentsRequestDto commentsRequestDto) {
//        CommentsResponseDto updatedComment = commentsService.updateComment(Id, commentsRequestDto);
//        return new ResponseEntity<>(updatedComment, HttpStatus.OK);
//    }

    @DeleteMapping("/delete/{Id}")
    public ResponseEntity<String> deleteComment(@PathVariable long Id) {
        commentsService.deleteCommentById(Id);
        return new ResponseEntity<>("Comment deleted successfully", HttpStatus.OK);
    }



}
