package kosa.com.suntofu.L_LIFE.premium.vo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class PremiumDetailVo {
    private int lfId; //리바트 라이프 가구 아이디
    private String lfImgMain; // 가구 이미지 URL
    private String lfBrandName; // 가구 브랜드 이름
    private String lfName; // 가구 이름
    private int lfPrPrice; // 프리미엄 월 구독료
    private int lfPrMinPeriod; // 프리미엄 최소 구독 개월수
}
