package com.example.wearVillage.dataController;

import java.io.File;
import java.net.URLDecoder;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;

public class deleteFile {
  @PostMapping("/deleteFile")
    public ResponseEntity<String> deleteFile(String fileName) {
        // logger.info("deleteFile......" + fileName);
        File file = null;

        try {
            file = new File("c:\\upload\\" + URLDecoder.decode(fileName, "UTF-8"));
            file.delete();
            String originalFileName = file.getAbsolutePath().replaceFirst("s_", "");
            // logger.info("originalFileName : " + originalFileName);
            file = new File(originalFileName);
            file.delete();
        } catch (Exception e) {
            e.printStackTrace();
            return new ResponseEntity<String>("fail", HttpStatus.NOT_IMPLEMENTED);
        }
        return new ResponseEntity<String>("success", HttpStatus.OK);
    }
}
