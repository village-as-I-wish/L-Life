package kosa.com.suntofu.L_LIFE.chat.vo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class ChatMessageVo {
    private String lStreamId;
    private String mName;
    private String message;
}
