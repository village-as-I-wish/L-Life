package kosa.com.suntofu.L_LIFE.member.vo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class DeliveryListVo {

    private int subLfId;
    private String subscribeStartDate;
    private String deliveryCode;
    private String lfName;
    private String lfImgMain;
    private int lfSubType;
    private int lfStCoin;
    private int lfPrPrice;

}
