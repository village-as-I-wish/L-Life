package kosa.com.suntofu.L_LIFE.community.vo;


import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class BookFurnitureVo {
    private int bfId;

    private int bookId;

    private int lfId;

    private String lfName;
    private String lfImgMain;

    private int lfStCoin;

    private String lfStGrade;

    private String lfSubComment;

    private int lfBrandId;

    private String lfBrandName;

    private int lfMoodId;
    private String lfMoodName;
}
