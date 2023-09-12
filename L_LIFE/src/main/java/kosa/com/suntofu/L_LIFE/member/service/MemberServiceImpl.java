package kosa.com.suntofu.L_LIFE.member.service;

import kosa.com.suntofu.L_LIFE.member.dao.MemberDao;
import kosa.com.suntofu.L_LIFE.member.vo.*;
import kosa.com.suntofu.L_LIFE.subscription.vo.SubscriptionVo;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
@RequiredArgsConstructor
public class MemberServiceImpl implements MemberService {

    private final MemberDao memberDao;

    @Override
    public MemberVo insertOrSelectMember(MemberVo memberVo) {
        MemberVo existingMember = memberDao.selectMemberByEmail(memberVo.getMEmail());

        if (existingMember == null) {
            // 이메일로 검색한 멤버가 존재하지 않으면 새로운 멤버를 추가합니다.
            memberDao.insertMember(memberVo);
            return memberVo;
        } else {
            // 이메일로 검색한 멤버가 이미 존재하면 해당 멤버를 반환합니다.
            return existingMember;
        }
    }

    public List<MemberVo> getAllProducts() {

        return memberDao.findAllProducts();
    }

    @Override
    public List<CartVo> getAllStandardCarts(int memberId) {
        return memberDao.findAllStandardCarts(memberId);
    }

    @Override
    public List<CartVo> getAllPremiumCarts(int memberId) {
        return memberDao.findAllPremiumCarts(memberId);
    }

    @Override
    public Integer getCurrentCoin(int memberId) {
        Integer currentCoin = memberDao.getCurrentCoin(memberId);

        if (currentCoin == null){
            return 0;
        }
        return currentCoin;
    }

    @Override
    public List<SubscriptionListVo> getAllStandardScriptionList(int memberId) {
        return memberDao.getAllStandardScriptionList(memberId);
    }

    @Override
    public List<SubscriptionListVo> getRecentStandardScriptionList(int memberId, String startDate, String endDate) {
        Map<String, Object> paramMap = new HashMap<>();
        paramMap.put("memberId", memberId);
        paramMap.put("startDate", startDate);
        paramMap.put("endDate", endDate);
        return memberDao.getRecentStandardScriptionList(paramMap);
    }

    @Override
    public Integer getOrderCount(int memberId) {
        Integer orderCount = memberDao.getOrderCount(memberId);
        if (orderCount == null){
            return 0;
        }else{
            return orderCount;
        }
    }

    @Override
    public int getDeliveryReadyStatus(int memberId) {
        Integer deliveryReadyStatus = memberDao.getDeliveryReadyStatus(memberId);
        if (deliveryReadyStatus == null){
            return 0;
        }else{
            return deliveryReadyStatus;
        }
    }

    @Override
    public int getDeliveryProgressStatus(int mId) {
        Integer deliveryProgressStatus = memberDao.getDeliveryProgressStatus(mId);
        if (deliveryProgressStatus == null){
            return 0;
        }else{
            return deliveryProgressStatus;
        }
    }

    @Override
    public int getDeliveryCompleteStatus(int mId) {
        Integer deliveryCompleteStatus = memberDao.getDeliveryCompleteStatus(mId);
        if (deliveryCompleteStatus == null){
            return 0;
        }else{
            return deliveryCompleteStatus;
        }        }


    @Override
    public List<DeliveryListVo> getDeliveryList(int memberId) {
        return memberDao.getDeliveryList(memberId);
    }

    @Override
    public void updateSubcriptionStatus(int productId) {
        memberDao.updateSubcriptionStatus(productId);
    }

    @Override
    public List<SubscriptionListVo> getAllpremiumScriptionList(int memberId) {
        return memberDao.getAllPremiumScriptionList(memberId);
    }

    @Override
    public List<SubscriptionListVo> getRecentPremiumScriptionList(int memberId, String startDate, String endDate) {
        Map<String, Object> paramMap = new HashMap<>();
        paramMap.put("memberId", memberId);
        paramMap.put("startDate", startDate);
        paramMap.put("endDate", endDate);
        return memberDao.getRecentPremiumScriptionList(paramMap);
    }

    @Override
    public Integer getStandardSubscriptionId(int mId) {
        Integer stSubId = memberDao.getStSubId(mId);
        if (stSubId == null){
            return 0;
        }else{
            return stSubId;
        }
    }

    @Override
    public Integer getPremiumSubscriptionId(int mId) {
        Integer prSubId = memberDao.getPrSubId(mId);
        if (prSubId == null){
            return 0;
        }else{
            return prSubId;
        }
    }

    @Override
    public SubscriptionVo getStandardSubscription(Integer standardSubscriptionId) {
        return memberDao.getStandardSubscription(standardSubscriptionId);
    }
}

