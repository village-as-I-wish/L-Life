package kosa.com.suntofu.L_LIFE.subscription.vo;

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
    private HttpStatus httpStatus;
    private String message;
    private Integer count;
    private List<Object> result;
}