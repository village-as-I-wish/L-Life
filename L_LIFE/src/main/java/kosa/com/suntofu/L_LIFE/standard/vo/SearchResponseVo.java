package kosa.com.suntofu.L_LIFE.standard.vo;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class SearchResponseVo {

    private int lfStCoin; //스탠다드 코인
    private boolean lfSubIssub; // 구독 가능 여부
    private String lfName;  // 가구이름
    private String lfImgMain; // 가구이미지
    private String lfBrandName; //브랜드이름
    private Integer lfMoodId; // 가구 분위기 아이디 (필터링)
    private Integer lfBrandId; // 가구 브랜드 아이디 (필터링)
    private int minCoin;
    private int maxCoin;
}