package com.kyobo.koreait.mapper;

import com.kyobo.koreait.domain.dtos.CartDTO;
import com.kyobo.koreait.domain.dtos.HeartDTO;
import com.kyobo.koreait.domain.vos.BookVO;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

@Mapper
public interface MainMapper {

    //책 정보 가져오기
    List<BookVO> get_all_books();

    BookVO get_book_by_isbn(String bookISBN);
//    책제목을 통합검색으로 찾기(DB로 찾아서 하는게 맞다.)
    List<BookVO> find_specific_book(
            @Param("searchKeyword") String searchKeyword,
            @Param("order") String order
    );
}
