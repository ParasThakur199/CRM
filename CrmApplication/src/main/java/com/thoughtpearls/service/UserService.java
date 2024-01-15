package com.thoughtpearls.service;

import com.thoughtpearls.dto.requestdto.UserRequestDto;
import com.thoughtpearls.dto.responsedto.UserResponseDto;
import com.thoughtpearls.dto.SimplePage;
import com.thoughtpearls.model.User;

import java.io.IOException;
import java.util.Optional;


public interface UserService {

    public UserResponseDto addUser(UserRequestDto userRequestDto) throws IOException;


    public SimplePage<UserResponseDto> getAllUser(Integer pageNo, Integer pageSize, String sortBy);

    public Optional<User> findById(Long id);

    User findByEmail(String email);
}
