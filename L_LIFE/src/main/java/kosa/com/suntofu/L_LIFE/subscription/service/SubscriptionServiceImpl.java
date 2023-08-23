package kosa.com.suntofu.L_LIFE.subscription.service;


import kosa.com.suntofu.L_LIFE.subscription.dao.SubscriptionDao;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class SubscriptionServiceImpl implements SubscriptionService {

    private final SubscriptionDao subscriptionDao;
}
