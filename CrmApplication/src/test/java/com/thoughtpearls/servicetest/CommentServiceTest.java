package com.thoughtpearls.servicetest;

import com.thoughtpearls.config.JwtService;
import com.thoughtpearls.dto.responsedto.CommentResponseDto;
import com.thoughtpearls.mapper.CommentMapper;
import com.thoughtpearls.mapper.UserMapper;
import com.thoughtpearls.model.Comment;
import com.thoughtpearls.repository.CommentRepository;
import com.thoughtpearls.repository.LeadRepository;
import com.thoughtpearls.repository.UserRepository;
import com.thoughtpearls.service.LeadService;
import com.thoughtpearls.service.impl.CommentServiceImpl;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.ArrayList;
import java.util.List;

import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class CommentServiceTest {
    @InjectMocks
    private CommentServiceImpl commentsService;
    @Mock
    private CommentMapper commentMapper;
    @Mock
    private CommentRepository commentRepository;
    @Mock
    private JwtService jwtService;
    @Mock
    private LeadService leadService;
    @Mock
    private UserRepository userRepository;
    @Mock
    private UserMapper userMapper;
    @Mock
    private LeadRepository leadRepository;

//    @Test
//    void testGetAllComments() {
//        Integer pageNo = 0;
//        Integer pageSize = 10;
//        String sortBy = "createdAt";
//        long leadId = 1;
//
//        List<Comments> mockCommentsList = createMockCommentsList();
//        Page<Comments> mockPagedResult = new PageImpl<>(mockCommentsList);
//        lenient().when(commentsRepository.findAllByLeadId(any(), eq(leadId))).thenReturn(mockPagedResult);
//
//        List<CommentsResponseDto> mockDtoList = createMockDtoList();
//        lenient().when(commentsMapper.entityToDto(any(Comments.class))).thenReturn(mockDtoList.get(0), mockDtoList.get(1));
//
//        Lead mockLead = new Lead();
//        lenient().when(leadRepository.findById(eq(leadId))).thenReturn(Optional.of(mockLead));
//        List<CommentsResponseDto> result = commentsService.getAllCommentsByLeadId(pageNo, pageSize, sortBy, leadId);
//
//        assertNotNull(result);
//
//        verify(commentsRepository).findAllByLeadId(any(), eq(leadId));
//        verify(userRepository, times(2)).findById(anyLong());
//
//    }

    private List<Comment> createMockCommentsList() {
        List<Comment> commentList = new ArrayList<>();

        Comment comment1 = new Comment();
        comment1.setId(1L);
        comment1.setTextField("textField1");
        comment1.setCreatedBy(1L);

        Comment comment2 = new Comment();
        comment2.setId(2L);
        comment2.setTextField("textField2");
        comment1.setCreatedBy(1L);

        commentList.add(comment1);
        commentList.add(comment2);

        return commentList;
    }
    private List<CommentResponseDto> createMockDtoList() {
        List<CommentResponseDto> commentResponseDtos = new ArrayList<>();

        CommentResponseDto dto1 = new CommentResponseDto();
        dto1.setId(1L);
        dto1.setTextField("textField1");

        CommentResponseDto dto2 = new CommentResponseDto();
        dto2.setId(2L);
        dto2.setTextField("textField2");

        commentResponseDtos.add(dto1);
        commentResponseDtos.add(dto2);

        return commentResponseDtos;
    }

    @Test
    void testDeleteCommentById() {
        long commentId = 1L;
        Comment comment = new Comment();
        when(commentRepository.findById(commentId)).thenReturn(java.util.Optional.of(comment));
        commentsService.deleteCommentById(commentId);
        verify(commentRepository).deleteById(commentId);
    }

//    @Test
//    void testCreateComment() {
//        try {
//            CommentsRequestDto requestDto = new CommentsRequestDto();
//            requestDto.setTextField("Test Comment");
//
//            User user = new User();
//            user.setId(2L);
//
//            long leadId = 2L;
//
//            String token = "eyJhbGciOiJIUzI1NiJ9.eyJzdWIiOiJwYXJhc2phbXdhbDE5QGdtYWlsLmNvbSIsImp0aSI6IjIiLCJpYXQiOjE3MDA2MzY3MzEsImV4cCI6MTcwMDYzODE3MX0.FpqOTCLBrfZaPfz0e7OkJvq-5Vx-Y2L6uYJN1urqP0U";
//
//            CommentsResponseDto expectedResponseDto = new CommentsResponseDto();
//            expectedResponseDto.setId(1L);
//            expectedResponseDto.setTextField("Test Comment");
////            expectedResponseDto.setCreatedBy(UserResponseDto.builder().build()));
//            expectedResponseDto.setCreatedOn(LocalDateTime.now().toString());
//
//            when(jwtService.getUserDetailsFromToken(token)).thenReturn(user);
//            when(leadService.findLeadById(leadId)).thenReturn(new Lead());
//
//            Comments comments = new Comments();
//            when(commentsMapper.dtoToEntity(requestDto)).thenReturn(comments);
//            when(commentsRepository.save(any(Comments.class))).thenReturn(comments);
//
//            when(commentsService.createComment(requestDto, leadId, token)).thenReturn(expectedResponseDto);
//
//            CommentsResponseDto responseDto = commentsService.createComment(requestDto, leadId, token);
//
//            assertNotNull(responseDto);
//            assertEquals(expectedResponseDto.getId(), responseDto.getId());
//            assertEquals(expectedResponseDto.getTextField(), responseDto.getTextField());
//            assertEquals(expectedResponseDto.getCreatedBy(), responseDto.getCreatedBy());
//            assertEquals(expectedResponseDto.getCreatedOn(), responseDto.getCreatedOn());
//
//        }catch (Exception e){
//            fail("Unexpected exception: " + e.getMessage());
//        }
//    }
}