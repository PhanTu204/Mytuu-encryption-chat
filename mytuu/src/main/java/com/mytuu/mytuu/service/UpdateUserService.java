package com.mytuu.mytuu.service;

import com.mytuu.mytuu.dto.UserUpdateDTO;
import com.mytuu.mytuu.model.User;
import com.mytuu.mytuu.repository.UserRepository;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class UpdateUserService {
    @Autowired
    private UserRepository userRepository;

    @Transactional
    public User updateUserInfo(Long userId, UserUpdateDTO updateDTO) {
        User existingUser = userRepository.findById(userId)
                .orElseThrow(() -> new RuntimeException("User not exist!"));

        // check if email alr exist
        Optional<User> userWithEmail = userRepository.findByEmail(updateDTO.getEmail());
        if (userWithEmail.isPresent() && !userWithEmail.get().getId().equals(userId)) {
            throw new RuntimeException("Email '" + updateDTO.getEmail() + "' already exists!");
        }

        // check if phone number alr exist
        Optional<User> userWithPhone = userRepository.findByPhoneNumber(updateDTO.getPhoneNumber());
        if (userWithPhone.isPresent() && !userWithPhone.get().getId().equals(userId)) {
            throw new RuntimeException("Phone number '" + updateDTO.getPhoneNumber() + "' already exists!");
        }

        // update info
        if (updateDTO.getEmail() != null) existingUser.setEmail(updateDTO.getEmail());
        if (updateDTO.getPhoneNumber() != null) existingUser.setPhoneNumber(updateDTO.getPhoneNumber());
        if (updateDTO.getFullName() != null) existingUser.setFullName(updateDTO.getFullName());
        if (updateDTO.getDateOfBirth() != null) existingUser.setDateOfBirth(updateDTO.getDateOfBirth());
        if (updateDTO.getGender() != null) existingUser.setGender(updateDTO.getGender());

        return userRepository.save(existingUser);
    }
}
