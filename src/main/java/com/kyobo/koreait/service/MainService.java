package com.kyobo.koreait.service;

import com.kyobo.koreait.domain.dtos.BookDTO;
import com.kyobo.koreait.domain.dtos.CartDTO;
import com.kyobo.koreait.domain.dtos.HeartDTO;
import com.kyobo.koreait.domain.vos.BookVO;
import com.kyobo.koreait.mapper.MainMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class MainService {
    @Autowired
    private MainMapper mapper;

    //책 정보 가져오기
    public List<BookVO> get_all_books(){
        return mapper.get_all_books();
    }

    public BookVO get_book_by_isbn(String bookISBN){return mapper.get_book_by_isbn(bookISBN);}

    public BookDTO find_specific_book(
            String searchKeyword,
            int nowPage
    ) {
        List<BookVO> bookVOS = mapper.find_specific_book(searchKeyword);
        BookDTO bookDTO = new BookDTO(bookVOS,nowPage);
        return bookDTO;
    }


//    public BookDTO get_all_books_by

}
