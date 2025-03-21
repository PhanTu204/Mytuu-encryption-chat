package com.mytuu.mytuu.service;

import com.mytuu.mytuu.dto.RegisterDTO;
import com.mytuu.mytuu.dto.UserUpdateDTO;
import com.mytuu.mytuu.model.User;
import com.mytuu.mytuu.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.List;
import java.util.Optional;

@Service
public class UserService {
    @Autowired
    private UserRepository userRepository;

    public List<User> getAllUsers() {
        return userRepository.findAll();
    }

    public Optional<User> getUserByUsername(String username) {
        return userRepository.findByUsername(username);
    }

    // API đăng ký với DTO
    public User registerUser(RegisterDTO registerDTO) {
        if (userRepository.findByUsername(registerDTO.getUsername()).isPresent()) {
            throw new RuntimeException("Username already exists!");
        }

        if (!registerDTO.getPassword().equals(registerDTO.getConfirmPassword())) {
            throw new RuntimeException("Passwords do not match!");
        }

        // Tạo User từ DTO
        User newUser = new User();
        newUser.setUsername(registerDTO.getUsername());
        newUser.setPassword(registerDTO.getPassword());

        return userRepository.save(newUser);
    }

    // API cập nhật thông tin user
    public User updateUserInfo(Long userId, UserUpdateDTO updateDTO) {
        Optional<User> userOptional = userRepository.findById(userId);

        if (userOptional.isEmpty()) {
            throw new RuntimeException("User not exist!");
        }

        User existingUser = userOptional.get();
        if (updateDTO.getFullName() != null) existingUser.setFullName(updateDTO.getFullName());
        if (updateDTO.getEmail() != null) existingUser.setEmail(updateDTO.getEmail());
        if (updateDTO.getPhoneNumber() != null) existingUser.setPhoneNumber(updateDTO.getPhoneNumber());
        if (updateDTO.getDateOfBirth() != null) existingUser.setDateOfBirth(updateDTO.getDateOfBirth());
        if (updateDTO.getGender() != null) existingUser.setGender(updateDTO.getGender());

        return userRepository.save(existingUser);
    }

    // API đăng nhập 
    public User authenticateUser(String identifier, String password) {
        Optional<User> userOptional = userRepository.findByUsername(identifier);

        if (userOptional.isEmpty()) {
            userOptional = userRepository.findByEmail(identifier);
        }

        if (userOptional.isEmpty()) {
            userOptional = userRepository.findByPhoneNumber(identifier);
        }

        if (userOptional.isEmpty()) {
            throw new RuntimeException("User not found!");
        }

        User user = userOptional.get();
        if (!user.getPassword().equals(password)) {
            throw new RuntimeException("Invalid password!");
        }

        return user;
    }
}
