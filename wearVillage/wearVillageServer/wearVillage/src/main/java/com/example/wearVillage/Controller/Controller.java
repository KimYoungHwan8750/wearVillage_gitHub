package com.example.wearVillage.Controller;




import static com.example.wearVillage.dataController.LoginCheck.*;
import static com.example.wearVillage.dataController.check_email.*;
import static com.example.wearVillage.dataController.check_id.*;
import static com.example.wearVillage.dataController.createUserToOracle.*;


import com.example.wearVillage.DTO.CreateUserDTO;
import com.example.wearVillage.Entity.USER_INFO;
import com.example.wearVillage.PostData;

import com.example.wearVillage.Repository.Repository_USER_INFO;
import jakarta.servlet.http.HttpSession;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import java.util.List;
import java.util.Map;

@org.springframework.stereotype.Controller
@CrossOrigin(origins = { "http://localhost:8090/posting", "http://localhost:8090/maps",
        "http://localhost:8090/map_popup",
        "http://localhost:8090/map_popup2" ,"*"})
//@RequiredArgsConstructor
@Slf4j
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
    @ResponseBody
    public String loginSession(@RequestBody Map<String,String> map,HttpSession session) {
        String email = map.get("email");
        USER_INFO user_info = rep_user_info.findByEMAIL(email).get(0);
        session.setAttribute("email",user_info.getEMAIL());
        session.setAttribute("id",user_info.getID());
        session.setAttribute("pw",user_info.getPW());
        session.setAttribute("theme",user_info.getTHEME());
        session.setAttribute("profileimg",user_info.getPROFILEIMG());
        session.setAttribute("nickname",user_info.getNICKNAME());
        session.setAttribute("gender",user_info.getGENDER());
        session.setAttribute("birth",user_info.getBIRTH());
        log.info((String) session.getAttribute("nickname")+"님이 로그인하셨습니다.");

        return "";
    }
    //카카오api회원가입
    @PostMapping("/createUserAPI")
    public String data_createUserApi2(
            @ModelAttribute CreateUserDTO createUserDTO, Model model){
        log.info("createUserDTO 내용 : {}", createUserDTO.toString());
        return "createUser";
    }
    //카카오api회원가입
    @GetMapping("/createUserAPI")
    public String data_createUserApi(){

        return "createUser";
    }
    /**
     *
     * @param email 이메일
     * @param profile_img 이미지
     * @param model 모델
     * @return 모델값
     */

    //로그인 방식에 따라 회원가입 양식을 자동으로 채워주는 API
    @PostMapping(value = "/createUser")
    public String data_to_createUser(@RequestParam (required = false) String email, @RequestParam (required = false) String profile_img, Model model) {

            return "memberjoin.html";
    }


    @ResponseBody
    @PostMapping("/use_api")
    public Boolean use_api(@RequestBody Map<String,String> map, HttpSession session) {
        String email = map.get("email");
        log.info(email);
        if(rep_user_info.existsByEMAIL(email)){
            session.setAttribute("email",email);
            return true;
        } else {
            return false;
        }
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

    @PostMapping("/checkNickname")
    @ResponseBody
    public Boolean checkNickname(@RequestParam String nickname_box){
        return rep_user_info.existsByNICKNAME(nickname_box);
    }

    @PostMapping("/checkEmail")
    @ResponseBody
    public Boolean checkEmail(@RequestParam String email_box){
        return rep_user_info.existsByEMAIL(email_box);
    }

    //아이디 비밀번호 체크 후 있을시 True반환하고 로그인 성공
    @PostMapping(value ="/Dologin")
    @ResponseBody
    public List<USER_INFO> Dologin(@RequestBody Map<String, String> map){
        String id = map.get("id");
        String pw = map.get("password");
        return rep_user_info.findByIDAndPW(id, pw);
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
    public String posting(HttpSession session){
        if(session.getAttribute("email")!=null){
            return "posting";

        } else {
            return "redirect:/login";
        }
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

        String Id = rep_user_info.findByNICKNAME(postData.getPostWriterId()).getID();
        ModelAndView modelAndView = new ModelAndView("postDetail3");
        modelAndView.addObject("post", postData);
        modelAndView.addObject("Id",Id);
        USER_INFO user_info = rep_user_info.findByNICKNAME(postData.getPostWriterId());
        modelAndView.addObject("profileimg", "/profileimg?fileName="+user_info.getPROFILEIMG());

        return modelAndView;
    }


    @GetMapping(value = "/https_healthy_check")
    public String healthy_check(){
        return "main.html";
    }




}
