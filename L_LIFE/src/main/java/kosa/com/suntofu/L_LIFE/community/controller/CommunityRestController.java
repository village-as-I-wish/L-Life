package kosa.com.suntofu.L_LIFE.community.controller;

import kosa.com.suntofu.L_LIFE.common.vo.BasicResponse;
import kosa.com.suntofu.L_LIFE.community.service.CommunityService;
import kosa.com.suntofu.L_LIFE.community.util.Style;
import kosa.com.suntofu.L_LIFE.community.vo.BookPageVo;
import kosa.com.suntofu.L_LIFE.community.vo.BookVo;
import kosa.com.suntofu.L_LIFE.community.vo.ProductVo;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;

@Slf4j
@RestController
@RequestMapping("/api/v1/community")
@RequiredArgsConstructor
public class CommunityRestController {

    private final CommunityService communityService;

    @PostMapping("/upload-files")
    public ResponseEntity<BasicResponse> uploadFile(@RequestParam("file") MultipartFile file) {
        log.info("[이미지 업로드 ] file : {} ", file);
        String uploadedUrl = communityService.uploadFileImg(file);
        if (uploadedUrl != null){
            return new ResponseEntity<BasicResponse>(BasicResponse.builder().code(200).message("이미지 업로드 성공").result(uploadedUrl).build(), HttpStatus.OK);
        }
        return new ResponseEntity<BasicResponse>(BasicResponse.builder().code(500).message("이미지 업로드 실패").result(-1).build(), HttpStatus.INTERNAL_SERVER_ERROR);
    }

    @GetMapping("/rec-products")
    public ResponseEntity<BasicResponse> getProductByStyle(@RequestParam String style){
        log.info("style  : {} " ,style);
        log.info("style enum : {} ", Style.fromValue(style).getNum());
        List<ProductVo> products = communityService.getProductByStyle(Style.fromValue(style).getNum());
        log.info("rec-products : {}", products);
        return new ResponseEntity<BasicResponse>(BasicResponse.builder().code(200).message("스타일 관련 상품 로드 성공").result(products).build(), HttpStatus.OK);

    }

    @GetMapping("/product/{categoryId}")
    public ResponseEntity<BasicResponse> getProductByCategoryId(@PathVariable int categoryId){
        try{
            List<ProductVo> products = communityService.getProductByCategoryId(categoryId);
            return new ResponseEntity<BasicResponse>(BasicResponse.builder().code(200).message("카테고리별 상품검색 완료").result(products).build(), HttpStatus.OK);

        }catch(Exception e){
            return new ResponseEntity<BasicResponse>(BasicResponse.builder().code(500).message("서버에러").result(-1).build(), HttpStatus.INTERNAL_SERVER_ERROR);

        }
    }

    @GetMapping("/search")
    public ResponseEntity<BasicResponse> searchProductByKeyWord(String keyword){
        try{
            List<ProductVo> products = communityService.getProductByKeyword(keyword);
            log.info(" Searched Product By Keyword : {} ", products);
            return new ResponseEntity<BasicResponse>(BasicResponse.builder().code(200).message("키워드별 상품검색 완료").result(products).build(), HttpStatus.OK);
        }catch(Exception e){
            return new ResponseEntity<BasicResponse>(BasicResponse.builder().code(500).message("키워드별 상품검색 실패").result(-1).build(), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @PostMapping("/book")
    public ResponseEntity<BasicResponse> createBook(@RequestBody BookVo bookVo) {
        log.info("books {} ", bookVo);
        return new ResponseEntity<BasicResponse>(BasicResponse.builder().code(200).message("플립북 생성 완료 ").build(), HttpStatus.OK);

    }
}
