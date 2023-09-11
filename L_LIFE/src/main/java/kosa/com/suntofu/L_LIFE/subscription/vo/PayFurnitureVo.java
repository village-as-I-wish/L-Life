package kosa.com.suntofu.L_LIFE.subscription.vo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class PayFurnitureVo {
    private int subLfId;
    private int subscriptionId;
    private int lfId;
    private int lfOptId;
    private String lfName;
    private String lfImgMain;
    private int lfPrPrice;
    private int lfStCoin;
    private int quantity;
    private String lfBrandName;
    private int lfPrMinPeriod;
    private int memberId;

    private String deliveryDate;
    private String deliveryTime;
    private String deliveryAddress;
}
