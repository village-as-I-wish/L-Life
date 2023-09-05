package kosa.com.suntofu.L_LIFE.standard.vo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class StandardPaginationVo {

    private int lfId; //리바트 가구 아이디
    private int lfSubType;// 가구 구독 타입
    private int lfStCoin; //스탠다드 코인
    private boolean lfSubIssub; // 구독 가능 여부
    private String lfName;  // 가구이름
    private String lfImgMain; // 가구이미지
    private String lfBrandName; //브랜드이름
    private Integer lfMoodId; // 가구 분위기 아이디 (필터링)
    private Integer lfBrandId; // 가구 브랜드 아이디 (필터링)
    private int lfCategoryId; // 가구 카테고리 아이디 (필터링)
    private int offset; // 오프셋 수
    private int page; // 페이지 수
}
