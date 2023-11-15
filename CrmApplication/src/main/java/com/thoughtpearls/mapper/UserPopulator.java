package com.thoughtpearls.mapper;

import com.thoughtpearls.dto.UserRequestDto;
import com.thoughtpearls.model.User;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

@Mapper(componentModel = "spring")
public interface UserPopulator {
    User populateUser(UserRequestDto userRequestDto);
}