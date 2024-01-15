package com.thoughtpearls.service.impl;

import com.thoughtpearls.dto.SimplePage;
import com.thoughtpearls.dto.requestdto.UserRequestDto;
import com.thoughtpearls.dto.responsedto.UserResponseDto;
import com.thoughtpearls.exception.ExceptionMessage;
import com.thoughtpearls.mapper.UserMapper;
import com.thoughtpearls.model.User;
import com.thoughtpearls.repository.UserRepository;
import com.thoughtpearls.s3_bucket.BucketName;
import com.thoughtpearls.s3_bucket.FileStore;
import com.thoughtpearls.service.UserService;
import com.thoughtpearls.util.CommonMethod;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.util.*;
import java.util.concurrent.atomic.AtomicLong;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Slf4j
public class UserServiceImpl implements UserService {
    @Autowired
    private UserRepository userRepository;
    @Autowired
    private UserMapper userMapper;
    @Autowired
    private BCryptPasswordEncoder passwordEncoder;
    @Value("${imagePath}")
    public String imagePath;

    private final FileStore fileStore;

    public UserResponseDto addUser(UserRequestDto userRequestDto) throws IOException {
        User user = userMapper.dtoToEntity(userRequestDto);
        user.setPassword(passwordEncoder.encode(userRequestDto.getPassword()));
        if (userRequestDto.getFile().isEmpty()) {
            throw new IllegalStateException(ExceptionMessage.emptyFile);
        }
        CommonMethod.checkImageformat(userRequestDto.getFile());
        AtomicLong compressedSizeKB = new AtomicLong();
        byte[] compressedImageBytes = CommonMethod.compressImage(userRequestDto.getFile().getBytes(), compressedSizeKB);
        Map<String, String> metadata = new HashMap<>();
        CommonMethod.putMetaData(userRequestDto.getFile(), compressedSizeKB, metadata);
        String path = String.format("%s", BucketName.TODO_IMAGE.getBucketName());
        String fileName = (UUID.randomUUID() + "." + userRequestDto.getFile().getContentType().substring(6));
        user.setImagePath(imagePath + fileName);
        user = userRepository.save(user);
        try {
            log.warn("Empty file provided for user: {}", userRequestDto.getEmail());
            fileStore.upload(path, fileName, Optional.of(metadata), new ByteArrayInputStream(compressedImageBytes));
        } catch (Exception e) {

            throw new IllegalStateException(ExceptionMessage.failedUpload, e);
        }
        return userMapper.entityToDto(user);
    }

    @Override
    public SimplePage<UserResponseDto> getAllUser(Integer pageNo, Integer pageSize, String sortBy) {
        Pageable pageable = PageRequest.of(pageNo, pageSize, Sort.by(sortBy));
        Page<User> pagedResult = userRepository.findAll(pageable);
        SimplePage<UserResponseDto> simplePage = new SimplePage<>(
                pagedResult.stream()
                        .map(user -> userMapper.entityToDto(user))
                        .filter(Objects::nonNull)
                        .collect(Collectors.toList()), pagedResult.getTotalElements(), pageable);
        return simplePage;
    }

    @Override
    public Optional<User> findById(Long id) {
        Optional<User> user = userRepository.findById(id);
        return user;
    }

    @Override
    public User findByEmail(String email) {
        return userRepository.findByEmail(email).orElseThrow(() -> new UsernameNotFoundException(ExceptionMessage.userNotFound));
    }


}
