package com.thoughtpearls.controller;

import com.thoughtpearls.dto.AuthenticationResponse;
import com.thoughtpearls.dto.SignInDto;
import com.thoughtpearls.dto.UserRequestDto;
import com.thoughtpearls.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/user")
public class UserController {
    @Autowired
    private UserService userService;

    @PostMapping("/add")
    public void addUser(@RequestBody UserRequestDto userRequestDto)
    {
        userService.addUser(userRequestDto);
    }

    @PostMapping("/login")
    public ResponseEntity<AuthenticationResponse> signIn(@RequestBody
                                                             SignInDto signInDto)
    {
        return ResponseEntity.ok(userService.authenticate(signInDto));
    }
}
