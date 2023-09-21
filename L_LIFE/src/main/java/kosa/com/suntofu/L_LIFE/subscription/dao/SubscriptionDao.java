package kosa.com.suntofu.L_LIFE.subscription.dao;


import kosa.com.suntofu.L_LIFE.standard.vo.StandardVo;
import kosa.com.suntofu.L_LIFE.subscription.vo.PayFurnitureVo;
import kosa.com.suntofu.L_LIFE.subscription.vo.SubscriptionVo;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface SubscriptionDao {

    int insertSubscription(SubscriptionVo subscriptionVo);

    // 프리미엄 결제 로직
    int updateStock(PayFurnitureVo payFurniture);
    int insertPrLFSubscription(PayFurnitureVo payFurniture);
    int insertdelivery(PayFurnitureVo payFurniture);

    int deleteCart(PayFurnitureVo payFurniture);

    // 스탠다드 결제 로직
    int stUpdateStock(PayFurnitureVo payFurniture);

    int stInsertStLFSubscription(PayFurnitureVo payFurniture);

    int stInsertDelivery(PayFurnitureVo payFurniture);

    int stDeleteCart(PayFurnitureVo payFurniture);

    int stUpdateSubPoint(PayFurnitureVo payFurniture);

    void renewStSubscription();
}
