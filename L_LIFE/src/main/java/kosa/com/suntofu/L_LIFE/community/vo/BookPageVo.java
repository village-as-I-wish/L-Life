package kosa.com.suntofu.L_LIFE.community.vo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class BookPageVo {

    private int bookId;  // 북 id
    private int bpId;  // 북페이지 id
    private String bpTitle; // 북 페이지 제목
    private String bpContent; // 북페이지 내용
    private String bpImg; // 북페이지 사진
    private String bpAiImg; //북페이지 ai 사진
    private String bpAiContent;  //북페이지 ai 내용
    private String bpTag;  // 북페이지 태그
    private int bpPageNum;  // 북페이지 페이지 번호
}
