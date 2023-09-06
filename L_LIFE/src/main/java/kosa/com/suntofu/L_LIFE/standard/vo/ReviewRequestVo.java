package kosa.com.suntofu.L_LIFE.standard.vo;


import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class ReviewRequestVo {

    int lfReviewId;

    int mId;
    int lfId;

    String lfReviewTitle;

    String lfReviewContent;

    int lfReviewType;

    int lfReviewRating;

    int lfReviewSerRating;

    int lfReviewDelRating;

    List<MultipartFile> files;

}
