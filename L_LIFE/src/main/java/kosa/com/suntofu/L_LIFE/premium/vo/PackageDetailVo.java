package kosa.com.suntofu.L_LIFE.premium.vo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class PackageDetailVo {

    private int lfPackageId;
    private String lfPackageName;

    private Integer lfPackageType;

    private  String lfPackageImg;

    List<PremiumVo> lfPackageProducts;
}
