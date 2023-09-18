package com.example.wearVillage.Controller;



import static com.example.wearVillage.dataController.LoginCheck.*;
import static com.example.wearVillage.dataController.check_email.*;
import static com.example.wearVillage.dataController.check_id.*;
import static com.example.wearVillage.dataController.createUserToOracle.*;

import java.util.List;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.example.wearVillage.PostData;

@org.springframework.stereotype.Controller
// @RequiredArgsConstructor

public class Controller {

    private final JdbcTemplate jdbcTemplate;
    @Autowired
    public Controller(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    // 메인화면으로 이동
    @GetMapping(value = "/")
    public String home() {
        return "main.html";
    }

    //로그인 방식에 따라 회원가입 양식을 자동으로 채워주는 API
    @PostMapping(value = "/createUser")
    public String data_to_createUser(@RequestParam (required = false) String email, @RequestParam (required = false) String profile_img, Model model) {
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
        return check_email(email)? "true":"false";
    }

    // 옷빌리지 회원가입 (외부 API 사용 X)
    @GetMapping(value = "/createUser")
    public String default_createUser(Model model) {
        // 로그인 API를 경유하지 않고 곧장 회원가입 눌렀을 때
        model.addAttribute("testStyle", "border-bottom: solid 2px var(--color-wear_gray);");
        model.addAttribute("profile_img", "img/기본프사.jpg");
        return "createUser.html";
    }

    //회원가입시 메인화면으로 이동하기 위한 코드 (구현완료)
    @PostMapping(value = "/")
    public String finished_create_user(@RequestParam String userId,@RequestParam String userPassword, @RequestParam String email) {
            dataToOracle(email, userId, userPassword);
            return "main.html";
    }
    @GetMapping(value = "/chat")
    public String chat() {
        System.out.println("확인2");
        return "chat.html";
    }

    //아이디 중복검사
    @PostMapping(value ="/checkID")
    @ResponseBody
    public boolean checkID(@RequestParam String id_box){
        return check_id(id_box);
    }

    //아이디 비밀번호 체크 후 있을시 True반환
    @PostMapping(value ="/Dologin")
    @ResponseBody
    public boolean Dologin(@RequestParam String id, @RequestParam String password){
        return login_check(id,password);
    }

    //로그인 화면으로 이동
    @RequestMapping(value ="/login")
    public String login(){
        return "login";
    }

    @GetMapping(value = "/items_buy")
    public String items_buy(){
        return "items_buy.html";
    }

    @GetMapping(value = "/https_healthy_check")
    public String healthy_check(){
        return "main.html";
    }

    @GetMapping(value ="/posting")
    public String posting(){
        return "posting";
    }

    @GetMapping("/map_popup")
    public String makePopup() {
        return "maps.html";
    }

    @GetMapping("/map_popup2")
    public String map2() {
        return "maps2.html";
    }


    @GetMapping("/login_session")
    @ResponseBody
    public String loginSession(@RequestParam String id){
        System.out.println(id);
        return id;
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

    @GetMapping("/viewPost2")
    public ModelAndView viewPost2(@RequestParam("id") Integer id) {
        // 게시글 조회
        String selectQuery = "SELECT * FROM POSTING_TABLE WHERE POST_ID = ?";
        PostData postData = jdbcTemplate.queryForObject(selectQuery, new BeanPropertyRowMapper<>(PostData.class), id);

        ModelAndView modelAndView = new ModelAndView("postDetail2");
        modelAndView.addObject("post", postData);

        return modelAndView;
    }

    // 모든 게시글을 DB에서 가져와서 List화 시키기
    
    
    @GetMapping("/posts")
    public ModelAndView listPosts() {
        String sql = "SELECT * FROM POSTING_TABLE";
        List<PostData> posts = jdbcTemplate.query(sql, new BeanPropertyRowMapper<>(PostData.class));

        for (PostData post : posts) {
            String postText = post.getPostText();

            Document doc = Jsoup.parse(postText);
            Element img = doc.select("img").first();

            if (img != null) {
                String imgUrl = img.attr("src");
                post.setPostThumbnailUrl(imgUrl);
            }
        }

        ModelAndView modelAndView = new ModelAndView("items_buy");
        modelAndView.addObject("posts", posts);

        return modelAndView;
    }

}
