package com.thoughtpearls.service;

import com.thoughtpearls.dto.UserRequestDto;
import com.thoughtpearls.mapper.UserPopulator;
import com.thoughtpearls.model.User;
import com.thoughtpearls.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class UserService {
    @Autowired
    private UserRepository userRepository;
    public User addUser(UserRequestDto userRequestDto){
        User user= UserPopulator.INSTANCE.populateUser(userRequestDto);
        return userRepository.save(user);
    }
}
