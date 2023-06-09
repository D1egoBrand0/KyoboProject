package com.kyobo.koreait.controller;

import com.kyobo.koreait.domain.dtos.BookDTO;
import com.kyobo.koreait.domain.dtos.CartDTO;
import com.kyobo.koreait.domain.dtos.HeartDTO;
import com.kyobo.koreait.domain.dtos.UploadFileDTO;
import com.kyobo.koreait.domain.vos.BookVO;
import com.kyobo.koreait.domain.vos.CartVO;
import com.kyobo.koreait.service.MainService;
import com.kyobo.koreait.service.UserService;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import javax.annotation.security.PermitAll;
import java.awt.print.Book;
import java.io.File;
import java.util.List;

@Log4j2
@Controller
public class MainController {
    @Autowired
    private MainService mainService;

    @PermitAll
    @GetMapping("/")
    public String main(){
       log.info(" -------  진짜 메인 페이지---------");
       return "main/main";
    }

//    @ResponseBody
//    @GetMapping("/main/books")
//    public List<BookVO> get_all_books(){
//        return mainService.get_all_books();
//    } // 이미있어서 주석처리


    @PermitAll
    @GetMapping("/main/naverMapTest")
    public void map_test(){}

//    @Value("${com.kyobo.koreait.upload.path}")
//    private String uploadPath;
//    @ResponseBody
//    @PostMapping("/upload")
//    public void upload_file(UploadFileDTO uploadFileDTO){
//        if(uploadFileDTO.getMultipartFiles() != null){
//            uploadFileDTO.getMultipartFiles().forEach(multipartFile -> {
//                File file = new File("C://test/a.jpg");
////                multipartFile.transferTo(file); // 익셉션 뜨지만, 정상작동은 한다고 함
//                log.info(multipartFile.getOriginalFilename());
//            });
//        }
//    }


    @PreAuthorize("isAuthenticated()")
    @GetMapping("/main/cart")
    public void cart(){ log.info("-------장바구니가즈아----------");}

    @PermitAll
    @GetMapping("/main/details/{bookISBN}")
    public String book_details(
            @PathVariable String bookISBN,
            Model model
    ){
        log.info(" -----------maincontroller의 책 상세정보-------------");
        BookVO bookVO = mainService.get_book_by_isbn(bookISBN);
        log.info("-------책의 상세정보다 => " + bookVO);
        if(bookVO == null){
            log.info("*------해당 책이 없어서 상세정보도 없음");
            return "/error/main";
        }
        model.addAttribute("bookVO",bookVO);
        return "main/details";
    }

//    @PermitAll
//    @GetMapping("/main/main")
//    public List<BookVO> search_word(
//            @PathVariable String search_word,
//            Model model
//    ){
//        log.info("--------------maincontroller의 단어 검색정보-------");
//        BookVO word = mainService.find_specific_book(search_word);
//
//        return "으악";
//    }
    @ResponseBody
//    @GetMapping("/main/books/{searchKeyword}")
    @GetMapping("/main/books")
    public BookDTO find_specific_book(
//            @PathVariable String searchKeyword,
            @RequestParam(defaultValue = "") String searchKeyword,
            @RequestParam(defaultValue = "rating") String order,
            @RequestParam(defaultValue = "8") int pagePerArticle,
            @RequestParam(defaultValue = "1") int nowPage
    ){
        log.info("--------------maincontroller의 단어 검색정보-------");
        return mainService.find_specific_book(searchKeyword, order, pagePerArticle, nowPage);

    }

//    장바구니에서 현재 주문내역 확인 페이지
    @PreAuthorize("isAuthenticated()")
    @GetMapping("/main/order")
    public void order_complete_page(){
        log.info("----------maincontroller 주문 확인----------");
    }



    @GetMapping("/accessdenied")
    public void access_denied(){
        log.info("접근거부");
    }

}
