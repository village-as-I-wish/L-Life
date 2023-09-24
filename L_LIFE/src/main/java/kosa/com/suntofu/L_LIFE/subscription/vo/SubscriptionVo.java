package kosa.com.suntofu.L_LIFE.subscription.vo;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class SubscriptionVo {

    private Integer subscriptionId;
    private Integer subscriptionType;

    private Integer mId;

    private Date subscriptionStartDate;

    private Date subscriptionEndDate;

    private Integer subscriptionPlanId;

    private Integer subscriptionPoint;

    private Integer subscriptionStatus;

    private String subscriptionPlanName;

    private Integer subscriptionPlanPrice;

}
