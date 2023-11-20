package com.thoughtpearls.service;

import com.thoughtpearls.dto.AuthenticationResponse;
import com.thoughtpearls.dto.SignInDto;
import com.thoughtpearls.dto.UserRequestDto;
import com.thoughtpearls.model.User;

public interface UserService{
    public User addUser(UserRequestDto userRequestDto);

    public AuthenticationResponse authenticate(SignInDto signInDto);
}
