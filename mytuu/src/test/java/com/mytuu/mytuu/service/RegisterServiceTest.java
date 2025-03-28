package com.mytuu.mytuu.service;

import com.mytuu.mytuu.dto.RegisterDTO;
import com.mytuu.mytuu.model.User;
import com.mytuu.mytuu.repository.UserRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.security.crypto.password.PasswordEncoder;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

import java.util.Optional;

@ExtendWith(MockitoExtension.class)
class RegisterServiceTest {

    @Mock
    private UserRepository userRepository;

    @Mock
    private PasswordEncoder passwordEncoder;

    @InjectMocks
    private RegisterService registerService;

    @Test
    void registerUser_ShouldCreateNewUser_WhenValidData() {
        RegisterDTO registerDTO = new RegisterDTO();
        registerDTO.setUsername("testuser");
        registerDTO.setPassword("password123");
        registerDTO.setConfirmPassword("password123");

        when(userRepository.findByUsername("testuser")).thenReturn(Optional.empty());
        when(passwordEncoder.encode("password123")).thenReturn("encodedPassword");

        User savedUser = new User();
        savedUser.setUsername("testuser");
        savedUser.setPassword("encodedPassword");

        when(userRepository.save(any(User.class))).thenReturn(savedUser);

        User result = registerService.registerUser(registerDTO);

        assertNotNull(result);
        assertEquals("testuser", result.getUsername());
        assertEquals("encodedPassword", result.getPassword());

        verify(userRepository).save(any(User.class));
    }

    @Test
    void registerUser_ShouldThrowException_WhenUsernameExists() {
        RegisterDTO registerDTO = new RegisterDTO();
        registerDTO.setUsername("existingUser");
        registerDTO.setPassword("password123");
        registerDTO.setConfirmPassword("password123");

        when(userRepository.findByUsername("existingUser")).thenReturn(Optional.of(new User()));

        assertThrows(RuntimeException.class, () -> registerService.registerUser(registerDTO));
    }

    @Test
    void registerUser_ShouldThrowException_WhenPasswordsDoNotMatch() {
        RegisterDTO registerDTO = new RegisterDTO();
        registerDTO.setUsername("newUser");
        registerDTO.setPassword("password123");
        registerDTO.setConfirmPassword("password456");

        assertThrows(RuntimeException.class, () -> registerService.registerUser(registerDTO));
    }
}
