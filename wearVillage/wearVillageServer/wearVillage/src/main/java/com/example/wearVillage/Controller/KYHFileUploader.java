package com.example.wearVillage.Controller;

import com.example.wearVillage.Entity.USER_INFO;
import com.example.wearVillage.Repository.Repository_USER_INFO;
import com.example.wearVillage.status.local_or_server;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.http.util.ByteArrayBuffer;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.util.FileCopyUtils;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
import java.sql.Blob;
import java.util.ArrayList;
import java.util.Base64;
import java.util.List;
import java.util.UUID;

@Controller
@Slf4j
@RequiredArgsConstructor
public class KYHFileUploader {
    private final Repository_USER_INFO userInfo;
    @PostMapping("/chatimg")
    @ResponseBody
    public List<ResponseEntity<String>> KYH_ChatImg(List<MultipartFile> files) throws IOException {
        List<ResponseEntity<String>> filePath = new ArrayList<>();

        String local = "C:\\upload\\";
        String server = "/home"+File.separator+"ubuntu"+File.separator+"upload"+File.separator;

            for (MultipartFile file : files) {
                String p = File.separator;
                File imgFile = new File(local_or_server.status.equals("local")?local:server, UUID.randomUUID() + "_" + file.getOriginalFilename());
                file.transferTo(imgFile);

                filePath.add(new ResponseEntity<>(imgFile.getPath(), HttpStatus.OK));
            }


        return filePath;

    }

        @GetMapping("/getprofileimg")
        @ResponseBody
        public byte[] getProfileImg(@RequestParam String userNickname) throws IOException {
            log.info("겟프로필 이미지 유저네임 : {}",userNickname);

        USER_INFO searchRow = userInfo.findByNICKNAME(userNickname);
        String fileName = searchRow.getPROFILEIMG();

        try {

            log.info("겟프로필 이미지 파일네임 : {}", fileName);
            String fileNameDecode = URLDecoder.decode(fileName, "UTF-8");
            log.info("디코딩된 문자열:{}", fileNameDecode);
            String fileNameDecodeSeparator = fileNameDecode.replace("/", File.separator);
            log.info("겟프로필 이미지 파일디코드네임 : {}", fileNameDecodeSeparator);
            File file = new File(local_or_server.status.equals("local") ? "C:\\upload\\" : "/home/ubuntu/profileImage/", fileNameDecodeSeparator);
            if(file.exists()){
                file.mkdirs();
                log.info("mkdirs로 파일 만들어짐");
            }
            log.info("리턴 값 정보:{}",file.getPath());
            log.info("파일 정보:{}",file);
            log.info("파일 정보:{} to String",file.toString());

            byte[] result = FileCopyUtils.copyToByteArray(file);
            log.info("카피투바이트어레이 성공");
            return result;
        } catch (IOException e){
            log.info("KYHFileUploader.getProfileImg에서 예외 발생");
            e.printStackTrace();

        }
        return new byte[0];
    }

    @GetMapping("/getchatimg")
    public ResponseEntity<byte[]> getChatImg(@RequestParam String filePath) throws IOException {
        log.info("filePath 경로 : {}",filePath);
        File file = new File(filePath);
        return new ResponseEntity<>(FileCopyUtils.copyToByteArray(file),HttpStatus.OK);

    }

}
