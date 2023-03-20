package com.kyobo.koreait;

import com.kyobo.koreait.mapper.UserMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class KyoboProjectApplication {

    public static void main(String[] args) {
        SpringApplication.run(KyoboProjectApplication.class, args);
    }


//    @Autowired
//    UserMapper userMapper;


}
