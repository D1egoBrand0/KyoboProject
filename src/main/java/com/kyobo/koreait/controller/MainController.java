package com.kyobo.koreait.controller;

import com.kyobo.koreait.domain.vos.BookVO;
import com.kyobo.koreait.service.MainService;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.annotation.security.PermitAll;
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
       return "/main";
    }

    @ResponseBody
    @GetMapping("/main/books")
    public List<BookVO> get_all_books(){
        return mainService.get_all_books();
    }

    @ResponseBody
    @PostMapping("/main/cart")
    public boolean insert_cart(@RequestParam("bookArray") List<BookVO> bookVOS){
        log.info(bookVOS);
//        mainService.insert_cart(bookVOS);
//        on duplicate key update SQL문 활용하기

        return false;
    }

}