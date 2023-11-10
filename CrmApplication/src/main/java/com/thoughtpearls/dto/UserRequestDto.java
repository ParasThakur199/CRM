package com.thoughtpearls.dto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class UserRequestDto {
    private String userName;
    private String email;
    private String password;
    private String dnf;
}
