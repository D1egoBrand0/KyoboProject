package com.kyobo.koreait.service;

import com.kyobo.koreait.domain.dtos.CartDTO;
import com.kyobo.koreait.domain.dtos.HeartDTO;
import com.kyobo.koreait.domain.dtos.UserDTO;
import com.kyobo.koreait.domain.vos.CartVO;
import com.kyobo.koreait.domain.vos.UserVO;
import com.kyobo.koreait.mapper.UserMapper;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Collection;
import java.util.Collections;
import java.util.List;

@Log4j2
@Service
public class UserService {
    @Autowired
    private UserMapper userMapper;
    @Autowired
    private PasswordEncoder passwordEncoder;


//    유저를 회원가입시키는 서비스 동작
//    public void register_user(UserVO userVO){
//        UserDTO userDTO =new UserDTO(
//                userVO.getEmail(),
////                비밀번호 인코딩
//                passwordEncoder.encode(userVO.getPassword()),
//                userVO.getName(),
//                userVO.getBirth(),
//                userVO.getPhone(),
//                Collections.singleton(new SimpleGrantedAuthority("ROLE_NONE"))
//        );
//        userMapper.regist_user(userDTO);
//    }

    public void register_user(UserVO userVO){
        userVO.setPassword(passwordEncoder.encode(userVO.getPassword()));
        userMapper.register_user(userVO);
    }



    //장바구니추가
    public boolean insert_books_in_cart(UserDetails userDetails, List<CartVO> cartVOS){
        cartVOS.parallelStream().forEach(cartVO -> cartVO.setUserEmail(userDetails.getUsername()));
        return userMapper.insert_books_in_cart(cartVOS);
    }

    //찜하기
    public boolean insert_books_in_heart(UserDetails userDetails, List<HeartDTO> heartDTOS){
        heartDTOS.parallelStream().forEach(heartDTO -> heartDTO.setUserEmail(userDetails.getUsername()));
        return userMapper.insert_books_in_heart(heartDTOS);
    }

//    장바구니 내용 가져요ㅗ기
    public List<CartDTO> get_cart(String userEmail){
        return userMapper.get_cart(userEmail);
    }




    public boolean modify_book_count_in_cart(String useEmail, CartVO cartVO){
        cartVO.setUserEmail(useEmail);
        return userMapper.modify_book_count_in_cart(cartVO);
    }







}
