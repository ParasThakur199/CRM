package com.thoughtpearls.dto;

import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class CommentsResponseDto {
    private long id;
    private String text_field;
    private String created_by;
}
