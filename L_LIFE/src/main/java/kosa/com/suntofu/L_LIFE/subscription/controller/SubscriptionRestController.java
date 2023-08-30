package kosa.com.suntofu.L_LIFE.subscription.controller;


import kosa.com.suntofu.L_LIFE.subscription.service.SubscriptionService;
import kosa.com.suntofu.L_LIFE.subscription.vo.BasicResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Slf4j
@RestController
@RequestMapping("/api/v1/subscription")
@RequiredArgsConstructor
public class SubscriptionRestController {

    private final SubscriptionService subscriptionService;

    @PostMapping("")
    public ResponseEntity<BasicResponse> subscribe(){
        log.info("testing subscription");

        ResponseEntity responseEntity = new ResponseEntity<>(null);
        return responseEntity;
    }

}
