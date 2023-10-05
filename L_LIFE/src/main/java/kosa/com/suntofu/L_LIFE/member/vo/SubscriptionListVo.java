package kosa.com.suntofu.L_LIFE.member.vo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class SubscriptionListVo {
    private int lfId;
    private int subLfId;
    private String lfName;
    private int lfStCoin;
    private int lfPrPrice;
    private int lfPrMinPeriod;
    private String lfImgMain;
    private String lfOptName;
    private String lfBrandName;
    private LocalDateTime subLfStartDate;
    private LocalDateTime subLfEndDate;
    private String subscriptionPlanName;

}
