package com.thoughtpearls.controller;

import com.thoughtpearls.dto.requestdto.UserRequestDto;
import com.thoughtpearls.dto.responsedto.UserResponseDto;
import com.thoughtpearls.service.UserService;
import com.thoughtpearls.dto.SimplePage;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;

@RestController
@RequestMapping("/user")
public class UserController {
    @Autowired
    private UserService userService;

    @PostMapping(consumes = MediaType.MULTIPART_FORM_DATA_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<UserResponseDto> addUser(@ModelAttribute UserRequestDto userRequestDto) throws IOException {
        return new ResponseEntity<>(userService.addUser(userRequestDto), HttpStatus.CREATED);
    }

    @GetMapping
    public ResponseEntity<SimplePage<UserResponseDto>> getAllUser(@RequestParam(defaultValue = "${pageNo}") Integer pageNo,
                                                                  @RequestParam(defaultValue = "${pageSize}") Integer pageSize,
                                                                  @RequestParam(defaultValue = "${sortById}") String sortBy) {
        return new ResponseEntity<>(userService.getAllUser(pageNo, pageSize, sortBy), HttpStatus.OK);
    }


}
