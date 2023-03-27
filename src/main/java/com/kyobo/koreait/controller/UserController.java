package com.kyobo.koreait.controller;

import com.kyobo.koreait.domain.dtos.CartDTO;
import com.kyobo.koreait.domain.dtos.HeartDTO;
import com.kyobo.koreait.domain.dtos.OrderDTO;
import com.kyobo.koreait.domain.vos.CartVO;
import com.kyobo.koreait.domain.vos.PaymentVO;
import com.kyobo.koreait.domain.vos.UserVO;
import com.kyobo.koreait.mapper.UserMapper;
import com.kyobo.koreait.service.UserService;
import com.sun.org.apache.xpath.internal.operations.Bool;
import lombok.Getter;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpSession;
import java.util.List;

@Log4j2
@Controller
@RequestMapping("/user")
public class UserController {

    @Autowired
    private UserService userService;
    @Autowired
    private UserMapper userMapper;


    @GetMapping("/login")
    public void login_user_get(){    }
    @PostMapping("/login")
    public String login_user_post(){
        return "redirect:/";
    }



    @GetMapping("/register")
    public void register_user(){    }
    @PostMapping("/register")
    public String register_user(
            @Validated UserVO userVO,
            BindingResult bindingResult,
            HttpSession httpSession
    ){
        log.info("------UserController에서 POST매핑 register");
        log.info("받아온 userVO => " + userVO);
        if(bindingResult.hasErrors()){
            log.info("---------------bindingresult에서 에러발생");
            return "/error/main";
        }

//        인증받은 인증번호와 중복체크한 유저이메일(아이디)를 가져온다.


        Boolean phoneAuthenticated = (Boolean) httpSession.getAttribute("phoneAuthenticated"); //인증이 되었는지 체크
        String phoneAuthenticatedNumber = (String) httpSession.getAttribute("phoneAuthenticatedNumber");  // 휴대폰 번호 일치 체크
        String userEmail = (String) httpSession.getAttribute("emailAuthenticated");    //이메일 일치 체크
        if (
                phoneAuthenticated == null //휴대폰 인증을 못받았거나
                || userEmail == null     //이메일 중복체크를 하지 않았거나
                || !phoneAuthenticated      // 인증이 false이거나 (실패했거나)
                || !userVO.getEmail().equals(userEmail)  // 인증받은 이메일과 가입할 이메일이 다르거나
                || !userVO.getPhone().equals(phoneAuthenticatedNumber)  //인증받은 휴대폰과 가입할 휴대폰이 다르거나
        ) {
            log.info("에러");
            return "/error/main";
        }
        log.info("--------- UserController에서 등록 시작 중");
        userService.register_user(userVO);
        log.info("--------- UserController에서 등록성공");
        return "redirect:/";
    }


    @PreAuthorize("isAuthenticated()")
    @GetMapping("/mypage/order")
    public void mypage_user(){
        log.info(" ---------- 마이페이지 컨트롤러-------");
    }
    @PreAuthorize("isAuthenticated()")
    @GetMapping("/mypage/order/main/")
    public void mypage_orde_main(
            @AuthenticationPrincipal UserDetails userDetails,
            Model model
    ){
        log.info(" ---------- 마이페이지 컨트롤러-------");
        log.info("  ====== mypage_order - 유저의 마이페이지 - 주문/배송 메인화면 =======");
//        해당 유저가 결제한 결제 내역들을 가져온다.(결제 내역만 가져오고 상세는 없음)
//        주문한 유저정보, 결제일시, 총 가격, 주문 번호만 가지고 있음
        List<PaymentVO> paymentVOS = userService.get_payment(userDetails.getUsername());
        model.addAttribute("paymentVOS",paymentVOS);
    }




    @PreAuthorize("isAuthenticated()")
    @GetMapping("/mypage/order/detail/{orderNo}")
    public void mypage_order_detail(
            @AuthenticationPrincipal UserDetails userDetails,
            @PathVariable String orderNo,
            Model model
    ){
        log.info(" ---------- 유저 컨트롤러 -------");
        log.info("  ====== mypage_order - 유저의 마이페이지 - 상세페이지 =======");
        List<CartDTO> cartDTOS = userService.get_order(orderNo);
        model.addAttribute("cartDTOS",cartDTOS);

    }

    @PreAuthorize("isAuthenticated()")
    @GetMapping("/logout")
    public void logout(){   log.info("---------로그아웃----------");    }




//    여기서 메인으로 보내지말고 메인에서는 get매핑으로 단순하게 보고
    @ResponseBody
    @GetMapping("/cart")
    public List<CartDTO> get_cart(
            @AuthenticationPrincipal UserDetails userDetails
    ){
        log.info("-----------장바구니 오는거 맞아?---------");
        return userService.get_cart(userDetails.getUsername());
    }
    @ResponseBody
    @PostMapping("/cart")
    public boolean insert_cart(
            @AuthenticationPrincipal UserDetails userDetails,
            @RequestBody List<CartVO> cartVOS
    ){
        log.info(" ==== insert_cart ==== ");
        return userService.insert_books_in_cart(userDetails, cartVOS);
//        on duplicate key update SQL문 활용하기
    }

    @ResponseBody
    @PutMapping("/cart")
    public boolean modify_cart(
            @AuthenticationPrincipal UserDetails userDetails,
            @RequestBody CartVO cartVO
    ){
        log.info("-*----------------modify_ cart------------------");
        return userService.modify_book_count_in_cart(userDetails.getUsername(),cartVO);
    }


    @ResponseBody
    @DeleteMapping("/cart")
    public boolean delete_cart(
            @AuthenticationPrincipal UserDetails userDetails,
            @RequestBody List<CartVO> cartVOS
    ){
        log.info("-----------delete_cart 장바구니삭제, 유저컨트롤러---------");
        return userService.delete_book_in_cart(userDetails.getUsername(), cartVOS);
    }

    @ResponseBody
    @PostMapping("/main/heart")
    public boolean insert_heart(
            @AuthenticationPrincipal UserDetails userDetails,
            @RequestBody List<HeartDTO> heartDTOS
    ){
        log.info(" ===== insert_heart ===== ");
        return userService.insert_books_in_heart(userDetails, heartDTOS);
    }


//    ---------------주문관련---------------------
    @ResponseBody
    @PreAuthorize("isAuthenticated()")
    @PostMapping("/order")
    public String insert_order(
            @AuthenticationPrincipal UserDetails userDetails,
            @RequestBody OrderDTO orderDTO
    ){
        log.info("----insert_order 상품주문하기--------");
//        현재 로그인 된 유저 정보와 자바스크립트에서 받아온 DTO 객체 정보를 넘겨줌
        boolean orderResult = userService.insert_payment_order(userDetails.getUsername(),orderDTO);
        if(!orderResult){
            return "/error/main";
        }
        return "/main/order";
    }

}
