package com.thoughtpearls.controller;

import com.thoughtpearls.dto.UserRequestDto;
import com.thoughtpearls.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class UserController {
    @Autowired
    private UserService userService;

    @PostMapping("/addUser")
    public void addUser(@RequestBody UserRequestDto userRequestDto)
    {
        userService.addUser(userRequestDto);
    }
}
