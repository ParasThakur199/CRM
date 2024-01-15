package com.thoughtpearls.service.impl;

import com.thoughtpearls.config.JwtService;
import com.thoughtpearls.dto.requestdto.SignInRequestDto;
import com.thoughtpearls.dto.responsedto.AuthenticationResponseDto;
import com.thoughtpearls.dto.responsedto.UserResponseDto;
import com.thoughtpearls.exception.ExceptionMessage;
import com.thoughtpearls.exception.UserAuthenticationException;
import com.thoughtpearls.model.User;
import com.thoughtpearls.service.AuthenticationService;
import com.thoughtpearls.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.stereotype.Service;

@Service
public class AuthenticationServiceImpl implements AuthenticationService {
    @Autowired
    private AuthenticationManager authenticationManager;
    @Autowired
    private UserService userService;
    @Autowired
    private JwtService jwtService;

    public AuthenticationResponseDto authenticate(SignInRequestDto signInRequestDto) {
        try {
            authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(signInRequestDto.getEmail(), signInRequestDto.getPassword()));
        } catch (Exception e) {
            throw new UserAuthenticationException(ExceptionMessage.emailPasswordNotMatched);
        }

        User user = userService.findByEmail(signInRequestDto.getEmail());
        UserResponseDto userResponseDto = UserResponseDto.builder()
                .id(user.getId())
                .firstName(user.getFirstName())
                .lastName(user.getLastName())
                .email(user.getEmail())
                .imagePath(user.getImagePath())
                .build();

        String jwtToken = jwtService.generateToken(user);
        return AuthenticationResponseDto.builder()
                .token(jwtToken)
                .userResponseDto(userResponseDto)
                .build();
    }

}
