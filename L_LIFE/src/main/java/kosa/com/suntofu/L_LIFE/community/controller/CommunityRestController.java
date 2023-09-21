package kosa.com.suntofu.L_LIFE.community.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import kosa.com.suntofu.L_LIFE.common.vo.BasicResponse;
import kosa.com.suntofu.L_LIFE.community.service.CommunityService;
import kosa.com.suntofu.L_LIFE.community.util.Style;
import kosa.com.suntofu.L_LIFE.community.vo.BookPageVo;
import kosa.com.suntofu.L_LIFE.community.vo.BookRequestVo;
import kosa.com.suntofu.L_LIFE.community.vo.BookVo;
import kosa.com.suntofu.L_LIFE.community.vo.ProductVo;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.util.List;

@Slf4j
@RestController
@RequestMapping("/api/v1/community")
@RequiredArgsConstructor
@Tag(name = "community", description = "커뮤니티 API")
public class CommunityRestController {

    private final CommunityService communityService;
  
    @Operation(summary = "커뮤니티 메인 - 플립북 조회 ", description = "플립북 정보를 보여줍니다.")
    @GetMapping("/book/{bookId}")
    public ResponseEntity<BasicResponse> selectBookDetailById(@PathVariable int bookId){
        BookVo bookDetail = communityService.selectBookDetailById(bookId);
        log.info("북아이디 details for bookId: {}", bookDetail);

        if (bookDetail != null){
            return new ResponseEntity<BasicResponse>(BasicResponse.builder().code(200).message("메인 플립북 상세 정보 조회 성공").result(bookDetail).build(), HttpStatus.OK);
        }

        return new ResponseEntity<BasicResponse>(BasicResponse.builder().code(404).message("메인 플립북 상세 정보 조회 실패").result(null).build(), HttpStatus.NOT_FOUND);
    }

    @Operation(summary = "이미지 업로드", description = "S3에 단일 이미지를 업로드합니다.")
    @PostMapping("/upload-files")
    public ResponseEntity<BasicResponse> uploadFile(@RequestParam("file") MultipartFile file) {
        log.info("[이미지 업로드 ] file : {} ", file);
        String uploadedUrl = communityService.uploadFileImg(file);
        if (uploadedUrl != null){
            return new ResponseEntity<BasicResponse>(BasicResponse.builder().code(200).message("이미지 업로드 성공").result(uploadedUrl).build(), HttpStatus.OK);
        }
        return new ResponseEntity<BasicResponse>(BasicResponse.builder().code(500).message("이미지 업로드 실패").result(-1).build(), HttpStatus.INTERNAL_SERVER_ERROR);
    }

    @Operation(summary = "커뮤니티 레포트 - 스타일 기반 상품 추천", description = "특정 스타일과 관련된 상품을 검색합니다.")
    @GetMapping("/rec-products")
    public ResponseEntity<BasicResponse> getProductByStyle(@RequestParam String style){
        log.info("style enum : {} ", Style.fromValue(style).getNum());
        List<ProductVo> products = communityService.getProductByStyle(Style.fromValue(style).getNum());
        log.info("rec-products : {}", products);
        return new ResponseEntity<BasicResponse>(BasicResponse.builder().code(200).message("스타일 관련 상품 로드 성공").result(products).build(), HttpStatus.OK);

    }

    @Operation(summary = "커뮤니티 플립북 - 카테고리 기반 상품 검색", description = "특정 카테고리에 속하는 상품들을 검색합니다.")
    @GetMapping("/product/{categoryId}")
    public ResponseEntity<BasicResponse> getProductByCategoryId(@PathVariable int categoryId){
            List<ProductVo> products = communityService.getProductByCategoryId(categoryId);
            return new ResponseEntity<BasicResponse>(BasicResponse.builder().code(200).message("카테고리별 상품검색 완료").result(products).build(), HttpStatus.OK);

    }

    @Operation(summary = "커뮤니티 플립북 - 검색어 기반 상품 검색", description = "상품명에 검색 키워드를 포함하고 있는 상품을 검색합니다.")
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

    @Operation(summary = "커뮤니티 플립북 - 플립북 생성 ", description = "플립북을 생성합니다.")
    @PostMapping("/book")
    public ResponseEntity<BasicResponse> createBook(BookRequestVo bookRequestVo) {
        log.info("[플립북 생성] 요청 VO {} ", bookRequestVo);
        int result = communityService.createBook(bookRequestVo);
        if (result==1){
            return new ResponseEntity<BasicResponse>(BasicResponse.builder().code(200).message("플립북 생성 완료 ").result(1).build(), HttpStatus.OK);
        }else{
            return new ResponseEntity<BasicResponse>(BasicResponse.builder().code(500).message("플립북 생성 실패 ").result(-1).build(), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @PostMapping("/bookTest")
    public ResponseEntity<BasicResponse> createBookTest(BookRequestVo bookRequestVo) {
        log.info("[플립북 생성] 요청 VO {} ", bookRequestVo);
        log.info("[플립북 생성] 요청 file {} ", bookRequestVo.getFiles());
        for (int i = 0; i < 3; i++) {
            System.out.println(bookRequestVo.getFiles().get(i));
        }

        log.info("[플립북 생성] 요청 aifile {} ", bookRequestVo.getAifiles());
        return new ResponseEntity<BasicResponse>(BasicResponse.builder().code(200).message("플립북 생성 완료 ").result(1).build(), HttpStatus.OK);

    }
}
