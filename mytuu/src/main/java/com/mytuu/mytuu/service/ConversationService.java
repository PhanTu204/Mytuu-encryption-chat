package com.mytuu.mytuu.service;

import com.mytuu.mytuu.dto.ConversationDTO;
import com.mytuu.mytuu.dto.MessageDTO;
import com.mytuu.mytuu.model.*;
import com.mytuu.mytuu.repository.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.time.LocalDateTime;
import java.util.*;

@Service
public class ConversationService {
    @Autowired
    private ConversationRepository conversationRepository;
    @Autowired
    private UserRepository userRepository;

    public ConversationDTO createConversationDTO(List<Long> userIds) {
        List<User> users = userRepository.findAllById(userIds);
        Conversation convo = new Conversation();
        convo.setParticipants(users);
        convo.setCreatedAt(LocalDateTime.now());

        Conversation saved = conversationRepository.save(convo);

        return convertToDTO(saved, userIds.get(0)); // lấy user đầu làm người tạo
    }

    public List<ConversationDTO> getUserConversationsDTO(Long userId) {
        User user = userRepository.findById(userId).orElseThrow();
        List<Conversation> conversations = conversationRepository.findByParticipantsContaining(user);
        return conversations.stream()
                .map(convo -> convertToDTO(convo, userId))
                .toList();
    }

    private ConversationDTO convertToDTO(Conversation convo, Long currentUserId) {
        // tìm partner
        User partner = convo.getParticipants().stream()
                .filter(u -> !u.getId().equals(currentUserId))
                .findFirst()
                .orElse(null);

        // tìm message cuối
        Message lastMsg = convo.getMessages() != null && !convo.getMessages().isEmpty()
                ? convo.getMessages().get(convo.getMessages().size() - 1)
                : null;

        MessageDTO lastMessageDTO = null;
        if (lastMsg != null) {
            lastMessageDTO = new MessageDTO(
                    lastMsg.getId(),
                    lastMsg.getSender().getId(),
                    lastMsg.getReceiver().getId(),
                    lastMsg.getContent(),
                    lastMsg.getTimestamp(),
                    lastMsg.isRead(),
                    convo.getId()
            );
        }

        return new ConversationDTO(
                convo.getId(),
                partner != null ? partner.getId() : null,
                partner != null ? partner.getFullName() : null,
                partner != null ? partner.getAvatar() : null,
                convo.getCreatedAt(),
                lastMessageDTO
        );
    }
}
