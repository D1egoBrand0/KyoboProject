package com.kyobo.koreait.mapper;

import com.kyobo.koreait.domain.dtos.CartDTO;
import com.kyobo.koreait.domain.dtos.HeartDTO;
import com.kyobo.koreait.domain.dtos.UserDTO;
import com.kyobo.koreait.domain.vos.*;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;

import java.util.List;

@Mapper
public interface UserMapper {
    @Select("SELECT * FROM `kyobo_db`.`user_tbl` WHERE `email` = #{email}")
    UserVO get_user(String id);

    @Insert("INSERT INTO `kyobo_db`.`user_tbl` VALUES " +
            "(#{email}, #{password}, #{name}, #{birth}, #{phone}, default)")
    void register_user(UserVO userVO);


//    장바구니 내용 전부 가져오기
    List<CartDTO> get_cart(String userEmail);

//    장바구니에 있는 책의 개수 수정하기
    boolean modify_book_count_in_cart(CartVO cartVO);

    boolean modify_book_count_in_cart_by_count(List<CartVO> cartVOS);


//    장바구니에 있는 책 삭제하기
    boolean delete_book_in_cart(List<CartVO> cartVOS);

//    장바구니 추가
    boolean insert_books_in_cart(List<CartVO> cartVOS);

//    찜하기
    boolean insert_books_in_heart(List<HeartDTO> heartDTOS);

//    결제내역 추가하기
    boolean insert_payment(PaymentVO paymentVO);

//    주문내역 추가하기
    boolean insert_order(List<CartVO> cartVOS);



}






