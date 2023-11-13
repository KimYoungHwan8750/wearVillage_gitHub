package com.example.wearVillage.Controller;

import org.apache.http.util.ByteArrayBuffer;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.sql.Blob;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Controller
public class KYHFileUploader {
    @PostMapping("/chatimg")
    @ResponseBody
    public List<ResponseEntity<String>> KYH_ChatImg(List<MultipartFile> files) throws IOException {
        List<ResponseEntity<String>> filePath = new ArrayList<>();



            for (MultipartFile file : files) {
                String p = File.separator;
                File imgFile = new File("C:\\Users\\seoye\\OneDrive\\바탕 화면\\영환이 작업 폴더\\프로젝트 폴더\\wearVillage_gitHub\\wearVillage\\wearVillageServer\\wearVillage\\src\\main\\resources\\static\\img\\test\\", UUID.randomUUID() + "_" + file.getOriginalFilename());
                file.transferTo(imgFile);

                filePath.add(new ResponseEntity<>(imgFile.getPath(), HttpStatus.OK));
            }


        return filePath;

    }
}
