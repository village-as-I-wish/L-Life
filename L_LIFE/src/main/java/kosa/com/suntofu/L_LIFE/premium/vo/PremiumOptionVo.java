package kosa.com.suntofu.L_LIFE.premium.vo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class PremiumOptionVo {
    private int lfId; // 가구 아이디
    private int stockAmount; // 재고
    private int stockId; // 재고 아이디
    private int lfOptId; // 옵션 아이디
    private String lfOptName; // 옵션명
    private int mId; // 멤버 아이디
}
