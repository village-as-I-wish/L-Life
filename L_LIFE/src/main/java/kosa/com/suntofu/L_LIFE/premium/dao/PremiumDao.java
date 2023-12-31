package kosa.com.suntofu.L_LIFE.premium.dao;


import kosa.com.suntofu.L_LIFE.common.vo.CartItemVO;
import kosa.com.suntofu.L_LIFE.common.vo.ReviewImgVo;
import kosa.com.suntofu.L_LIFE.common.vo.ReviewRequestVo;
import kosa.com.suntofu.L_LIFE.common.vo.ReviewVo;
import kosa.com.suntofu.L_LIFE.premium.vo.*;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

@Mapper
public interface PremiumDao {
    List<PremiumVo> selectPremiumProductList(PaginationVo paginationVo);
    int selectProductCountPagination(PaginationVo paginationVo);
    List<PremiumVo> selectProductByCategory(PaginationVo paginationVo);
    int selectProductCountByCategoryByPagination(PaginationVo paginationVo);
    List<PremiumVo> selectProductByKeyword(PaginationVo paginationVo);
    int selectProductByKeywordPagination(PaginationVo paginationVo);
    List<PremiumVo> selectProductByFilter(PaginationVo paginationVo);
    int selectProductByFilterByPagination(PaginationVo paginationVo);
    PremiumVo selectPremiumProductDetailById(int lfId);
    List<PremiumOptionVo> selectPremiumOptionById(int lfId);
    PremiumOptionVo selectPremiumStockAmount(@Param("lfOptId") int lfOptId, @Param("lfId") int lfId);
    int insertOptionToReservation(PremiumOptionVo premiumOptionVo);
    List<PremiumVo> selectPremiumRecommendation(PremiumVo premiumVo);
    int insertPremiumProductToCart(CartItemVO cartItemVO);
    List<PackageVo> selectMDPickPackages();
    List<PackageVo> selectPromotionPackages();

    PackageDetailVo selectPackageDetail(int lfPackageId);

    List<PackageVo> selectRecommendationPackages();

    List<ReviewVo> selectAllReviews(int lfId);

    void insertReview(ReviewRequestVo reviewRequestVo);

    int insertReviewImg(List<ReviewImgVo> list);

    ReviewVo selectReviewById(int insertedReviewId);

    int deleteReview(int lfReviewId);


}
