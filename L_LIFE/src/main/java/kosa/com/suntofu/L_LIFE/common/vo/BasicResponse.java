package kosa.com.suntofu.L_LIFE.common.vo;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.http.HttpStatus;

import java.util.List;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class BasicResponse {

    private Integer code;
    private String message;
    private Object result;

}