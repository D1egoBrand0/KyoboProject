package com.kyobo.koreait.service;

import com.kyobo.koreait.domain.vos.BookVO;
import com.kyobo.koreait.mapper.MainMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class MainService {
    @Autowired
    private MainMapper mainMapper;

//    책 정보 가져오기
    public List<BookVO> get_all_books(){
        return mainMapper.get_all_books();
    }
////    장바구니 추가
//    public boolean insert_cart(List<BookVO> bookVOS){
//        return mainMapper.insert_cart(bookVOS);
//    }

}
