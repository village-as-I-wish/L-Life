package kosa.com.suntofu.L_LIFE.standard.vo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class StandardVo {

    private int lfId; //리바트 가구 아이디
    private int fId; //리바트 가구 아이디
    private boolean lfSubType;// 가구 구독 타입
    private int lfStCoin; //스탠다드 코인
    private boolean lfSubIssub; // 구독 가능 여부
    private String fName;  // 가구이름
    private String fImgUrl; // 가구이미지
    private String fBrandName; //브랜드이름
    private Integer fMoodId;
    private Integer fBrandId;
    private Integer fCategroyId;
}

