package kosa.com.suntofu.L_LIFE.community.service;

import kosa.com.suntofu.L_LIFE.community.vo.ProductVo;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

public interface CommunityService {

    String uploadFileImg(MultipartFile file);

    List<ProductVo> getProductByStyle(int lfMoodId);

    List<ProductVo> getProductByCategoryId(int categoryId);

    List<ProductVo> getProductByKeyword(String keyWord);
}
