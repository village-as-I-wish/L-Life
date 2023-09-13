package kosa.com.suntofu.L_LIFE.premium.vo;

import kosa.com.suntofu.L_LIFE.standard.vo.DetailImgVo;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class PremiumVo {
    private int lfId; //리바트 라이프 가구 아이디
    private boolean lfSubType; // 가구 구독 타입 (스탠다드는 0, 프리미엄은 1)
    private String lfImgMain; // 가구 이미지 URL
    private String lfBrandName; // 가구 브랜드 이름
    private String lfName; // 가구 이름
    private int lfPrPrice; // 프리미엄 월 구독료
    private int lfPrMinPeriod; // 프리미엄 최소 구독 개월수
    private Integer lfMoodId; // 가구 분위기 아이디 (필터링)
    private Integer lfBrandId; // 가구 브랜드 아이디 (필터링)
    private Integer lfCategoryId; // 가구 카테고리 아이디 (필터링)
    private List<PremiumDetailImgVo> detailImgUrl;
}
