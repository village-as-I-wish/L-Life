package kosa.com.suntofu.L_LIFE.member.dao;


import kosa.com.suntofu.L_LIFE.member.vo.*;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;
import java.util.Map;

@Mapper
public interface MemberDao {

    MemberVo selectMemberByEmail(String email);
    int insertMember(MemberVo memberVo);
    List<MemberVo> findAllProducts();

    List<CartVo> findAllStandardCarts(int memberId);

    List<CartVo> findAllPremiumCarts(int memberId);

    Integer getCurrentCoin(int memberId);

    List<SubscriptionListVo> getAllStandardScriptionList(int memberId);

    List<SubscriptionListVo> getRecentStandardScriptionList(Map<String,Object> paramMap);

    Integer getOrderCount(int mId);

    Integer getDeliveryReadyStatus(int mId);
    Integer getDeliveryProgressStatus(int mId);
    Integer getDeliveryCompleteStatus(int mId);

    List<DeliveryListVo> getDeliveryList(int memberId);

    void updateSubcriptionStatus(int productId);

    List<SubscriptionListVo> getAllPremiumScriptionList(int memberId);

    List<SubscriptionListVo> getRecentPremiumScriptionList(Map<String, Object> paramMap);

    Integer getStSubId(int mId);

    Integer getPrSubId(int mId);

}
