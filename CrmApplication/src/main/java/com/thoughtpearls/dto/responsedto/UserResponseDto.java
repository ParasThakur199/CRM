package com.thoughtpearls.dto.responsedto;

import com.thoughtpearls.enums.Role;
import lombok.*;

@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class UserResponseDto {
    private Long id;
    private String firstName;
    private String lastName;
    private String email;
    private String imagePath;
    private final Role role = Role.User;

}
