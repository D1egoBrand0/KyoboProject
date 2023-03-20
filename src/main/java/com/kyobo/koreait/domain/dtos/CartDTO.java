package com.kyobo.koreait.domain.dtos;

import com.kyobo.koreait.domain.vos.BookVO;
import com.kyobo.koreait.domain.vos.CartVO;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

@Data
@ToString
@NoArgsConstructor
@AllArgsConstructor
public class CartDTO {
//    private CartVO cartVO;
//    private BookVO bookVO;

    private int no;
    private int bookCount;
    private String bookISBN;
    private String title;
    private String price;
}
