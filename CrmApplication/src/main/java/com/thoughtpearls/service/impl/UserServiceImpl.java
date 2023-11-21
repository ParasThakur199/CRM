package com.thoughtpearls.service.impl;

import com.thoughtpearls.config.JwtService;
import com.thoughtpearls.dto.AuthenticationResponse;
import com.thoughtpearls.dto.SignInDto;
import com.thoughtpearls.dto.UserRequestDto;
import com.thoughtpearls.mapper.UserMapper;
import com.thoughtpearls.model.User;
import com.thoughtpearls.repository.UserRepository;
import com.thoughtpearls.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class UserServiceImpl implements UserService {
    @Autowired
    private UserRepository userRepository;
    @Autowired
    private UserMapper userMapper;

    @Autowired
    private BCryptPasswordEncoder passwordEncoder;
    @Autowired
    private JwtService jwtService;
    @Autowired
    private AuthenticationManager authenticationManager;
    public User addUser(UserRequestDto userRequestDto){
        User user=userMapper.dtoToUser(userRequestDto);
        user.setPassword(passwordEncoder.encode(userRequestDto.getPassword()));
        return userRepository.save(user);
    }

    public AuthenticationResponse authenticate(SignInDto signInDto) {
        authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(signInDto.getEmail(),signInDto.getPassword()));
        User user=userRepository.findByEmail(signInDto.getEmail()).orElseThrow();
        String jwtToken=jwtService.generateToken(user);
        return AuthenticationResponse.builder().token(jwtToken).build();
    }

}
