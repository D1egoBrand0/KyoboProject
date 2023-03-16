package com.kyobo.koreait.mapper;

import com.kyobo.koreait.domain.dtos.UserDTO;
import com.kyobo.koreait.domain.vos.UserVO;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;

@Mapper
public interface UserMapper {
    @Select("SELECT * FROM `kyobo_db`.`user_tbl` WHERE `email` = #{email}")
    UserVO get_user(String id);

    @Insert("INSERT INTO `kyobo_db`.`user_tbl` (#{email}, #{password}, #{name}, #{birth}, #{phone}, default) VALUES " +
            "<foreach collection="">"




    )
    void register_user(UserVO userVO);
}






