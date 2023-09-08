package com.example.wearVillage.Controller;
import static com.example.wearVillage.dataContoller.LoginCheck.*;
import static com.example.wearVillage.dataContoller.check_email.*;
import static com.example.wearVillage.dataContoller.check_id.*;
import static com.example.wearVillage.dataContoller.createUserToOracle.*;
import static com.example.wearVillage.dataContoller.imgToOracle.*;

import java.awt.Graphics2D;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.net.URLDecoder;
import java.nio.file.Files;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.UUID;

import javax.imageio.ImageIO;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.ui.Model;
import org.springframework.util.FileCopyUtils;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.ModelAndView;

import com.example.wearVillage.AttachImageVO;
import com.example.wearVillage.PostData;
import com.example.wearVillage.dataContoller.uploadTest;

@org.springframework.stereotype.Controller
@CrossOrigin(origins = { "http://localhost:8090/posting", "http://localhost:8090/maps",
        "http://localhost:8090/map_popup" })
public class Controller {
    private static final Logger logger = LoggerFactory.getLogger(uploadTest.class);

    // 메인화면으로 이동
    @RequestMapping(value = "/")
    public String home() {
        return "main.html";
    }

    //로그인 방식에 따라 회원가입 양식을 자동으로 채워주는 API
    @PostMapping(value = "/createUser")
    public String data_to_createUser(@RequestParam(required = false) String email,
            @RequestParam(required = false) String profile_img, Model model) {
        // 외부 API로그인에 성공했을때
        model.addAttribute("user_email", email);
        model.addAttribute("email_readonly", "true");
        model.addAttribute("testStyle", "background-color: var(--color-wear_gray);");
        model.addAttribute("profile_img", profile_img);
        return "createUser.html";
    }

    @ResponseBody
    @PostMapping("/use_api")
    public String use_api(@RequestParam(required = false) String email) {
        return check_email(email) ? "true" : "false";
    }

    // 옷빌리지 회원가입 (외부 API 사용 X)
    @GetMapping(value = "/createUser")
    public String default_createUser(Model model) {
        // 로그인 API를 경유하지 않고 곧장 회원가입 눌렀을 때
        model.addAttribute("testStyle", "border-bottom: solid 2px var(--color-wear_gray);");
        model.addAttribute("profile_img", "img/기본프사.jpg");
        return "createUser.html";
    }

    //회원가입시 메인화면으로 이동하기 위한 코드 (아직 구현 중)
    @PostMapping(value = "/")
    public String finished_create_user(@RequestParam String userId, @RequestParam String userPassword,
            @RequestParam String email) {
        dataToOracle(email, userId, userPassword);
        return "main.html";
    }

    //아이디 중복검사
    @PostMapping(value = "/checkID")
    @ResponseBody
    public boolean checkID(@RequestParam String id_box) {
        return check_id(id_box);
    }

    //아이디 비밀번호 체크 후 있을시 True반환
    @PostMapping(value = "/Dologin")
    @ResponseBody
    public boolean Dologin(@RequestParam String id, @RequestParam String password) {
        return login_check(id, password);
    }

    //로그인 화면으로 이동
    @RequestMapping(value = "/login")
    public String login() {
        return "login";
    }

    @GetMapping(value = "/items_buy")
    public String items_buy() {
        return "items_buy.html";
    }

    @GetMapping(value = "/https_healthy_check")
    public String healthy_check() {
        return "main.html";
    }

    @GetMapping(value = "/posting")
    public String posting() {
        return "posting";
    }

    @ResponseBody
    @PostMapping(value = "/letsgo_oracle")
    public void letsgo(@RequestBody List<AttachImageVO> images) {
        for (AttachImageVO image : images) {
            String uploadPath = image.getUploadPath();
            String uuid = image.getUuid();
            String fileName = image.getFileName();

            System.out.println("uploadPath: " + uploadPath);
            System.out.println("uuid: " + uuid);
            System.out.println("fileName: " + fileName);

            imgdataToOracle(uploadPath, uuid, fileName);
        }
    }

