package com.kyobo.koreait;

import com.kyobo.koreait.util.S3Uploader;
import lombok.extern.log4j.Log4j2;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@Log4j2
@SpringBootTest
class KyoboProjectApplicationTests {

    @Test
    void contextLoads() {
    }


    @Autowired
    private S3Uploader s3Uploader;

//    @Test
//    public void s3_test_upload(){
//        try{
//            String filePath = "C:\\Users\\Administrator\\IdeaProjects\\새프로젝트\\upload savepoint\\1\\main.jpg";
//            String uploadName = s3Uploader.upload(filePath,);
//            log.info(uploadName);
//        } catch (Exception e) {
//            log.info("오류발생");
//        }
//    }

    @Test
    public void s3_test_upload(){
        try{
//                //String bookISBN, String dirPath, List<String> fileNames
//                String dirpath = "C:\\Users\\Administrator\\Desktop\\Web19 KSW\\spring files\\2";
//                String fileNames =
//                String uploadName = s3Uploader.upload(2, , Arrays.asList("", ""));
//            log.info(uploadName);
        }catch (Exception e){
            log.info("에러 발생!");
        }
    }



}
