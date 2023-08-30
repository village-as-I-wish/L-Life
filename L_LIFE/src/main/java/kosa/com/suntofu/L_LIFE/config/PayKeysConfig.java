package kosa.com.suntofu.L_LIFE.config;

import kosa.com.suntofu.L_LIFE.subscription.vo.PayKeysVo;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class PayKeysConfig {

    @Value("${kakao-pay-key}")
    private String kakaoPayKey;

    @Value("${tosspayments-key}")
    private String tossPayKey;

    @Bean
    public PayKeysVo payKeysVO() {
        PayKeysVo payKeysVo = new PayKeysVo();
        payKeysVo.setKakaoPayKey(kakaoPayKey);
        payKeysVo.setTossPayKey(tossPayKey);
        return payKeysVo;
    }
}