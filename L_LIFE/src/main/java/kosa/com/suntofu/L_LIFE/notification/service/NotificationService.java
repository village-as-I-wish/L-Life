package kosa.com.suntofu.L_LIFE.notification.service;


import kosa.com.suntofu.L_LIFE.notification.vo.NotificationMessageVo;
import lombok.RequiredArgsConstructor;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;

import javax.mail.MessagingException;
import javax.mail.internet.MimeMessage;

@Service
@RequiredArgsConstructor
public class NotificationService {

    private final JavaMailSender javaMailSender;

    public void sendMail(NotificationMessageVo notificationMessageVo) {
        MimeMessage mimeMessage = javaMailSender.createMimeMessage();
        try {
            MimeMessageHelper mimeMessageHelper = new MimeMessageHelper(mimeMessage, false, "UTF-8");
            mimeMessageHelper.setTo(notificationMessageVo.getTo());
            mimeMessageHelper.setSubject(notificationMessageVo.getSubject());
            mimeMessageHelper.setText(notificationMessageVo.getMessage(), true); // 메일 본문 내용, HTML 여부
            javaMailSender.send(mimeMessage);
        } catch (MessagingException e) {
            throw new RuntimeException(e);
        }
    }
}
