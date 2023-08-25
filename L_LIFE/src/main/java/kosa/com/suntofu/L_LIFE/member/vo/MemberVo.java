package kosa.com.suntofu.L_LIFE.member.vo;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
@AllArgsConstructor
public class MemberVo {
    private String image;
    private String name;
    private int price; //스탠다드에서 가격은 코인의 개수이니까 int
}
