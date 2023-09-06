package kosa.com.suntofu.L_LIFE.standard.vo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ReviewDelRequestVo {

    private int mId;
    private int lfId;
    private int lfReviewId;
}
