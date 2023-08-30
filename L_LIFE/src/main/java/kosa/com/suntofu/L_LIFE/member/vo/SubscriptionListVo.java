package kosa.com.suntofu.L_LIFE.member.vo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class SubscriptionListVo {

    private int subLfId;
    private String lfName;
    private int lfStCoin;
    private int lfPrPrice;
    private String lfImgMain;
    private String lfOptName;
    private String lfBrandName;

}
