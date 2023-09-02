package kosa.com.suntofu.L_LIFE.subscription.dao;


import kosa.com.suntofu.L_LIFE.standard.vo.StandardVo;
import kosa.com.suntofu.L_LIFE.subscription.vo.SubscriptionVo;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface SubscriptionDao {

    int insertSubscription(SubscriptionVo subscriptionVo);

}
