package com.thoughtpearls.dto.responsedto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class CommentResponseDto {
    private Long id;
    private String textField;
    private UserResponseDto createdBy;
    private String createdOn;
}
