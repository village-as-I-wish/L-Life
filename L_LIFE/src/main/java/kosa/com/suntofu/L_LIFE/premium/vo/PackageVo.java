package kosa.com.suntofu.L_LIFE.premium.vo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class PackageVo  implements Serializable {
    private Integer lfPackageId;
    private String lfPackageName;

    private Integer lfPackageType;

    private  String lfPackageImg;
}
