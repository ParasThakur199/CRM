package com.thoughtpearls.mapper;

import com.thoughtpearls.dto.UserRequestDto;
import com.thoughtpearls.model.User;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

@Mapper
public interface UserPopulator {
    UserPopulator INSTANCE= Mappers.getMapper(UserPopulator.class);
    User populateUser(UserRequestDto userRequestDto);
}
