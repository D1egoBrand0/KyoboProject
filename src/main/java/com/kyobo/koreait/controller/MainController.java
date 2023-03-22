package com.kyobo.koreait.controller;

import com.kyobo.koreait.domain.dtos.BookDTO;
import com.kyobo.koreait.domain.dtos.CartDTO;
import com.kyobo.koreait.domain.dtos.HeartDTO;
import com.kyobo.koreait.domain.vos.BookVO;
import com.kyobo.koreait.domain.vos.CartVO;
import com.kyobo.koreait.service.MainService;
import com.kyobo.koreait.service.UserService;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import javax.annotation.security.PermitAll;
import java.awt.print.Book;
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
       return "/main/main";
    }

    @ResponseBody
    @GetMapping("/main/books")
    public List<BookVO> get_all_books(){
        return mainService.get_all_books();
    }


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
        return "/main/details";
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
    @GetMapping("/main/books/{searchKeyword}")
    public BookDTO find_specific_book(
            @PathVariable String searchKeyword,
            @RequestParam(defaultValue = "1") int nowPage
    ){
        log.info("--------------maincontroller의 단어 검색정보-------");
        return mainService.find_specific_book(searchKeyword, nowPage);

    }



}
