package com.example.wearVillage.dataController;

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

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.multipart.MultipartFile;

import com.example.wearVillage.AttachImageVO;

public class uploadTest {
    private static final Logger logger = LoggerFactory.getLogger(uploadTest.class);

    @PostMapping(value = "/uploadTest", produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
    public ResponseEntity<List<AttachImageVO>> uploadTest(MultipartFile[] uploadFile) {
        {
            System.out.println("응답받아쪙");
            logger.info("uploadTestPOST............");
            for (MultipartFile multipartFile : uploadFile) {
                File checkfile = new File(multipartFile.getOriginalFilename());
                String type = null;
                try {
                    type = Files.probeContentType(checkfile.toPath());
                    logger.info("MIME TYPE: " + type);
                } catch (IOException e) {
                    e.printStackTrace();
                }
                if (!type.startsWith("image")) {
                    List<AttachImageVO> list = null;
                    return new ResponseEntity<>(list, HttpStatus.BAD_REQUEST);
                }
            }

            for (int i = 0; i < uploadFile.length; i++) {
                logger.info("------");
                logger.info("파일 이름: " + uploadFile[i].getOriginalFilename());
                logger.info("파일 타입: " + uploadFile[i].getContentType());
                logger.info("파일 크기 :" + uploadFile[i].getSize());
            }

            // 내가 업로드 파일을 저장할 경로

            String uploadFolder = "/home/ubuntu/upload";  // 경로를 EC2 인스턴스에 맞게 변경
            SimpleDateFormat sdf = new SimpleDateFormat("yyyy/MM/dd");  // 날짜 형식을 Linux 스타일에 맞게 변경
            Date date = new Date();
            String str = sdf.format(date);
            String datePath = str.replace("/", File.separator);
            File uploadPath = new File(uploadFolder, str);
            if (uploadPath.exists() == false) {
                uploadPath.mkdirs();
            }

            List<AttachImageVO> list = new ArrayList();
            for (MultipartFile multipartFile : uploadFile) {
                AttachImageVO vo = new AttachImageVO();
                String uploadFileName = multipartFile.getOriginalFilename();
                vo.setFileName(uploadFileName);
                vo.setUploadPath(datePath);
                // 저장할 파일, 생성자로 경로와 이름을 지정해줌.
                String uuid = UUID.randomUUID().toString();
                vo.setUuid(uuid);
                uploadFileName = uuid + "_" + uploadFileName;
                File saveFile = new File(uploadPath, uploadFileName);
                File thumbnailFile = new File(uploadPath, "s_" + uploadFileName);

                try {

                    multipartFile.transferTo(saveFile);
                    BufferedImage bo_image = ImageIO.read(saveFile);
                    double ratio = 3;
                    int width = (int) (bo_image.getWidth() / ratio);
                    int height = (int) (bo_image.getHeight() / ratio);
                    BufferedImage bt_image = new BufferedImage(width, height, BufferedImage.TYPE_3BYTE_BGR);
                    Graphics2D graphic = bt_image.createGraphics();
                    graphic.drawImage(bo_image, 0, 0, width, height, null);
                    ImageIO.write(bt_image, "jpg", thumbnailFile);
                    // void transferTo(File dest) throws IOException 업로드한 파일 데이터를 지정한 파일에 저장

                } catch (Exception e) {
                    logger.error("Error occurred while file uploading", e);
                }
                list.add(vo);

                // (의존성 thunmnailatr 주입할시, thumbnailFile import할것)
                // try{
                //   multipartFile.transferTo(saveFile);
                //   BufferedImage bo_image = ImageIO.read(saveFile);
                //   double ratio = 3;
                //   int width = (int) (bo_image.getWidth() / ratio);
                //   int height = (int) (bo_image.getHeight() / ratio);
                //   File thumbnailFile = new File(uploadPath,"s_"+uploadFileName);
                //   thumbnails.of(saveFile).size(width,height).toFile(thumbnailFile);
                // } catch (Exception e) {
                //   e.printStackTrace();
                // }
            }
            ResponseEntity<List<AttachImageVO>> result = new ResponseEntity<List<AttachImageVO>>(list,
                    HttpStatus.OK);

            return result;
        }
    }
}