package com.thoughtpearls.dto.responsedto;

import lombok.*;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class AuthenticationResponseDto {
    private String token;
    private UserResponseDto userResponseDto;
}
