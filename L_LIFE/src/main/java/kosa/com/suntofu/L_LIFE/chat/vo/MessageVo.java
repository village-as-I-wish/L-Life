package kosa.com.suntofu.L_LIFE.chat.vo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;


@Data
@AllArgsConstructor
@NoArgsConstructor
@Document(collection = "LSChatMessage")
public class MessageVo {

    @Id
    private String mName;
    private String messageContent;
    private int mId;
    private int messageId;
    private int chatroomId;

    public void setId(String id) {
    }
}
