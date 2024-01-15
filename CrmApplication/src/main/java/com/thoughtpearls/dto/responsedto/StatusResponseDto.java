package com.thoughtpearls.dto.responsedto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class StatusResponseDto {
    private Long id;
    private String code;
    private String description;
    private Long indexNumber;
}
