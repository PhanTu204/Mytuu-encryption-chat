package com.mytuu.mytuu.service;

import com.mytuu.mytuu.dto.RegisterDTO;
import com.mytuu.mytuu.dto.UserUpdateDTO;
import com.mytuu.mytuu.model.User;
import com.mytuu.mytuu.repository.UserRepository;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class UserServiceTest {

    @Mock
    private UserRepository userRepository;

    @InjectMocks
    private UserService userService;

    @BeforeEach
    void setUp() {
        Mockito.reset(userRepository);
    }

    // Test: registerUser
    // ✅ Test: Đăng ký người dùng khi username đã tồn tại
    @Test
    void registerUser_WhenUsernameExists_ThrowsException() {
        RegisterDTO registerDTO = new RegisterDTO("existingUser", "password", "password");

        when(userRepository.findByUsername(registerDTO.getUsername())).thenReturn(Optional.of(new User()));

        Exception exception = assertThrows(RuntimeException.class, () -> userService.registerUser(registerDTO));
        assertEquals("Username already exists!", exception.getMessage());
    }

    // ✅ Test: Đăng ký khi mật khẩu không khớp
    @Test
    void registerUser_WhenPasswordsDoNotMatch_ThrowsException() {
        RegisterDTO registerDTO = new RegisterDTO("newUser", "password", "wrongPassword");

        Exception exception = assertThrows(RuntimeException.class, () -> userService.registerUser(registerDTO));
        assertEquals("Passwords do not match!", exception.getMessage());
    }

    // ✅ Test: Đăng ký thành công
    @Test
    void registerUser_SuccessfulRegistration_ReturnsUser() {
        RegisterDTO registerDTO = new RegisterDTO("newUser", "password", "password");
        User user = new User();
        user.setUsername(registerDTO.getUsername());
        user.setPassword(registerDTO.getPassword());

        when(userRepository.findByUsername(registerDTO.getUsername())).thenReturn(Optional.empty());
        when(userRepository.save(any(User.class))).thenReturn(user);

        User registeredUser = userService.registerUser(registerDTO);

        assertNotNull(registeredUser);
        assertEquals("newUser", registeredUser.getUsername());
    }

    // Test: updateUserInfo
    // ✅ Test: Cập nhật thông tin user khi không tìm thấy user
    @Test
    void updateUserInfo_WhenUserNotFound_ThrowsException() {
        UserUpdateDTO updateDTO = new UserUpdateDTO();
        updateDTO.setFullName("New Name");

        when(userRepository.findById(1L)).thenReturn(Optional.empty());

        Exception exception = assertThrows(RuntimeException.class, () -> userService.updateUserInfo(1L, updateDTO));
        assertEquals("User not exist!", exception.getMessage());
    }

    // ✅ Test: Cập nhật thông tin user thành công
    @Test
    void updateUserInfo_SuccessfulUpdate_ReturnsUpdatedUser() {
        User existingUser = new User();
        existingUser.setId(1L);
        existingUser.setFullName("Old Name");

        UserUpdateDTO updateDTO = new UserUpdateDTO();
        updateDTO.setFullName("New Name");

        when(userRepository.findById(1L)).thenReturn(Optional.of(existingUser));
        when(userRepository.save(any(User.class))).thenReturn(existingUser);

        User updatedUser = userService.updateUserInfo(1L, updateDTO);

        assertNotNull(updatedUser);
        assertEquals("New Name", updatedUser.getFullName());
    }

    // Test: authenticateUser
    // ✅ Test: Đăng nhập khi user không tồn tại
    @Test
    void authenticateUser_WhenUserNotFound_ThrowsException() {
        when(userRepository.findByUsername("nonexistent")).thenReturn(Optional.empty());

        Exception exception = assertThrows(RuntimeException.class, () -> userService.authenticateUser("nonexistent", "password"));
        assertEquals("User not found!", exception.getMessage());
    }

    // ✅ Test: Đăng nhập khi mật khẩu sai
    @Test
    void authenticateUser_WhenPasswordIncorrect_ThrowsException() {
        User user = new User();
        user.setUsername("testUser");
        user.setPassword("correctPassword");

        when(userRepository.findByUsername("testUser")).thenReturn(Optional.of(user));

        Exception exception = assertThrows(RuntimeException.class, () -> userService.authenticateUser("testUser", "wrongPassword"));
        assertEquals("Invalid password!", exception.getMessage());
    }

    // ✅ Test: Đăng nhập thành công
    @Test
    void authenticateUser_SuccessfulLogin_ReturnsUser() {
        User user = new User();
        user.setUsername("testUser");
        user.setPassword("password123");

        when(userRepository.findByUsername("testUser")).thenReturn(Optional.of(user));

        User authenticatedUser = userService.authenticateUser("testUser", "password123");

        assertNotNull(authenticatedUser);
        assertEquals("testUser", authenticatedUser.getUsername());
    }
}
