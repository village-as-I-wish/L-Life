package kosa.com.suntofu.L_LIFE.common.vo;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class ReviewImgVo {

    private int rImgId;
    private String rImgUrl;
    private int lfReviewId;

    private int rImgSeq;
}
