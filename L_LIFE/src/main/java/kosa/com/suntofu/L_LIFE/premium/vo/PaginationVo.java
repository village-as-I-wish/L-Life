package kosa.com.suntofu.L_LIFE.premium.vo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class PaginationVo {
    private int lfId;
    private String lfImgMain; // 가구 이미지 URL
    private String lfBrandName; // 가구 브랜드 이름
    private String lfName; // 가구 이름
    private int lfPrPrice; // 프리미엄 월 구독료
    private int offset; // 오프셋 수
    private int page; // 페이지 수
    private int lfSubType; // 구독종류
    private int lfCategoryId; // 카테고리 아이디
    private String keyword; // 검색 키워드
    private Integer lfMoodId; // 가구 분위기 아이디 (필터링)
    private Integer lfBrandId; // 가구 브랜드 아이디 (필터링)
    private List<Integer> lfBrandIds;
    private List<Integer> lfMoodIds;
    private int minPrice;
    private int maxPrice;
}
