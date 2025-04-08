package com.mytuu.mytuu.controller;

import com.mytuu.mytuu.dto.*;
import com.mytuu.mytuu.service.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.*;

@RestController
@RequestMapping("/api/messages")
public class MessageController {
    @Autowired
    private MessageService messageService;

    @PostMapping
    public ResponseEntity<MessageDTO> sendMessage(@RequestBody MessageRequestDTO dto) {
        return ResponseEntity.ok(
            messageService.sendMessageAndReturnDTO(
                dto.getConversationId(),
                dto.getSenderId(),
                dto.getReceiverId(),
                dto.getContent()
            )
        );
    }

    @GetMapping("/{conversationId}")
    public ResponseEntity<List<MessageDTO>> getMessages(@PathVariable Long conversationId) {
        return ResponseEntity.ok(messageService.getMessagesDTO(conversationId));
    }
}
