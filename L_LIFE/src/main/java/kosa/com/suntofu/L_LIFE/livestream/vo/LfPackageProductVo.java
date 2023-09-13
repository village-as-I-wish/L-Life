package kosa.com.suntofu.L_LIFE.livestream.vo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class LfPackageProductVo {

    private int lfpCatalogId;
    private int lfPackageId; // 해당 패키지 Id
    private int lfId; // 해당 상품 Id

    private int lfOptId; // 상품 옵션 Id

    private String lfOptName; // 옵션 이름

    private int lfStCoin; // 상품 코인 개수

    private String lfSubComment; // 상품 추가 정보

    private String lfStGrade; // 상품 grade

    private String lfName; // 상품명

    private String lfImgMain; // 상품 사진

    private int lfMoodId; // 상품 분위기 Id

    private String lfMoodName; // 상품 분위기 명
    private int lfBrandId; // 상품 브랜드 Id

    private String lfBrandName; // 상품 브랜드 명


}
