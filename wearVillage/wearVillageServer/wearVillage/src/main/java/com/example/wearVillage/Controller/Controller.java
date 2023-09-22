package com.example.wearVillage.Controller;



import static com.example.wearVillage.dataController.LoginCheck.*;
import static com.example.wearVillage.dataController.check_email.*;
import static com.example.wearVillage.dataController.check_id.*;
import static com.example.wearVillage.dataController.createUserToOracle.*;


import com.example.wearVillage.Entity.USER_INFO;
import com.example.wearVillage.PostData;

import com.example.wearVillage.Repository.Repository_USER_INFO;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import java.util.ArrayList;
import java.util.List;

@org.springframework.stereotype.Controller
@CrossOrigin(origins = { "http://localhost:8090/posting", "http://localhost:8090/maps",
        "http://localhost:8090/map_popup",
        "http://localhost:8090/map_popup2" ,"*"})
//@RequiredArgsConstructor

public class Controller {

    private final JdbcTemplate jdbcTemplate;
    private final Repository_USER_INFO rep_user_info;


    @Autowired
    public Controller(JdbcTemplate jdbcTemplate, Repository_USER_INFO rep_user_info) {
        this.jdbcTemplate = jdbcTemplate;
        this.rep_user_info = rep_user_info;
    }

    // 메인화면으로 이동
    @GetMapping(value = "/")
    public String home() {
        return "main.html";
    }

    //로그인에 성공할 시 메인화면으로 이동
    @PostMapping(value = "/login_createSession")
    public String loginSession(@RequestParam String email,HttpServletRequest request) {
        HttpSession session = request.getSession();
        session.setAttribute("email",email);
        System.out.println(session.getAttribute("email")+"세션 등록 완료");

        return "redirect:/";
    }

    //로그인 방식에 따라 회원가입 양식을 자동으로 채워주는 API
    @PostMapping(value = "/createUser")
    public String data_to_createUser(@RequestParam (required = false) String email, @RequestParam (required = false) String profile_img, Model model) {
        // 외부 API로그인에 성공했을때
//            model.addAttribute("user_email", email);
//            model.addAttribute("email_readonly", "true");
//            model.addAttribute("testStyle", "background-color: var(--color-wear_gray);");
//            model.addAttribute("profile_img", profile_img);
            return "createUser.html";
    }
    @ResponseBody
    @PostMapping("/use_api")

    public List<USER_INFO> use_api(@RequestParam(required = false) String email, HttpSession session) {
        return rep_user_info.findByEMAIL(email);

    }

    // 옷빌리지 회원가입 (외부 API 사용 X)
    @GetMapping(value = "/createUser")
    public String default_createUser(Model model) {
        // 로그인 API를 경유하지 않고 곧장 회원가입 눌렀을 때
        model.addAttribute("testStyle", "border-bottom: solid 2px var(--color-wear_gray);");
        model.addAttribute("profile_img", "img/기본프사.jpg");
        return "memberjoin.html";
    }

    //회원가입시 메인화면으로 이동하기 위한 코드 (구현완료)


    //회원가입 할 때 아이디란 onblur시 아이디 중복검사
    @PostMapping(value ="/checkID")
    @ResponseBody
    public Boolean checkID(@RequestParam String id_box){
        return rep_user_info.existsByID(id_box);
    }

    //아이디 비밀번호 체크 후 있을시 True반환하고 로그인 성공
    @PostMapping(value ="/Dologin")
    @ResponseBody
    public List<USER_INFO> Dologin(@RequestParam String id, @RequestParam String password){
        List<USER_INFO> userinfo = new ArrayList<>(rep_user_info.findByIDAndPW(id, password));
        if(userinfo.size()!=0) {
            System.out.println(userinfo.get(0).getID());
        }
        return rep_user_info.findByIDAndPW(id, password);
    }

    //로그인 화면으로 이동
    @GetMapping(value ="/login")
    public String login(){
        return "login.html";
    }

    @GetMapping(value = "/items_buy")
    public String items_buy(){
        return "items_buy.html";
    }


    @GetMapping(value ="/posting")
    public String posting(){
        return "posting";
    }

    @GetMapping(value = "/map_popup")
    public String makePopup() {
        return "maps.html";
    }

    @GetMapping("/map_popup2")
    public String map2() {
        return "maps2.html";
    }



    @GetMapping("/viewPost")
    public ModelAndView viewPost(@RequestParam Integer id) {
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


    @GetMapping(value = "/https_healthy_check")
    public String healthy_check(){
        return "main.html";
    }


}
