package kosa.com.suntofu.L_LIFE.standard.vo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class StandardOptionVo {

    private int lfId;
    private int stockAmount;
    private int lfOptId;
    private String lfOptName;
    private int mId;
}
