package com.mytuu.mytuu.service;

import com.mytuu.mytuu.model.User;
import com.mytuu.mytuu.repository.UserRepository;
import com.mytuu.mytuu.security.jwt.JwtUtil;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.security.crypto.password.PasswordEncoder;
import java.util.Optional;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class LoginServiceTest {

    @Mock
    private UserRepository userRepository;

    @Mock
    private PasswordEncoder passwordEncoder;

    @Mock
    private JwtUtil jwtUtil;

    @InjectMocks
    private LoginService loginService;

    @Test
    void authenticateUser_ShouldReturnTokens_WhenCredentialsAreCorrect() {
        User mockUser = new User();
        mockUser.setUsername("testuser");
        mockUser.setPassword("encodedPassword");

        when(userRepository.findByUsername("testuser")).thenReturn(Optional.of(mockUser));
        when(passwordEncoder.matches("password123", "encodedPassword")).thenReturn(true);
        when(jwtUtil.generateAccessToken("testuser")).thenReturn("mockAccessToken");
        when(jwtUtil.generateRefreshToken("testuser")).thenReturn("mockRefreshToken");

        String[] tokens = loginService.authenticateUser("testuser", "password123");

        assertNotNull(tokens);
        assertEquals("mockAccessToken", tokens[0]);
        assertEquals("mockRefreshToken", tokens[1]);
    }

    @Test
    void authenticateUser_ShouldThrowException_WhenUserNotFound() {
        when(userRepository.findByUsername("unknownUser")).thenReturn(Optional.empty());
        assertThrows(RuntimeException.class, () -> loginService.authenticateUser("unknownUser", "password123"));
    }

    @Test
    void authenticateUser_ShouldThrowException_WhenPasswordIncorrect() {
        User mockUser = new User();
        mockUser.setUsername("testuser");
        mockUser.setPassword("encodedPassword");

        when(userRepository.findByUsername("testuser")).thenReturn(Optional.of(mockUser));
        when(passwordEncoder.matches("wrongPassword", "encodedPassword")).thenReturn(false);

        assertThrows(RuntimeException.class, () -> loginService.authenticateUser("testuser", "wrongPassword"));
    }
}
