package kosa.com.suntofu.L_LIFE.community.vo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class BookRequestVo {

    private int bookId;

    private int mId;

    private String bookMainImg;

    private List<BookPageRequestVo> pages;

    private List<BookFurnitureVo> furnitures;
}
