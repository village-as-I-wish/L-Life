package kosa.com.suntofu.L_LIFE.premium.service;


import kosa.com.suntofu.L_LIFE.premium.dao.PremiumDao;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class PremiumServiceImpl implements PremiumService{

    private final PremiumDao premiumDao;
}
