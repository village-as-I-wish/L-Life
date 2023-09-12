package kosa.com.suntofu.L_LIFE.chat.vo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.web.socket.WebSocketSession;

import java.util.HashSet;
import java.util.Set;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class ChatRoomVo {
    private int lStreamId; // 라이브 방송 아이디
    private String lStreamName; //라이브 방송 이름
    private Set<WebSocketSession> sessions = new HashSet<>();
}
