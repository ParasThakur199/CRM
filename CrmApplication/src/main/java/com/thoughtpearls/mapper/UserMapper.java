package com.thoughtpearls.mapper;

import com.thoughtpearls.dto.UserRequestDto;
import com.thoughtpearls.dto.UserResponseDto;
import com.thoughtpearls.model.User;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface UserMapper {
    User dtoToUser(UserRequestDto userRequestDto);

    UserResponseDto userToDto(User user);
}
