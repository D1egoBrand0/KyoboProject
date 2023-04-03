package com.kyobo.koreait.util;

import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.AmazonS3Client;
import com.amazonaws.services.s3.model.CannedAccessControlList;
import com.amazonaws.services.s3.model.DeleteObjectRequest;
import com.amazonaws.services.s3.model.PutObjectRequest;
import com.kyobo.koreait.domain.vos.BookVO;
import lombok.RequiredArgsConstructor;

import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

@Log4j2
@Component
@RequiredArgsConstructor
public class S3Uploader {

    private final AmazonS3Client amazonS3Client;


    @Value("${cloud.aws.s3.bucket}")
    public String bucket; //내 버켓명

//    S3로 파일 업로드하기 => 업로드한 파일 URL리스트를 반환한다.
    public List<String> upload(String bookISBN, String dirPath, List<String> fileNames){
        List<String> uploadImageUrls = new ArrayList<>();

        fileNames.forEach( fileName -> {
            try {
                File targetFile = new File(dirPath + "\\" + bookISBN ,fileName);
                // S3로 업로드
                String uploadImageUrl = putS3(targetFile, bookISBN, targetFile.getName());
                // 원본파일 삭제
                removeOriginalFile(targetFile);
                removeOriginalDirectory(targetFile);
                // 업로드된 URL 리턴
                uploadImageUrls.add(uploadImageUrl);
            }catch (Exception e){
                log.info("--- 업로드중 오류발생 : " + fileName + "쪽임");
            }
        });
        return uploadImageUrls;
    }

    //S3로 업로드
    private String putS3(File uploadFile,String dirPath ,String fileName) throws Exception{
        log.info("------S3 업로드 시도중");
        //지정된 bucket의 S3에 해당 파일이름으로 해당 파일을 업로드한다.
        amazonS3Client.putObject(
                new PutObjectRequest(bucket, dirPath + "/" + fileName, uploadFile)
//                        .withCannedAcl(CannedAccessControlList.AuthenticatedRead.PublicRead)  );
                        .withCannedAcl(CannedAccessControlList.PublicRead)  );
        log.info("------S3 업로드 성공");
        return amazonS3Client.getUrl(bucket, fileName).toString();
    }



    // 업로드후 원본삭제
    private void removeOriginalFile(File targetFile){
        // 파일이 존재하고, 지웠다면
        if(targetFile.exists() && targetFile.delete()){
            log.info("-------원본 [파일] 삭제가 완료됨");
            return;
        }
        log.info("------원본 [파일] 삭제 실패");
    }

    // S3 업로드 후 원본 폴더 삭제
    private void removeOriginalDirectory(File targetDirectory){
        // 폴더가 존재하고, 지웠다면
        // isDirectory : 경로에 있는 객체가 폴더면 true
        if(targetDirectory.exists() && targetDirectory.isDirectory() && targetDirectory.delete()){
            log.info("-------원본 [폴더] 삭제가 완료됨");
            return;
        }
        log.info("------원본 [폴더] 삭제 실패");
    }

    // S3 서버에 업로드 되어있는 파일 삭제하기
    public void removeS3File(String fileName){
        log.info("---- [" + fileName + "] 삭제 요청중----" );
        // S3 버킷명과 파일명을 전달하여 삭제요청 생성
        DeleteObjectRequest deleteObjectRequest = new DeleteObjectRequest(bucket, fileName);
        // 삭제 요청 전송
        amazonS3Client.deleteObject(deleteObjectRequest);
        log.info("---- [" + fileName + "] 삭제 성공----" );
    }



}
