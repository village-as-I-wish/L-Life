package kosa.com.suntofu.L_LIFE.community.dao;

import kosa.com.suntofu.L_LIFE.community.vo.*;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface CommunityDao {
    List<ProductVo> selectProductByStyle(int lfMoodId);
    BookVo selectBookDetailById(int bookId);
    List<BookVo> selectBooks();
    List<ProductVo> getProductByCategoryId(int categoryId);
    List<ProductVo> selectProductByKeyword(String keyword);

    void insertBook(BookRequestVo bookRequestVo);

    int insertBookPages(List<BookPageRequestVo> pages);

    int insertBFurniture(List<BookFurnitureVo> furnitures);
}
