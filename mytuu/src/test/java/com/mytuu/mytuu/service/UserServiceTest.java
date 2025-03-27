package com.mytuu.mytuu.service;

import com.mytuu.mytuu.dto.RegisterDTO;
import com.mytuu.mytuu.dto.UserUpdateDTO;
import com.mytuu.mytuu.model.User;
import com.mytuu.mytuu.repository.UserRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

class UserServiceTest {

    @Mock
    private UserRepository userRepository;

    @Mock
    private PasswordEncoder passwordEncoder;

    @InjectMocks
    private UserService userService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    // ✅ Test đăng ký user thành công
    @Test
    void registerUser_Success() {
        // Arrange
        RegisterDTO registerDTO = new RegisterDTO("newUser", "password123", "password123");

        when(userRepository.findByUsername(registerDTO.getUsername())).thenReturn(Optional.empty());
        when(passwordEncoder.encode(registerDTO.getPassword())).thenReturn("hashedPassword");

        User savedUser = new User();
        savedUser.setUsername(registerDTO.getUsername());
        savedUser.setPassword("hashedPassword");

        when(userRepository.save(any(User.class))).thenReturn(savedUser);

        // Act
        User result = userService.registerUser(registerDTO);

        // Assert
        assertNotNull(result);
        assertEquals("newUser", result.getUsername());
        assertEquals("hashedPassword", result.getPassword());
    }

    // ❌ Test đăng ký user thất bại do username đã tồn tại
    @Test
    void registerUser_WhenUsernameExists_ThrowsException() {
        // Arrange
        RegisterDTO registerDTO = new RegisterDTO("existingUser", "password123", "password123");

        when(userRepository.findByUsername(registerDTO.getUsername())).thenReturn(Optional.of(new User()));

        // Act & Assert
        Exception exception = assertThrows(RuntimeException.class, () -> userService.registerUser(registerDTO));
        assertEquals("Username already exists!", exception.getMessage());
    }

    // ❌ Test đăng ký user thất bại do mật khẩu không khớp
    @Test
    void registerUser_WhenPasswordsDoNotMatch_ThrowsException() {
        // Arrange
        RegisterDTO registerDTO = new RegisterDTO("newUser", "password123", "wrongPassword");

        // Act & Assert
        Exception exception = assertThrows(RuntimeException.class, () -> userService.registerUser(registerDTO));
        assertEquals("Passwords do not match!", exception.getMessage());
    }


    // ✅ Test cập nhật user thành công
    @Test
    void updateUserInfo_Success() {
        // Arrange
        Long userId = 1L;
        User existingUser = new User();
        existingUser.setId(userId);
        existingUser.setUsername("existingUser");
        existingUser.setEmail("tu@example.com");

        UserUpdateDTO updateDTO = new UserUpdateDTO();
        updateDTO.setEmail("new@example.com");
        updateDTO.setPhoneNumber("123456789");

        when(userRepository.findById(userId)).thenReturn(Optional.of(existingUser));
        when(userRepository.findByEmail(updateDTO.getEmail())).thenReturn(Optional.empty());
        when(userRepository.findByPhoneNumber(updateDTO.getPhoneNumber())).thenReturn(Optional.empty());
        when(userRepository.save(any(User.class))).thenReturn(existingUser);

        // Act
        User updatedUser = userService.updateUserInfo(userId, updateDTO);

        // Assert
        assertNotNull(updatedUser);
        assertEquals("new@example.com", updatedUser.getEmail());
        assertEquals("123456789", updatedUser.getPhoneNumber());
    }

    // ❌ Test cập nhật user thất bại do email đã tồn tại
    @Test
    void updateUserInfo_WhenEmailExists_ThrowsException() {
        // Arrange
        Long userId = 1L;
        User existingUser = new User();
        existingUser.setId(userId);

        User duplicateUser = new User();
        duplicateUser.setId(2L); // ID hợp lệ khác userId

        UserUpdateDTO updateDTO = new UserUpdateDTO();
        updateDTO.setEmail("duplicate@example.com");

        when(userRepository.findById(userId)).thenReturn(Optional.of(existingUser));
        when(userRepository.findByEmail(updateDTO.getEmail())).thenReturn(Optional.of(duplicateUser)); // Cần ID hợp lệ

        // Act & Assert
        Exception exception = assertThrows(RuntimeException.class, () -> userService.updateUserInfo(userId, updateDTO));
        assertEquals("Email already exists!", exception.getMessage());
    }

    // ❌ Test cập nhật user thất bại do số điện thoại đã tồn tại
    @Test
    void updateUserInfo_WhenPhoneNumberExists_ThrowsException() {
        // Arrange
        Long userId = 1L;
        User existingUser = new User();
        existingUser.setId(userId);
    
        User duplicateUser = new User();
        duplicateUser.setId(3L); // ID hợp lệ khác userId
    
        UserUpdateDTO updateDTO = new UserUpdateDTO();
        updateDTO.setPhoneNumber("123456789");
    
        when(userRepository.findById(userId)).thenReturn(Optional.of(existingUser));
        when(userRepository.findByPhoneNumber(updateDTO.getPhoneNumber())).thenReturn(Optional.of(duplicateUser)); // Cần ID hợp lệ
    
        // Act & Assert
        Exception exception = assertThrows(RuntimeException.class, () -> userService.updateUserInfo(userId, updateDTO));
        assertEquals("Phone number already exists!", exception.getMessage());
    }

    
    // ✅ Test đăng nhập thành công
    @Test
    void authenticateUser_Success() {
        // Arrange
        User user = new User();
        user.setUsername("testUser");
        user.setPassword("hashedPassword");

        when(userRepository.findByUsername("testUser")).thenReturn(Optional.of(user));
        when(passwordEncoder.matches("password123", "hashedPassword")).thenReturn(true);

        // Act
        User authenticatedUser = userService.authenticateUser("testUser", "password123");

        // Assert
        assertNotNull(authenticatedUser);
        assertEquals("testUser", authenticatedUser.getUsername());
    }

    // ❌ Test đăng nhập thất bại do sai mật khẩu
    @Test
    void authenticateUser_WhenWrongPassword_ThrowsException() {
        // Arrange
        User user = new User();
        user.setUsername("testUser");
        user.setPassword("hashedPassword");

        when(userRepository.findByUsername("testUser")).thenReturn(Optional.of(user));
        when(passwordEncoder.matches("wrongPassword", "hashedPassword")).thenReturn(false);

        // Act & Assert
        Exception exception = assertThrows(RuntimeException.class, () -> userService.authenticateUser("testUser", "wrongPassword"));
        assertEquals("Invalid password!", exception.getMessage());
    }

    // ❌ Test đăng nhập thất bại do không tìm thấy user
    @Test
    void authenticateUser_WhenUserNotFound_ThrowsException() {
        // Arrange
        when(userRepository.findByUsername("nonExistentUser")).thenReturn(Optional.empty());

        // Act & Assert
        Exception exception = assertThrows(RuntimeException.class, () -> userService.authenticateUser("nonExistentUser", "password123"));
        assertEquals("User not found!", exception.getMessage());
    }
}
