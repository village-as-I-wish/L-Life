package kosa.com.suntofu.L_LIFE.community.vo;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.web.multipart.MultipartFile;

import javax.validation.constraints.NotNull;
import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class BookRequestVo {

    private int bookId;

    private int mId;
    @NotNull
    private String bookMainImg;
    @NotNull
    private List<BookPageRequestVo> pages;
    @NotNull
    private List<BookFurnitureVo> furnitures;
    private List<MultipartFile> files;
    private List<MultipartFile> aifiles;
}
