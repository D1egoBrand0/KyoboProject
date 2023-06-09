//package com.kyobo.koreait.controller;
//
//import com.kyobo.koreait.domain.dtos.UploadBookDTO;
//import com.kyobo.koreait.domain.vos.BookVO;
//import lombok.extern.log4j.Log4j2;
//import org.springframework.beans.factory.annotation.Value;
//import org.springframework.security.core.parameters.P;
//import org.springframework.stereotype.Controller;
//import org.springframework.web.bind.annotation.GetMapping;
//import org.springframework.web.bind.annotation.PostMapping;
//import org.springframework.web.bind.annotation.RequestMapping;
//import org.springframework.web.multipart.MultipartFile;
//
//import java.io.File;
//import java.io.IOException;
//import java.util.List;
//
//
//@Log4j2
//@Controller
//@RequestMapping("/admin")
//public class AdminController {
//
//    @Value("${com.kyobo.koreait.upload.path}")  // lombok아니다
//    private String uploadPath;
//
//
//    @GetMapping("/upload")
//    public void upload_book(){
//        log.info("-----adminController의 업로드-------");
//    }
//
//    @PostMapping("/upload")
//    public void upload_book_data(UploadBookDTO uploadBookDTO) {
//        log.info("-----adminController의 book data 업로드-------");
//        log.info("-----uploadBookDTO ==> " + uploadBookDTO);
//        BookVO bookVO = uploadBookDTO.getBookVO();
//        save_book_image_data(bookVO.getISBN(), uploadBookDTO.getMainImagefile(), uploadBookDTO.getContentsImagefile());
//    }
//
//
//    private void save_book_image_data(
//            String dirName,
//            MultipartFile mainImageFile, MultipartFile contentsImageFile
//    )
//        {
//        File saveDirObj = new File(uploadPath, dirName);
////        files.forEach(file -> {
////            String originalFilename =  file.getOriginalFilename();
////            File saveDirObj = new File(uploadPath, dirName);
//
//        //해당 isbn폴더가 존재하지않는다면 만들어주기
//        if (!saveDirObj.exists()) {
//            saveDirObj.mkdir();
//        }
//
////        유저가 올린 파일의 확장자 형식을 가져온다.
//        String mainImageFileExtension = mainImageFile.getContentType().split("/")[1];
//        String contentsFileExtension = mainImageFile.getContentType().split("/")[1];
////        mainImageFile.getOriginalFilename();
////        mainImageFile.getResource().getDescription();
////        log.info(mainImageFile.getResource().getDescription());
//
////        유저가 올린 파일을 저장할 경로를 지정하여 파일 객체 생성
//        File saveFileObj = new File(saveDirObj, "main."+mainImageFileExtension);
//        File contentsFileObj = new File(saveDirObj, "contents." + contentsFileExtension);
//
//
////        해당 파일 객체를 실제 위치에 write한다.
//        try {
//            mainImageFile.transferTo(saveFileObj);
//            contentsImageFile.transferTo(contentsFileObj);
//        } catch (IOException e) {
//            throw new RuntimeException(e);
//        }
////
////            if(originalFilename !=null) {
////                File saveFileObj = new File(saveDirObj, originalFilename);
////                try{ file.transferTo(saveFileObj); }
////                catch (IOException e) { throw new RuntimeException(e); }
////            }
//
//
//    }
//}


package com.kyobo.koreait.controller;

import com.kyobo.koreait.domain.dtos.UploadBookDTO;
import com.kyobo.koreait.domain.vos.BookVO;
import com.kyobo.koreait.util.S3Uploader;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.multipart.MultipartFile;

import javax.annotation.security.PermitAll;
import java.io.File;
import java.io.IOException;
import java.util.Arrays;
import java.util.List;

@Log4j2
@Controller
@RequestMapping("/admin")
public class AdminController {

    @Autowired
    private S3Uploader s3Uploader;


    @Value("${com.kyobo.koreait.upload.path}")
    private String uploadPath;

    @PreAuthorize("isAuthenticated()")
    @GetMapping("/upload")
    public void upload_book(){
        log.info("==== upload 화면 ====");
    }

    @PreAuthorize("isAuthenticated()")
    @PostMapping("/upload")
    public void upload_book_data(UploadBookDTO uploadBookDTO){
        log.info(" ==== upload_book_data - 게시물 작성 ==== ");
        log.info("uploadBookDTO ==> " + uploadBookDTO);
        BookVO bookVO = uploadBookDTO.getBookVO();
        List<String> fileNames = save_book_image_data(bookVO.getISBN(), uploadBookDTO.getMainImagefile(), uploadBookDTO.getContentsImagefile());
        List<String> uploadImageUrls = s3Uploader.upload(bookVO.getISBN(), uploadPath, fileNames);
        log.info(uploadImageUrls);
    }

    private List<String> save_book_image_data(String dirName, MultipartFile mainImageFile, MultipartFile contentsImageFile){
        File saveDirObj = new File(uploadPath, dirName);
        // 해당 ISBN 폴더가 존재하지 않는다면 폴더를 만들어준다
        if(!saveDirObj.exists()){
            saveDirObj.mkdir();
        }
        // 유저가 올린 파일의 확장자 형식을 가져온다
        String mainImageFileExtension =  mainImageFile.getContentType().split("/")[1];
        String contentsFileExtension =  mainImageFile.getContentType().split("/")[1];
        // 유저가 올린 파일을 저장할 경로를 지정하여 파일 객체 생성
        File saveFileObj = new File(saveDirObj, "main." + mainImageFileExtension);
        File contentsFileObj = new File(saveDirObj, "contents." + contentsFileExtension);
        // 해당 파일 객체를 실제 위치에 write 한다
        try {
            mainImageFile.transferTo(saveFileObj);
            contentsImageFile.transferTo(contentsFileObj);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        return Arrays.asList("main."+mainImageFileExtension, "contents." + contentsFileExtension);
    }
}









