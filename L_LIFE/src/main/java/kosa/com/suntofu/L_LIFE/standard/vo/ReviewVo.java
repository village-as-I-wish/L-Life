package kosa.com.suntofu.L_LIFE.standard.vo;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.sql.Date;
import java.util.List;


@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class ReviewVo {

    private int lfReviewId;
    private String lfReviewTitle;
    private String lfReviewContent;

    private int mId;

    private String mName;

    private int lfReviewType;

    private Date lfReviewDate;

    private int lfId;

    private int lfReviewRating;

    private int lfReviewSerRating;

    private int lfReviewDelRating;


    private List<ReviewImgVo> lfReviewImgs;

}
