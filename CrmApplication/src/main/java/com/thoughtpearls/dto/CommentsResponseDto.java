package com.thoughtpearls.dto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class CommentsResponseDto {
    private long id;
    private String textField;
    private String createdBy;
}
