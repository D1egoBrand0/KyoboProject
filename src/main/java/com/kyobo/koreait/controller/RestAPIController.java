package com.kyobo.koreait.controller;

import com.kyobo.koreait.service.RestAPIService;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpSession;

@Log4j2
@RestController
@RequestMapping("/api")
public class RestAPIController {

    @Autowired
    private RestAPIService restAPIService;

//    이메일 중복체크
    @GetMapping("/user/{email}")
    public boolean user_email_confirm(
            @PathVariable("email") String userEmail,
            HttpSession httpSession
    ){
//        세션에 인증받은 이메일을 저장한다.  ==> 유저가 존재하지 않으면 저장
//        유저가 존재하면 true, 존재하지않으면 false 반환
        if(!restAPIService.get_user(userEmail)){
            httpSession.setAttribute("emailAuthenticated",userEmail);
            return false;
        }
        return true;
    }


//인증번호 제대로 입력했는지 확인하는 메소드(세션에 저장한 것과 비교)
    @GetMapping("/sms")
    public boolean authenticate_sms_confirm(
            @RequestParam("authNumber") String userInputAuthenticateNumber,
            HttpSession httpSession
    ){
        String authenticateNumber = (String) httpSession.getAttribute("authenticateNumber");
        if(authenticateNumber == null){
            return false;
        }
        //세션이 가지고 있는 번호와 사용자가 입력한 값이 같다면
        if(userInputAuthenticateNumber.equals(authenticateNumber)){
            httpSession.removeAttribute("authenticateNumber");
            httpSession.setAttribute("phoneAuthenticated", true);
            return true;
        }
        //       서버가 가지고 있는 인증번호와 사용자가 입력한 인증번호를 비교한 결과를 반환한다.
//        return RestAPIService.sms_authenticate_number.equals(authenticateNumber);
        return false;
    }


    @GetMapping("/sms/{phone_number}")
    public String authenticate_sms(
            @PathVariable String phone_number,
            HttpSession httpSession
    ){
        try {
            String authenticateNumber = restAPIService.sendMessage(phone_number);
            httpSession.setAttribute("authenticateNumber", authenticateNumber);
            httpSession.setAttribute("phoneAuthenticated", false);
            httpSession.setAttribute("phoneAuthenticatedNumber", phone_number);
            return "succeed";
        } catch (Exception e) {
            log.info("인증번호 전송 시 오류 발생");
//            return "none";
        }
//        return RestAPIService.sms_authenticate_number;
        return "failed";
    }

}
