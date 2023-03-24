package com.kyobo.koreait.service;

import com.kyobo.koreait.domain.dtos.CartDTO;
import com.kyobo.koreait.domain.dtos.HeartDTO;
import com.kyobo.koreait.domain.dtos.OrderDTO;
import com.kyobo.koreait.domain.dtos.UserDTO;
import com.kyobo.koreait.domain.enums.OrderState;
import com.kyobo.koreait.domain.vos.CartVO;
import com.kyobo.koreait.domain.vos.PaymentVO;
import com.kyobo.koreait.domain.vos.UserVO;
import com.kyobo.koreait.mapper.UserMapper;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.annotation.Order;
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

//    장바구니 내용 가져오기
    public List<CartDTO> get_cart(String userEmail){
        return userMapper.get_cart(userEmail);
    }


//    도서 장바구니 담아놓은거 수정하기
    public boolean modify_book_count_in_cart(String useEmail, CartVO cartVO){
        cartVO.setUserEmail(useEmail);
        return userMapper.modify_book_count_in_cart(cartVO);
    }


    public boolean modify_book_count_in_cart_by_count(String useEmail, List<CartVO> cartVOS, OrderState orderState){
        cartVOS.parallelStream().forEach(cartVO -> cartVO.setUserEmail(useEmail));
//        두번도는 이유 제대로 매핑해주려고
        if(orderState == OrderState.ADD){
            cartVOS.parallelStream().forEach(cartVO -> cartVO.setBookCount(-cartVO.getBookCount()));
        }
        return userMapper.modify_book_count_in_cart_by_count(cartVOS);
    }

//   도서삭제하기
    public boolean delete_book_in_cart(String userEmail,List<CartVO> cartVOS){
        cartVOS.parallelStream().forEach(vo -> vo.setUserEmail(userEmail));
        return userMapper.delete_book_in_cart(cartVOS);
    }


    /********************   결제/주문 관련   *******************************/
//    결제내역 + 부분내역 추가하기 결제가 제대로 이루어지면 주문내역에 추가
    public boolean insert_payment_order(String userEmail, OrderDTO orderDTO){
//        주문유저정보 설정하기
        PaymentVO paymentVO = orderDTO.getPaymentVO();
        paymentVO.setUserEmail(userEmail);

        //현 장바구니 리스트 가져오기
        List<CartVO> cartVOS = orderDTO.getCartVOS();

//        결제 내역에 추가하기
        boolean paymentSucceeded = userMapper.insert_payment(orderDTO.getPaymentVO());
//        주문내역에 추가하기
        boolean orderSucceeded = userMapper.insert_order(orderDTO.getCartVOS());

//        장바구니에 있는 내역 수정하기
        boolean modifySucceeded = modify_book_count_in_cart_by_count(userEmail,cartVOS,OrderState.DELETE);


//        장바구니에 있는 주문 내역 삭제하기
        boolean removeSucceded = delete_book_in_cart(userEmail,cartVOS);

        return paymentSucceeded && orderSucceeded;
    }







}
