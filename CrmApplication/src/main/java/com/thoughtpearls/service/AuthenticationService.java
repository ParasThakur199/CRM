package com.thoughtpearls.service;

import com.thoughtpearls.dto.responsedto.AuthenticationResponseDto;
import com.thoughtpearls.dto.requestdto.SignInRequestDto;

public interface AuthenticationService {
    public AuthenticationResponseDto authenticate(SignInRequestDto signInRequestDto);

}
