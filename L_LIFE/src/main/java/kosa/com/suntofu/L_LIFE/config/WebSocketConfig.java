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
        registry.addHandler(chatHandler, "/ws/chat").setAllowedOrigins("*");
        // WebSocket에 접속하기 위한 Endpoint는 /chat, 도메인이 다른 서버에서도 접속 가능하도록 CORS:setAllowedOrigins("*")추가
        // 이제 클라이언트가 ws://localhost:8080/chat으로 커넥션을 연결하고 메세지 통신 할 수 있는 준비 끝!!!
    }
}
