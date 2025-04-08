package com.mytuu.mytuu.controller;

import com.mytuu.mytuu.dto.ConversationDTO;
import com.mytuu.mytuu.service.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.*;

@RestController
@RequestMapping("/api/conversations")
public class ConversationController {
    @Autowired
    private ConversationService conversationService;

    @PostMapping
    public ResponseEntity<ConversationDTO> createConversation(@RequestBody List<Long> userIds) {
        return ResponseEntity.ok(conversationService.createConversationDTO(userIds));
    }

    @GetMapping("/{userId}")
    public ResponseEntity<List<ConversationDTO>> getUserConversations(@PathVariable Long userId) {
        return ResponseEntity.ok(conversationService.getUserConversationsDTO(userId));
    }
}
