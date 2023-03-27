package com.kyobo.koreait.service;

import com.kyobo.koreait.domain.dtos.UserDTO;
import com.kyobo.koreait.domain.vos.UserVO;
import com.kyobo.koreait.mapper.UserMapper;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.oauth2.client.registration.ClientRegistration;
import org.springframework.security.oauth2.client.userinfo.DefaultOAuth2UserService;
import org.springframework.security.oauth2.client.userinfo.OAuth2UserRequest;
import org.springframework.security.oauth2.core.OAuth2AuthenticationException;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.stereotype.Service;

import java.util.LinkedHashMap;
import java.util.Map;

@Log4j2
@Service
public class CustomOAuth2UserService extends DefaultOAuth2UserService {

    @Autowired
    private UserMapper userMapper;

    @Override
    public OAuth2User loadUser(OAuth2UserRequest userRequest) throws
            OAuth2AuthenticationException{
        log.info("--------로드 유저---------");
        ClientRegistration clientRegistration = userRequest.getClientRegistration();
        String clientName = clientRegistration.getClientName();

        log.info("userRequest : " + userRequest);
        log.info("clientName : " + clientName);
        OAuth2User oAuth2User = super.loadUser(userRequest);
        Map<String, Object> paramMap = oAuth2User.getAttributes();

        String userEmail="";

        switch (clientName){
            case "kakao" :
                userEmail = get_kakao_userEamil(paramMap);
        }


//        paramMap.forEach((k,v) -> {
//            log.info("Attr key" + k);
//            log.info("Attr value" + v);
//        });
        return oAuth2User;
    }

//    카카오로 접속한 유저의 이메일을 반환하는 메소드
    private String get_kakao_userEamil(Map<String, Object> paramMap){
        log.info("카카오 유저 로그인. 유저 이메일 얻기");
        LinkedHashMap kakaoAccount =(LinkedHashMap) paramMap.get("kakao_account");
        String userEmail = (String) kakaoAccount.get("email");
        log.info("가져온 유저 이메일은 " + userEmail +"이다.");
        return userEmail;
    }

//    해당 카카오 이메일을 가지고 있는 유저가 있는지 검사, 해당 유저있으면 반환하는 메소드
    private UserDTO get_user_by_email(String userEmail){
        UserVO userVO = userMapper.get_user(userEmail);
        if(userVO == null){     // DB에 user_tbl의 id에 해당 이메일 유저가 없으면
            userMapper.register_user(userVO);   // 가입시키기
        }
        return null;
    }

}
