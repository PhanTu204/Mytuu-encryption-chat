package com.mytuu.mytuu.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.messaging.simp.config.MessageBrokerRegistry;
import org.springframework.web.socket.config.annotation.*;

@Configuration
@EnableWebSocketMessageBroker
public class WebSocketConfig implements WebSocketMessageBrokerConfigurer {

    @Override
    public void configureMessageBroker(MessageBrokerRegistry config) {
        config.enableSimpleBroker("/topic", "/queue"); // đích nhận
        config.setApplicationDestinationPrefixes("/app"); // đích gửi từ client
    }

    @Override
    public void registerStompEndpoints(StompEndpointRegistry registry) {
        registry.addEndpoint("/ws") // endpoint WebSocket
                .setAllowedOriginPatterns("*") // hoặc chỉ định "http://localhost:3000" nếu dùng frontend riêng
                .withSockJS(); // fallback nếu browser không hỗ trợ
    }
}
