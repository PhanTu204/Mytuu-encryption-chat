package com.mytuu.mytuu.service;

import com.mytuu.mytuu.dto.MessageDTO;
import com.mytuu.mytuu.model.*;
import com.mytuu.mytuu.repository.ConversationRepository;
import com.mytuu.mytuu.repository.MessageRepository;
import com.mytuu.mytuu.repository.UserRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.time.LocalDateTime;
import java.util.*;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

class MessageServiceTest {

    @Mock
    private MessageRepository messageRepository;

    @Mock
    private ConversationRepository conversationRepository;

    @Mock
    private UserRepository userRepository;

    @InjectMocks
    private MessageService messageService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void testSendMessageAndReturnDTO() {
        Long conversationId = 1L;
        Long senderId = 1L;
        Long receiverId = 2L;
        String content = "Hello there";

        // Tạo dummy Conversation và Users
        Conversation convo = new Conversation();
        convo.setId(conversationId);

        User sender = new User();
        sender.setId(senderId);
        sender.setUsername("Alice");

        User receiver = new User();
        receiver.setId(receiverId);
        receiver.setUsername("Bob");

        Message message = new Message();
        message.setId(10L);
        message.setSender(sender);
        message.setReceiver(receiver);
        message.setContent(content);
        message.setTimestamp(LocalDateTime.now());
        message.setRead(false);
        message.setConversation(convo);

        when(conversationRepository.findById(conversationId)).thenReturn(Optional.of(convo));
        when(userRepository.findById(senderId)).thenReturn(Optional.of(sender));
        when(userRepository.findById(receiverId)).thenReturn(Optional.of(receiver));
        when(messageRepository.save(any(Message.class))).thenReturn(message);

        MessageDTO result = messageService.sendMessage(conversationId, senderId, receiverId, content);

        assertNotNull(result);
        assertEquals(senderId, result.getSenderId());
        assertEquals(receiverId, result.getReceiverId());
        assertEquals(content, result.getContent());
        assertEquals(conversationId, result.getConversationId());
    }

    @Test
    void testGetMessagesDTO() {
        Long conversationId = 1L;

        // Setup dummy data
        Conversation convo = new Conversation();
        convo.setId(conversationId);

        User sender = new User();
        sender.setId(1L);
        sender.setUsername("Alice");

        User receiver = new User();
        receiver.setId(2L);
        receiver.setUsername("Bob");

        Message message1 = new Message();
        message1.setId(1L);
        message1.setSender(sender);
        message1.setReceiver(receiver);
        message1.setContent("First message");
        message1.setTimestamp(LocalDateTime.now());
        message1.setRead(true);
        message1.setConversation(convo);

        Message message2 = new Message();
        message2.setId(2L);
        message2.setSender(receiver);
        message2.setReceiver(sender);
        message2.setContent("Second message");
        message2.setTimestamp(LocalDateTime.now());
        message2.setRead(false);
        message2.setConversation(convo);

        List<Message> messages = Arrays.asList(message1, message2);

        when(messageRepository.findByConversationIdOrderByTimestampAsc(conversationId)).thenReturn(messages);

        List<MessageDTO> result = messageService.getMessagesDTO(conversationId);

        assertEquals(2, result.size());
        assertEquals("First message", result.get(0).getContent());
        assertEquals("Second message", result.get(1).getContent());
    }
}
