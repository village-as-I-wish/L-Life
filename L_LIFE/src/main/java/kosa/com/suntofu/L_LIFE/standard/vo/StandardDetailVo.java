package kosa.com.suntofu.L_LIFE.standard.vo;


import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class StandardDetailVo {

    private int lfId; //리바트 가구 아이디
    private boolean lfSubType;// 가구 구독 타입
    private int lfStCoin; //스탠다드 코인
    private boolean lfSubIssub; // 구독 가능 여부
    private String lfName;  // 가구이름
    private String lfImgMain; // 가구이미지
    private String lfBrandName; //브랜드이름
    private String lfSubComment; // 코멘트
    private String lfStGrade;  // 상품등급
    private Integer lfMoodId;
    private Integer lfBrandId; // 가구 브랜드 아이디 (필터링)
    private int lfCategoryId; // 가구 카테고리 아이디 (필터링)
    private List<RefurImgVo> refurImgUrl;
    private List<DetailImgVo> detailImgUrl;
}