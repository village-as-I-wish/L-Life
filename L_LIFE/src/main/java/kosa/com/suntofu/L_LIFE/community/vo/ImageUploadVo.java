package kosa.com.suntofu.L_LIFE.community.vo;

import lombok.Data;
import org.springframework.web.multipart.MultipartFile;

@Data
public class ImageUploadVo {

    private MultipartFile file;
}
