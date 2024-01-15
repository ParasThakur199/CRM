package com.thoughtpearls.mapper;

import com.thoughtpearls.dto.requestdto.UserRequestDto;
import com.thoughtpearls.dto.responsedto.UserResponseDto;
import com.thoughtpearls.model.User;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface UserMapper {

    User dtoToEntity(UserRequestDto userRequestDto);


    UserResponseDto entityToDto(User user);
}
