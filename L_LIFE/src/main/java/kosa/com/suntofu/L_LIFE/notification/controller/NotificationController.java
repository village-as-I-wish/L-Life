package kosa.com.suntofu.L_LIFE.notification.controller;


import kosa.com.suntofu.L_LIFE.notification.vo.NotificationMessageVo;
import kosa.com.suntofu.L_LIFE.notification.service.NotificationService;
import lombok.Builder;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.thymeleaf.TemplateEngine;
import org.thymeleaf.context.Context;

@RestController
@RequiredArgsConstructor
@Builder
@RequestMapping("/notification")
public class NotificationController {

    private final NotificationService notificationService;
    private final TemplateEngine templateEngine;

    @GetMapping("/send-mail")
    public ResponseEntity sendMail() {
        String username = "dev-park";
        Context context = new Context();
        context.setVariable("nickname", username);

        String message = templateEngine.process("pages/notification/mail_alert", context);

        NotificationMessageVo notificationMessageVo = new NotificationMessageVo("seoungjoo01@gmail.com", username + "에게 ", message);
        notificationService.sendMail(notificationMessageVo);
        return new ResponseEntity(HttpStatus.OK);
    }
}