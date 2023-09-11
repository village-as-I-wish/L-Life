package kosa.com.suntofu.L_LIFE.standard.vo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;


@Data
@AllArgsConstructor
@NoArgsConstructor
public class DetailImgVo {

    private int lfImgId;
    private String lfImgUrl;

    private String imgComment;
}
