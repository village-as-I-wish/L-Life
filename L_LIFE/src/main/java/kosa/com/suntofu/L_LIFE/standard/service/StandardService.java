package kosa.com.suntofu.L_LIFE.standard.service;

import kosa.com.suntofu.L_LIFE.common.vo.CartItemVO;
import kosa.com.suntofu.L_LIFE.common.vo.ReviewRequestVo;
import kosa.com.suntofu.L_LIFE.common.vo.ReviewVo;
import kosa.com.suntofu.L_LIFE.standard.vo.*;

import java.util.List;

public interface StandardService {
    StandardPaginationVo calculateAndSetOffset(StandardPaginationVo standardPaginationVo);
    int calculatePaginationNum(int totalNum);
    List<StandardVo> getAllStandard(StandardPaginationVo standardPaginationVo);
    int getAllStandardPagination(StandardPaginationVo standardPaginationVo);
    List<StandardLiveVo> getAllLiveStream();
    List<StandardVo> getStandardProductByCategory(StandardPaginationVo standardPaginationVo);
    int getStandardProductByCategoryByPagination(StandardPaginationVo standardPaginationVo);
    List<StandardVo> getStandardProductByKeyword(StandardPaginationVo standardPaginationVo);
    int getStandardProductByKeywordByPagination(StandardPaginationVo standardPaginationVo);
    List<StandardVo> getStandardProductByFilter(SearchRequestVo requestVo);
    StandardDetailVo getStandardDetailById(int lfId);
    List<StandardOptionVo> getStandardOptionById(int lfId);
    List<StandardRefurVo> getStandardRefurById(int lfId);
    List<StandardVo> getStandardRecommendation(StandardDetailVo standardDetailVo);
    int getStandardStockAmount(int lfOptId, int lfId);
    int putOptionToReservation(StandardOptionVo standardOptionVo);
    int putProductToCart(CartItemVO cartItemVO);

    ReviewVo createReview(ReviewRequestVo reviewRequestVo) throws Exception;

    List<ReviewVo> getReviews(int lfId);

    int deleteReview(int lfReviewId);

    int putProductsToCart(CartsRequestVo cartRequestVo);

}
