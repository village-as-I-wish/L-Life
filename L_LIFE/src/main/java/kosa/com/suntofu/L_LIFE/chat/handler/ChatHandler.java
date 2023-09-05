package kosa.com.suntofu.L_LIFE.chat.handler;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import org.springframework.web.socket.CloseStatus;
import org.springframework.web.socket.TextMessage;
import org.springframework.web.socket.WebSocketSession;
import org.springframework.web.socket.handler.TextWebSocketHandler;

import java.util.ArrayList;
import java.util.List;

@Slf4j
@Component
// 소켓통신은 서버:클라이언트가 1:N관계이므로 다수의 클라이언트가 보낸 메세지를 처리할 핸들러가 필요
public class ChatHandler extends TextWebSocketHandler { // 텍스트 기반 채팅 구현이므로 TextWebSocketHandler을 상속받아 작성

    private static List<WebSocketSession> list = new ArrayList<>();

    @Override
    protected void handleTextMessage(WebSocketSession session, TextMessage message) throws Exception {
        String payload = message.getPayload();
        log.info("payload{}", payload);

        for(WebSocketSession sess: list) {
            sess.sendMessage(message);
        }
    }

    //Client가 접속 시 호출되는 메서드
    @Override
    public void afterConnectionEstablished(WebSocketSession session) throws Exception {
        list.add(session);
        log.info("session{} 클라이언트 접속", session);
    }

    //Client가 접속 해제 시 호출되는 메서드
    @Override
    public void afterConnectionClosed(WebSocketSession session, CloseStatus status) throws Exception {
        log.info("session{} 클라이언트 접속 해제", session);
        list.remove(session);
    }
}
