package com.example.wearVillage.Controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.util.List;
import java.util.UUID;

@Controller

public class KYHFileUploader {
    @PostMapping("/chatimg")
    public void KYH_ChatImg(List<MultipartFile> files) throws IOException {
        for(MultipartFile file : files){
            String p = File.separator;
            File imgFile = new File("C:"+p+"upload"+p,UUID.randomUUID()+"_"+file.getOriginalFilename());
            file.transferTo(imgFile);
        }
//
//        multipartFile.transferTo();
//
    }
}