    private final JdbcTemplate jdbcTemplate;

    @Autowired
    public Controller(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    @PostMapping("/postToOracle")
    public String postToOracle(@RequestBody PostData postData) {
        // 가장 최근의 postId 조회
        String idQuery = "SELECT MAX(POST_ID) FROM POSTING_TABLE";
        Integer maxId = jdbcTemplate.queryForObject(idQuery, Integer.class);

        // postId 값 증가
        int newId = (maxId != null ? maxId : 0) + 1;

        // 새로운 게시글 저장
        String insertQuery = "INSERT INTO POSTING_TABLE (POST_ID, POST_WRITER_ID, POST_SUBTITLE, POST_TEXT, POST_DATE, POST_MODIFY_DATE) VALUES (?, ?, ?, ?, ?, ?)";

        jdbcTemplate.update(insertQuery,
                newId,
                postData.getPostWriterId(),
                postData.getPostSubtitle(),
                postData.getPostText(),
                postData.getPostDate(),
                postData.getPostModifyDate());

        return "main.html";
    }

    @GetMapping("/viewPost")
    public ModelAndView viewPost(@RequestParam("id") Integer id) {
    // 게시글 조회
    String selectQuery = "SELECT * FROM POSTING_TABLE WHERE POST_ID = ?";
    PostData postData = jdbcTemplate.queryForObject(selectQuery, new BeanPropertyRowMapper<>(PostData.class), id);

    ModelAndView modelAndView = new ModelAndView("postDetail");
    modelAndView.addObject("post", postData);

    return modelAndView;
}

    // @PostMapping("/viewPost")
    // public String viewPost(@RequestBody PostData postData) {
    //     String selectQuery = "SELECT * FROM POSTING_TABLE";

    //     return "view_post.html"
    // }


    // @PostMapping("/postToOracle")
    // public String postToOracle(@RequestBody PostData postData) {
    //     postdataToOracle(postData.getPostId(), postData.getPostWriterId(),
    //                     postData.getPostSubtitle(), postData.getPostText(),
    //                     postData.getPostDate(), postData.getPostModifyDate());
    //     return "redirect:/login";
    // }

    @GetMapping("/map_popup")
    public String makePopup() {
        return "maps.html";
    }

    @PostMapping(value = "/uploadTest", produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
    // public void uploadTestPOST(MultipartFile[] uploadFile) {
    public ResponseEntity<List<AttachImageVO>> uploadTest(MultipartFile[] uploadFile) {
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
        String uploadFolder = "C:\\upload";
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        Date date = new Date();
        String str = sdf.format(date);
        String datePath = str.replace("-", File.separator);
        File uploadPath = new File(uploadFolder, datePath);
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

    @GetMapping("/display")
    public ResponseEntity<byte[]> getImage(String fileName) {
        File file = new File("c:\\upload\\" + fileName);
        ResponseEntity<byte[]> result = null;
        try {
            HttpHeaders header = new HttpHeaders();
            header.add("Content-type", Files.probeContentType(file.toPath()));
            result = new ResponseEntity<>(FileCopyUtils.copyToByteArray(file), header, HttpStatus.OK);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return result;
    }

    @PostMapping("/deleteFile")
    public ResponseEntity<String> deleteFile(String fileName) {
        logger.info("deleteFile......" + fileName);
        File file = null;

        try {
            /* 썸네일 파일 삭제 */
            file = new File("c:\\upload\\" + URLDecoder.decode(fileName, "UTF-8"));

            file.delete();

            /* 원본 파일 삭제 */
            String originFileName = file.getAbsolutePath().replace("s_", "");

            logger.info("originFileName : " + originFileName);

            file = new File(originFileName);

            file.delete();

        } catch (Exception e) {

            e.printStackTrace();

            return new ResponseEntity<String>("fail", HttpStatus.NOT_IMPLEMENTED);

        }
        return new ResponseEntity<String>("success", HttpStatus.OK);
    }

}
