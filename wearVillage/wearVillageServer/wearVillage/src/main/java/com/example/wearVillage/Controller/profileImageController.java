package com.example.wearVillage.Controller;

import java.awt.Graphics2D;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.UUID;

import javax.imageio.ImageIO;

import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.multipart.MultipartFile;

import com.example.wearVillage.AttachImageVO;
import com.example.wearVillage.status.local_or_server;

@org.springframework.stereotype.Controller
public class profileImageController {

  @PostMapping(value="/uploadProfileImage", produces=MediaType.APPLICATION_JSON_UTF8_VALUE)
  public ResponseEntity<List<AttachImageVO>> uploadProfileImage(MultipartFile uploadFile) {

    File checkfile = new File(uploadFile.getOriginalFilename());
    String type = null;
    try {
      type = Files.probeContentType(checkfile.toPath());
    } catch (IOException e) {
      e.printStackTrace();
    }
    if (!type.startsWith("image")) {
      List<AttachImageVO> list = null;
      return new ResponseEntity<>(list, HttpStatus.BAD_REQUEST);
    }

    // 업로드 폴더 설정하기
    String uploadFolder = local_or_server.status.equals("local") ? "c:\\upload\\" : "/home/ubuntu/profileImage/";

    // 업로드 폴더를 dateFormat 지정해서 넣어주기
    SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");

    Date date = new Date();

    String str = sdf.format(date);

    String datePath = str.replace("-", File.separator);
    // 이미지를 저장할 폴더 만들기
    File uploadPath = new File(uploadFolder, datePath);
    // 폴더가 있다면 만들지말기
    if (uploadPath.exists() == false) {
      uploadPath.mkdirs();
    }
    // 이미지 정보를 담는 객체
    List<AttachImageVO> list = new ArrayList();

    AttachImageVO vo = new AttachImageVO();

    // 파일의 이름 설정
    String uploadFileName = uploadFile.getOriginalFilename();

    // SETTER 메소드로 VO 객체에 저장하기
    vo.setFileName(uploadFileName);
    vo.setUploadPath(datePath);

    // 이름이 같다면 덮어씌워지니까 UUID포멧을 이용해 랜덤이름으로 바꾼다.
    String uuid = UUID.randomUUID().toString();
    vo.setUuid(uuid);
    uploadFileName = uuid + "_" + uploadFileName;

    // 파일 위치, 파일 이름을 합친 File 객체 지정
    File saveFile = new File(uploadPath, uploadFileName);

    // 썸네일 지정하기
    File thumbnailFile = new File(uploadPath, "s_" + uploadFileName);

    // 파일 저장하기
    try {
      uploadFile.transferTo(saveFile);
      BufferedImage bo_image = ImageIO.read(saveFile);
      double ratio = 1;
      int width = (int) (bo_image.getWidth() / ratio);
      int height = (int) (bo_image.getHeight() / ratio);
      BufferedImage bt_image = new BufferedImage(width, height, BufferedImage.TYPE_3BYTE_BGR);
      Graphics2D graphic = bt_image.createGraphics();
      graphic.drawImage(bo_image, 0, 0, width, height, null);
      ImageIO.write(bt_image, "jpg", thumbnailFile);
    } catch (Exception e) {
      e.printStackTrace();
    }
    // AttachImageVO 객체를 List의 요소로 추가해주기(멀티파트용)
    list.add(vo);

    ResponseEntity<List<AttachImageVO>> result = new ResponseEntity<List<AttachImageVO>>(list, HttpStatus.OK);

    return result;
  }
}
