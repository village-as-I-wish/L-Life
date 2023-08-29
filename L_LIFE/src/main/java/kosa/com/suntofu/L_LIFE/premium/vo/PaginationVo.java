package kosa.com.suntofu.L_LIFE.premium.vo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class PaginationVo {
    private int fId; //리바트 가구 아이디
    private String fImgUrl; // 가구 이미지 URL
    private String fBrandName; // 가구 브랜드 이름
    private String fName; // 가구 이름
    private int lfPrPrice; // 프리미엄 월 구독료
    private int offset; // 오프셋 수
    private int page; // 페이지 수
}
