package kosa.com.suntofu.L_LIFE.standard.vo;


import kosa.com.suntofu.L_LIFE.common.vo.CartItemVO;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class CartsRequestVo {

    private List<CartItemVO> carts;

}
