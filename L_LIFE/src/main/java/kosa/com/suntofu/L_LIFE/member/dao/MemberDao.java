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

    int getCurrentCoin(int memberId);

    List<SubscriptionListVo> getAllStandardScriptionList(int memberId);

    List<SubscriptionListVo> getRecentStandardScriptionList(Map<String,Object> paramMap);

    int getOrderCount(int mId);

    List<DeliveryStatusVo> getDeliveryStatus(int mId);

    List<DeliveryListVo> getDeliveryList(int memberId);

    void updateSubcriptionStatus(int productId);
}
