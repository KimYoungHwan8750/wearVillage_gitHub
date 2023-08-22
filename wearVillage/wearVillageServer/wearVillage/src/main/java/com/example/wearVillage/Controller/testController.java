package com.example.wearVillage.Controller;



import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;



import static com.example.wearVillage.dataContoller.LoginCheck.login_check;
import static com.example.wearVillage.dataContoller.check_email.check_email;
import static com.example.wearVillage.dataContoller.check_id.check_id;
import static com.example.wearVillage.dataContoller.createUserToOracle.dataToOracle;



@Controller
public class testController {
    // 메인화면으로 이동
    @RequestMapping(value = "/")
    public String home(){
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
        model.addAttribute("profile_img", "img/index/기본프사.jpg");
        return "createUser.html";
    }

    //회원가입시 메인화면으로 이동하기 위한 코드 (아직 구현 중)
    @PostMapping(value = "/")
    public String finished_create_user(@RequestParam String userId,@RequestParam String userPassword, @RequestParam String email) {
            dataToOracle(email, userId, userPassword);
            return "main.html";
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
        return "logins";
    }


}
