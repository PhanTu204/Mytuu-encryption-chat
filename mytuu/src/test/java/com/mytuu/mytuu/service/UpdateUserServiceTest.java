package com.mytuu.mytuu.service;

import com.mytuu.mytuu.dto.UserUpdateDTO;
import com.mytuu.mytuu.model.User;
import com.mytuu.mytuu.repository.UserRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

import java.util.Optional;

@ExtendWith(MockitoExtension.class)
class UpdateUserServiceTest {

    @Mock
    private UserRepository userRepository;

    @InjectMocks
    private UpdateUserService updateUserService;

    @Test
    void updateUserInfo_ShouldUpdateUser_WhenValidData() {
        Long userId = 1L;
        User existingUser = new User();
        existingUser.setId(userId);
        existingUser.setEmail("old@example.com");

        UserUpdateDTO updateDTO = new UserUpdateDTO();
        updateDTO.setEmail("new@example.com");

        when(userRepository.findById(userId)).thenReturn(Optional.of(existingUser));
        when(userRepository.findByEmail("new@example.com")).thenReturn(Optional.empty());
        when(userRepository.save(any(User.class))).thenReturn(existingUser);

        User result = updateUserService.updateUserInfo(userId, updateDTO);

        assertNotNull(result);
        assertEquals("new@example.com", result.getEmail());
        verify(userRepository).save(existingUser);
    }

    @Test
    void updateUserInfo_ShouldThrowException_WhenEmailExists() {
        Long userId = 1L;
        User existingUser = new User();
        existingUser.setId(userId);

        UserUpdateDTO updateDTO = new UserUpdateDTO();
        updateDTO.setEmail("existing@example.com");

        when(userRepository.findById(userId)).thenReturn(Optional.of(existingUser));
        when(userRepository.findByEmail("existing@example.com")).thenReturn(Optional.of(new User()));

        assertThrows(RuntimeException.class, () -> updateUserService.updateUserInfo(userId, updateDTO));
    }

    @Test
    void updateUserInfo_ShouldThrowException_WhenUserNotFound() {
        Long userId = 99L;
        UserUpdateDTO updateDTO = new UserUpdateDTO();

        when(userRepository.findById(userId)).thenReturn(Optional.empty());

        assertThrows(RuntimeException.class, () -> updateUserService.updateUserInfo(userId, updateDTO));
    }
}
