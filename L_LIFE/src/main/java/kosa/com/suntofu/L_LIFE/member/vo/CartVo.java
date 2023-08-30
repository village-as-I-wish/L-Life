package kosa.com.suntofu.L_LIFE.member.vo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.RequiredArgsConstructor;


@Data
@RequiredArgsConstructor
@AllArgsConstructor
public class CartVo {

    private String lfName;
    private int lfStCoin;

    private int lfPrPrice;
    private String lfImgMain;
    private String lfOptName;
    private String lfBrandName;

}


