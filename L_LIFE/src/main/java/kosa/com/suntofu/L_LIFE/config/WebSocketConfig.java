package kosa.com.suntofu.L_LIFE.config;

import kosa.com.suntofu.L_LIFE.chat.handler.ChatHandler;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.socket.config.annotation.EnableWebSocket;
import org.springframework.web.socket.config.annotation.WebSocketConfigurer;
import org.springframework.web.socket.config.annotation.WebSocketHandlerRegistry;

@Configuration
@RequiredArgsConstructor
@EnableWebSocket // 이 어노테이션을 사용해 WebSocket을 활성화
// 핸들러를 이용해 WebSocket을 활성화하기 위한 Config 작성
public class WebSocketConfig implements WebSocketConfigurer {

    private final ChatHandler chatHandler;

    @Override
    public void registerWebSocketHandlers(WebSocketHandlerRegistry registry) {
        registry.addHandler(chatHandler, "/ws/chat")
                .setAllowedOriginPatterns("*")
                .withSockJS()
                .setClientLibraryUrl("https://livart-life.com/l-life/js/sock-client.js");
    }
}
