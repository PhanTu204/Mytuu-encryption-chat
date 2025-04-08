package com.mytuu.mytuu.service;

import com.mytuu.mytuu.dto.MessageDTO;
import com.mytuu.mytuu.model.*;
import com.mytuu.mytuu.repository.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.time.LocalDateTime;
import java.util.*;

@Service
public class MessageService {
    @Autowired
    private MessageRepository messageRepository;
    @Autowired
    private ConversationRepository conversationRepository;
    @Autowired
    private UserRepository userRepository;

    public MessageDTO sendMessage(Long conversationId, Long senderId, Long receiverId, String content) {
        Conversation convo = conversationRepository.findById(conversationId).orElseThrow();
        User sender = userRepository.findById(senderId).orElseThrow();
        User receiver = userRepository.findById(receiverId).orElseThrow();

        Message message = new Message();
        message.setConversation(convo);
        message.setSender(sender);
        message.setReceiver(receiver);
        message.setContent(content);
        message.setTimestamp(LocalDateTime.now());
        message.setRead(false);

        convo.setLastMessage(content);
        conversationRepository.save(convo);

        Message saved = messageRepository.save(message);

        return convertToDTO(saved);
    }

    public List<MessageDTO> getMessagesDTO(Long conversationId) {
        List<Message> messages = messageRepository.findByConversationIdOrderByTimestampAsc(conversationId);
        return messages.stream().map(this::convertToDTO).toList();
    }

    private MessageDTO convertToDTO(Message message) {
        return new MessageDTO(
            message.getId(),
            message.getSender().getId(),
            message.getReceiver().getId(),
            message.getContent(),
            message.getTimestamp(),
            message.isRead(),
            message.getConversation().getId()
        );
    }    
}
