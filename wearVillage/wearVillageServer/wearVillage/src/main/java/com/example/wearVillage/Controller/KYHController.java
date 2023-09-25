package com.example.wearVillage.Controller;

import com.example.wearVillage.DAO.userChatDAO;
import com.example.wearVillage.DTO.USER_INFO_DTO;
import com.example.wearVillage.Entity.USER_INFO;

import com.example.wearVillage.Repository.Repository_USER_INFO;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;
import org.apache.catalina.User;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@Controller
public class KYHController {
    private final Repository_USER_INFO rep_user_info;

    private final userChatDAO userDAO;

    KYHController(userChatDAO userDAO, Repository_USER_INFO rep_user_info) {
        this.userDAO = userDAO;
        this.rep_user_info = rep_user_info;
    }


    @GetMapping(value ="/logout")
    public String logout(HttpSession session){
        session.removeAttribute("email");
        return "redirect:/";
    }

    @PostMapping(value = "/chat")
    public String chat(Model model, @RequestParam String id, @RequestParam String target_id, @RequestParam String post_id) {
        model.addAttribute("id", id);
        model.addAttribute("target_id", target_id);
        model.addAttribute("post_id", post_id);
        List<Map<String, Object>> chatHistory = userDAO.oracle_to_userChat(id, post_id);
        model.addAttribute("chat_history", chatHistory);
        return "chat.html";
    }

    @PostMapping(value = "/chatroom")
    public String chatroom(HttpSession session){
        session.getAttribute("id");

        return "chatroom.html";
    }
    @GetMapping(value = "/chatroom")
    public String getchatroom(){

        return "chatroom.html";
    }

    @ResponseBody
    @GetMapping(value = "/userInfo")
    public List<USER_INFO> userId(HttpSession session) throws IndexOutOfBoundsException{
        try {
            String email = (String) session.getAttribute("email");
            return rep_user_info.findByEMAIL(email);

        } catch (Exception e) {
            return null;
        }
    }

    @PostMapping(value = "/finished_signUp")
    public String signup(@RequestBody USER_INFO user_info) {
        rep_user_info.save(user_info);
        return "redirect:/";
    }

    @GetMapping(value ="/testPlace")
    public String testPlace(){
        return "datatest.html";
    }


    @GetMapping(value="/kyhTest")
    public String kyhTest(){
        return "";
    }

}

