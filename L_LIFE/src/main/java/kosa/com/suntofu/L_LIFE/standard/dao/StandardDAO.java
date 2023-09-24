package kosa.com.suntofu.L_LIFE.standard.dao;

import kosa.com.suntofu.L_LIFE.common.vo.CartItemVO;
import kosa.com.suntofu.L_LIFE.standard.vo.*;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;
import java.util.Map;

@Mapper
public interface StandardDAO {
    List<StandardVo> selectAllStandard(StandardPaginationVo standardPaginationVo);
    int selectAllStandardPagination(StandardPaginationVo standardPaginationVo);
    List<StandardLiveVo> selectAllLiveStream();
    List<StandardVo> selectStandardProductByCategory(StandardPaginationVo standardPaginationVo);
    int selectStandardProductByCategoryByPagination(StandardPaginationVo standardPaginationVo);
    List<StandardVo> selectStandardProductByKeyword(StandardPaginationVo standardPaginationVo);
    int selectStandardProductByKeywordByPagination(StandardPaginationVo standardPaginationVo);
    List<StandardVo> searchStandardProductByFilter(SearchRequestVo requestVo);
    StandardDetailVo selectStandardDetailById(int lfId);
    List<StandardOptionVo> selectStandardOptionById(int lfId);
    List<StandardRefurVo> selectStandardRefurById(int lfId);
    List<StandardVo> selectStandardRecommendation(StandardDetailVo standardDetailVo);
    StandardRestockVo selectStandardStockAmount(@Param("lfOptId") int lfOptId, @Param("lfId") int lfId);
    int insertOptionToReservation(StandardOptionVo standardOptionVo);
  
    int insertProductToCart(CartItemVO cartItemVO); // 스탠다드 상품 장바구니 추가

    void insertReview(ReviewRequestVo reviewRequestVo);

    int insertReviewImg(List<ReviewImgVo> list);

    List<ReviewVo> selectAllReviews(int lfId);

    ReviewVo selectReviewById(int insertedReviewId);

    int deleteReview(int lfReviewId);

    int insertProductsToCart(Map<String, Object> carts);

}

