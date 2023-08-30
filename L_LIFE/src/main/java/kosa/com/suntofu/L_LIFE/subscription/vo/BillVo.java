package kosa.com.suntofu.L_LIFE.subscription.vo;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class BillVo {
    private Integer billType;
    private SubscriptionPlanVo subscriptionPlanVo;

    private Integer totalPrice;

}
