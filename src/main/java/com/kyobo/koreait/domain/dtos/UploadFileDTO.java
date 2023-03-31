package com.kyobo.koreait.domain.dtos;

import com.kyobo.koreait.domain.vos.BookVO;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.ToString;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@Data
@ToString
@AllArgsConstructor
public class UploadFileDTO {
    private List<MultipartFile> multipartFiles;
}
