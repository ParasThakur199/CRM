package com.thoughtpearls.controller;

import com.thoughtpearls.dto.responsedto.AuthenticationResponseDto;
import com.thoughtpearls.dto.requestdto.SignInRequestDto;
import com.thoughtpearls.service.AuthenticationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class AuthenticationController {
    @Autowired
    private AuthenticationService authenticationService;
    @PostMapping("/login")
    public ResponseEntity<AuthenticationResponseDto> login(@RequestBody SignInRequestDto signInRequestDto) {
        return ResponseEntity.ok(authenticationService.authenticate(signInRequestDto));
    }
}
