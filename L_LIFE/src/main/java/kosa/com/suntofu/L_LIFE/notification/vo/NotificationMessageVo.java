package kosa.com.suntofu.L_LIFE.notification.vo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class NotificationMessageVo {

    private String to;

    private String subject;

    private String message;
}
