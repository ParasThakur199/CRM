package com.thoughtpearls.servicetest;

import com.thoughtpearls.config.JwtService;
import com.thoughtpearls.dto.requestdto.UserRequestDto;
import com.thoughtpearls.dto.responsedto.UserResponseDto;
import com.thoughtpearls.mapper.UserMapper;
import com.thoughtpearls.model.User;
import com.thoughtpearls.repository.UserRepository;
import com.thoughtpearls.s3_bucket.FileStore;
import com.thoughtpearls.service.impl.UserServiceImpl;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import java.io.IOException;
import java.io.InputStream;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class UserServiceTest {
    @Mock
    private UserMapper userMapper;

    @Mock
    private UserRepository userRepository;

    @Mock
    private BCryptPasswordEncoder passwordEncoder;

    @Mock
    private JwtService jwtService;

    @Mock
    private AuthenticationManager authenticationManager;

    @InjectMocks
    private UserServiceImpl userService;



//    @Test
//    public void testAuthenticate() {
//        SignInDto signInDto = new SignInDto();
//        signInDto.setEmail("example@example.com");
//        signInDto.setPassword("password");
//
//        User user = new User();
//        user.setId(1L);
//        user.setEmail("example@example.com");
//
//        when(userRepository.findByEmail(signInDto.getEmail())).thenReturn(Optional.of(user));
//
//        String jwtToken = "mockedToken";
//        when(jwtService.generateToken(user)).thenReturn(jwtToken);
//
//        UserResponseDto userResponseDto = mock(UserResponseDto.class);
//
//        AuthenticationResponse expectedResponse = AuthenticationResponse.builder()
//                .token(jwtToken)
//                .userResponseDto(userResponseDto)
//                .build();
//
//        AuthenticationResponse result = userService.authenticate(signInDto);
//
//        assertEquals(expectedResponse.getToken(), result.getToken());
//    }

    @Mock
    private FileStore fileStore;
    @Test
    public void testRegister() throws IOException {
        UserRequestDto userRequestDto = new UserRequestDto();
        userRequestDto.setFirstName("test");
        userRequestDto.setLastName("test");
        userRequestDto.setEmail("test@gmail.com");
        userRequestDto.setPassword("password");

        InputStream testImageInputStream = getClass().getResourceAsStream("/images/test.jpg");
        assertNotNull(testImageInputStream,"Image not found");
        MockMultipartFile file = new MockMultipartFile("file", "test.jpg", "image/jpeg", testImageInputStream);
        userRequestDto.setFile(file);

        User user = mock(User.class);
        lenient().when(userMapper.dtoToEntity(userRequestDto)).thenReturn(user);
        lenient().when(passwordEncoder.encode(userRequestDto.getPassword())).thenReturn("encodedPassword");
        lenient().when(userRepository.save(user)).thenReturn(user);

        lenient().doNothing().when(fileStore).upload(anyString(), anyString(), any(), any(InputStream.class));

        UserResponseDto result = userService.addUser(userRequestDto);

    }
}
