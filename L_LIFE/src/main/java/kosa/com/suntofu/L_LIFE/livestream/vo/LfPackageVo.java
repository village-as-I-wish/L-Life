package kosa.com.suntofu.L_LIFE.livestream.vo;


import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class LfPackageVo{

    private int lfPackageId;
    private String lfPackageName; // 라이브 패키지 이름
    private int lfPackageStCoin;  // 라이브 패키지 코인 개수
    private String lfPackageImg; // 패키지 이미지 -상품 대표 사진
}
