package com.example.imgTest.web;

import java.io.File;
import java.io.IOException;
import java.nio.file.Paths;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

import lombok.extern.slf4j.Slf4j;
@Slf4j
@Controller
public class FileUploadController {

    @Value("${com.example.imgTest.upload.path}")
    private String uploadPath;
    
    @GetMapping("/")
    public String home() {
        log.info("home()");
        
        return "home";
    }
    
    @PostMapping("/image-upload")
    public ResponseEntity<?> uploadImage(@RequestParam("image") MultipartFile image) {
        log.info("uploadimg()");
        if (image.isEmpty()) {
            return ResponseEntity.ok().build();
        }
        
        String originalName = image.getOriginalFilename();                                         // 원본 파일명
        String uuid = UUID.randomUUID().toString().replaceAll("-", "");           // 32자리 랜덤 문자열
        String extension = originalName.substring(originalName.lastIndexOf(".") + 1);  // 확장자
        String saveFilename = uuid + "." + extension;                                             // 디스크에 저장할 파일명
        String fileFullPath = Paths.get(uploadPath, saveFilename).toString();                      // 디스크에 저장할 파일의 전체 경로


        try {
            // 파일 저장 (write to disk)
            File uploadFile = new File(fileFullPath);
            image.transferTo(uploadFile);
            return ResponseEntity.ok().body(saveFilename);

        } catch (IOException e) {
            // 예외 처리는 따로 해주는 게 좋습니다.
            throw new RuntimeException(e);
        }
    }

}
