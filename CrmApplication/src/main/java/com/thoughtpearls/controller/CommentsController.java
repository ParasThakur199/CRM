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

    @GetMapping("/getAll")
    public ResponseEntity<List<CommentsResponseDto>> getAllComments(@RequestParam(defaultValue = "0") Integer pageNo,
                                                                    @RequestParam(defaultValue = "2") Integer pageSize,
                                                                    @RequestParam(defaultValue = "id") String sortBy) {
        List<CommentsResponseDto> comments = commentsService.getAllComments(pageNo, pageSize, sortBy);
        return new ResponseEntity<>(comments, HttpStatus.OK);
    }

}
