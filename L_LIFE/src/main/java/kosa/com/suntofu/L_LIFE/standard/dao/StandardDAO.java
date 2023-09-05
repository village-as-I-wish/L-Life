package kosa.com.suntofu.L_LIFE.standard.dao;

import kosa.com.suntofu.L_LIFE.premium.vo.PremiumVo;
import kosa.com.suntofu.L_LIFE.standard.vo.*;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

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
    List<StandardVo> selectStandardRecommendation(int lfId);
    StandardRestockVo selectStandardStockAmount(@Param("lfOptId") int lfOptId, @Param("lfId") int lfId);
    int insertOptionToReservation(StandardOptionVo standardOptionVo);
  
    int insertProductToCart(StandardSubscriptionVo standardSubscriptionVo);

    void insertReview(ReviewRequestVo reviewRequestVo);

    int insertReviewImg(List<ReviewImgVo> list);

    List<ReviewVo> selectAllReviews(int lfId);

}

