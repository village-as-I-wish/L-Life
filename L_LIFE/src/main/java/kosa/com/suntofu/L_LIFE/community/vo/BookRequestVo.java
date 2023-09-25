package kosa.com.suntofu.L_LIFE.community.vo;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.web.multipart.MultipartFile;

import javax.validation.constraints.NotNull;
import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class BookRequestVo {

    private int bookId;
    private int mId;
    private String bookMainImg;
    private List<BookPageRequestVo> pages;
    private List<BookFurnitureVo> furnitures;
    private List<MultipartFile> files;
    private List<MultipartFile> aifiles;
}
