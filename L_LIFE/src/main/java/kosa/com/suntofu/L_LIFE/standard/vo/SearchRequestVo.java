package kosa.com.suntofu.L_LIFE.standard.vo;

import kosa.com.suntofu.L_LIFE.constant.BrandFilter;
import kosa.com.suntofu.L_LIFE.constant.MoodFilter;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class SearchRequestVo {

    private int lfId;
    private List<Integer> lfBrandIds;
    private List<Integer> lfMoodIds;
    private int minCoin;
    private int maxCoin;
    private int minPrice;
    private int maxPrice;
    private int offset; // 오프셋 수
    private int page; // 페이지 수
}