package kosa.com.suntofu.L_LIFE.community.dao;

import kosa.com.suntofu.L_LIFE.community.vo.BookVo;
import kosa.com.suntofu.L_LIFE.community.vo.ProductVo;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface CommunityDao {
    List<ProductVo> selectProductByStyle(int lfMoodId);
    List<BookVo> selectBooks();
    BookVo selectBookDetailById(int bookId);
    List<ProductVo> getProductByCategoryId(int categoryId);
    List<ProductVo> selectProductByKeyword(String keyword);
}
