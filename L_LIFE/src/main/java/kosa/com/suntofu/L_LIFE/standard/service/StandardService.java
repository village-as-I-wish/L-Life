package kosa.com.suntofu.L_LIFE.standard.service;

import kosa.com.suntofu.L_LIFE.standard.vo.*;

import java.util.List;

public interface StandardService {
    StandardPaginationVo calculateAndSetOffset(StandardPaginationVo standardPaginationVo);
    int calculatePaginationNum(int totalNum);
    List<StandardVo> selectAllStandard(StandardPaginationVo standardPaginationVo);
    int selectAllStandardPagination(StandardPaginationVo standardPaginationVo);
    List<StandardLiveVo> getAllLiveStream();
    List<StandardVo> selectStandardProductByCategory(StandardPaginationVo standardPaginationVo);
    int selectStandardProductByCategoryByPagination(StandardPaginationVo standardPaginationVo);
    List<StandardVo> selectStandardProductByKeyword(StandardPaginationVo standardPaginationVo);
    int selectStandardProductByKeywordByPagination(StandardPaginationVo standardPaginationVo);
    List<StandardVo> getStandardProductByFilter(SearchRequestVo requestVo);
    StandardDetailVo getStandardDetailById(int lfId);
    List<StandardOptionVo> getStandardOptionById(int lfId);
    List<StandardRefurVo> getStandardRefurById(int lfId);
    List<StandardVo> getStandardRecommendation(int lfId);
    int getStandardStockAmount(int lfOptId, int lfId);
    int putOptionToReservation(StandardOptionVo standardOptionVo);
    int putProductToCart(StandardSubscriptionVo standardSubscriptionVo);

    int createReview(ReviewVo reviewVo);
}
