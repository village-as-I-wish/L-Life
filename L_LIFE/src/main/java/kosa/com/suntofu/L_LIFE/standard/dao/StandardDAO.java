package kosa.com.suntofu.L_LIFE.standard.dao;

import kosa.com.suntofu.L_LIFE.standard.vo.*;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

@Mapper
public interface StandardDAO {
    List<StandardVo> selectAllStandard();
    List<StandardLiveVo> selectAllLiveStream();
    List<StandardVo> selectStandardProductByCategory(int fCategoryId);
    List<StandardVo> selectStandardProductByKeyword(String keyword);
    List<StandardVo> searchStandardProductByFilter(SearchRequestVo requestVo);
    StandardDetailVo selectStandardDetailById(int lfId);
    List<StandardOptionVo> selectStandardOptionById(int lfId);
    List<StandardRefurVo> selectStandardRefurById(int lfId);
    List<StandardVo> selectStandardRecommendation(int lfId);
    StandardRestockVo selectStandardStockAmount(@Param("lfOptId") int lfOptId, @Param("lfId") int lfId);
    int insertOptionToReservation(StandardOptionVo standardOptionVo);
  
  
    int insertProductToCart(StandardSubscriptionVo standardSubscriptionVo);

    void insertReview(ReviewVo reviewVo);

    int insertReviewImg(List<ReviewImgVo> list);

}

