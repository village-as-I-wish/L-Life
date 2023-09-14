package kosa.com.suntofu.L_LIFE.common.vo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class CartItemVO {
    private Integer cartId;

    private Integer lfId;

    private Integer mId;

    private Integer lfPackageId;

    private Integer lfOptId;

    private Integer totalCoin;

    private Integer totalPrice;
}
