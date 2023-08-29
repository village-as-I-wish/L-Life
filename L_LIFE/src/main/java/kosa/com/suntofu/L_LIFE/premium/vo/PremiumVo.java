package kosa.com.suntofu.L_LIFE.premium.vo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class PremiumVo {
    private int lfId; //리바트 라이프 가구 아이디
    private int fId; //리바트 가구 아이디
    private boolean lfSubType; // 가구 구독 타입 (스탠다드는 0, 프리미엄은 1)
    private String fImgUrl; // 가구 이미지 URL
    private String fBrandName; // 가구 브랜드 이름
    private String fName; // 가구 이름
    private int lfPrPrice; // 프리미엄 월 구독료
    private Integer fMoodId; // 가구 분위기 아이디 (필터링)
    private Integer fBrandId; // 가구 브랜드 아이디 (필터링)
    private Integer fCategroyId; // 가구 카테고리 아이디 (필터링)
}
