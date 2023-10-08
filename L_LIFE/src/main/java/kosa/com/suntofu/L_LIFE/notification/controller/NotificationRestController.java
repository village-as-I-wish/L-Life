package kosa.com.suntofu.L_LIFE.notification.controller;


import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import kosa.com.suntofu.L_LIFE.common.vo.BasicResponse;
import kosa.com.suntofu.L_LIFE.member.service.MemberService;
import kosa.com.suntofu.L_LIFE.member.vo.MemberVo;
import kosa.com.suntofu.L_LIFE.notification.vo.NotificationMessageVo;
import kosa.com.suntofu.L_LIFE.notification.service.NotificationService;
import kosa.com.suntofu.L_LIFE.notification.vo.RestockVo;
import kosa.com.suntofu.L_LIFE.subscription.vo.PayFurnitureVo;
import lombok.Builder;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.thymeleaf.TemplateEngine;
import org.thymeleaf.context.Context;

@RestController
@RequiredArgsConstructor
@Builder
@RequestMapping("/notification")
@Tag(name = "notification", description = "알림 API")
public class NotificationRestController {

    private final MemberService memberService;
    private final NotificationService notificationService;
    private final TemplateEngine templateEngine;


    @Operation(summary = "재입고 메일 전송", description = "재입고 메일 전송")
    @PostMapping("/mail/restock/{lfId}")
    public  ResponseEntity<BasicResponse> sendRestockMail(@PathVariable int lfId,
                                                          @RequestBody RestockVo restockVo) {

        Context context = new Context();
        context.setVariable("furniture", restockVo);
        String message = templateEngine.process("pages/notification/mail_alert", context);

        NotificationMessageVo notificationMessageVo = new NotificationMessageVo("hyewon5266@naver.com", "재입고 알림", message);
        notificationService.sendMail(notificationMessageVo);
        return new ResponseEntity<BasicResponse>(BasicResponse.builder().code(200).message("재입고 메일 발송이 완료되었습니다.").result(1).build(), HttpStatus.OK);
    }

//    @Operation(summary = "재입고 메일 전송", description = "재입고 메일 전송")
//    @PostMapping("/mail/restock/{lfId}")
//    public  ResponseEntity<BasicResponse> sendRestockMail(@PathVariable int lfId,
//                                                          @RequestBody RestockVo restockVo) {
//        Context context = new Context();
////        context.setVariable("nickname", member.getMName());
//        context.setVariable("furniture", restockVo);
//        String message = templateEngine.process("pages/notification/mail_alert", context);
//
//        NotificationMessageVo notificationMessageVo = new NotificationMessageVo("hyewon5266@naver.com", "재입고 알림", message);
//        notificationService.sendMail(notificationMessageVo);
//        return new ResponseEntity<BasicResponse>(BasicResponse.builder().code(200).message("재입고 메일 발송이 완료되었습니다.").result(1).build(), HttpStatus.OK);
//    }

//    @Operation(summary = "결제 메일 전송", description = "결제 메일 전송")
//    @GetMapping("/mail/bill/{memberId}")
//    public ResponseEntity sendBillMail(@PathVariable int memberid) {
//        MemberVo member = memberService.selectMemberById(memberid);
//
//        Context context = new Context();
//        context.setVariable("member", member);
//
//        String message = templateEngine.process("pages/notification/mail_bill", context);
//
//        NotificationMessageVo notificationMessageVo = new NotificationMessageVo(member.getMEmail(), member.getMName() + "에게 ", message);
//        notificationService.sendMail(notificationMessageVo);
//        return new ResponseEntity(HttpStatus.OK);
//    }
}