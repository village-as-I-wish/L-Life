package kosa.com.suntofu.L_LIFE.chat.vo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class ChatRoomVo {
    private int lStreamId; // 라이브 방송 아이디
    private String lStreamName; //라이브 방송 이름
    private LocalDateTime lStreamTime; // 라이브 방송 날짜 (timestamp)
    private int adId; // 관리자 아이디
    private String streamTime; // 라이브 시간
}
