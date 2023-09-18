package kosa.com.suntofu.L_LIFE.community.vo;


import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ProductVo {

    private int lfId;
    private int lfSubType;

    private int lfStCoin;

    private String lfName;

    private int lfBrandId;
    private String lfBrandName;

    private int lfMoodId;
    private String lfMoodName;

    private String lfImgMain;

}
