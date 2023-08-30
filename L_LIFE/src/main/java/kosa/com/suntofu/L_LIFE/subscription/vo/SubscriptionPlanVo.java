package kosa.com.suntofu.L_LIFE.subscription.vo;

import lombok.Builder;
import lombok.Data;

import java.time.LocalDate;
import java.util.Date;

@Data
@Builder
public class SubscriptionPlanVo {
    private Integer subscriptionPlanType;
    private String subscriptionPlanName;

    private Integer subscriptionPlanPrice;

    private LocalDate subscriptionStartDate;
    private LocalDate subscriptionFinDate;

}
