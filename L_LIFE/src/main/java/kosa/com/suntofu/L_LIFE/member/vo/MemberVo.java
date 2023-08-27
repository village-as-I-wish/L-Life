package kosa.com.suntofu.L_LIFE.member.vo;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
@AllArgsConstructor
public class MemberVo {
    private String date;
    private String image;
    private String name;
    private int coins; //스탠다드에서 가격은 코인의 개수이니까 int
    private String subscriptionType;
    private int price; //월 45000원 등
    private int orderNumber;
    private int trackingNumber;
    private int month; //프리미엄 구독 24개월 12개월 등
}