package kosa.com.suntofu.L_LIFE.subscription.vo;

import lombok.Builder;
import lombok.Data;

import java.util.Date;

@Data
@Builder
public class SubscriptionVo {

    private Integer subscriptionId;
    private Integer subscriptionType;

    private Integer memberId;

    private Date subscriptionStartDate;

    private Date subscriptionEndDate;

    private Integer subscriptionPlanId;

    private Integer subscriptionPoint;

    private Integer subscriptionStatus;
}
