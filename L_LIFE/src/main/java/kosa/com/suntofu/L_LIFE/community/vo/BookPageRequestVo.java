package kosa.com.suntofu.L_LIFE.community.vo;


import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class BookPageRequestVo {
    private Integer bookId;
    private Integer bpId;

    private String bpTitle; // 북 페이지 제목
    private String bpContent; // 북페이지 내용
    private String bpImg; // 북페이지 사진
    private String bpAiImg; //북페이지 ai 사진
    private String bpAiContent;  //북페이지 ai 내용
    private String bpTag;  // 북페이지 태그
    private Integer bpPageNum;  // 북페이지 페이지 번호

    private Integer lfId;

    private MultipartFile file;
    private MultipartFile aiImageFile;
}

