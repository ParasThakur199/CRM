package com.thoughtpearls.dto;

import com.thoughtpearls.enums.Role;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class UserResponseDto {
    private long userId;
    private String firstName;
    private String lastName;
    private String email;
    private String password;
    private final Role role=Role.User;
}
