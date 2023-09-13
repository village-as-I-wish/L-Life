package kosa.com.suntofu.L_LIFE.chat.vo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ChatMessageVo {
    private int lStreamId;
    private String mName;
    private String message;
}

