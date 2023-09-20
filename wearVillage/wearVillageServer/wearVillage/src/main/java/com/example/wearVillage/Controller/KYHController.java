package com.example.wearVillage.Controller;

import com.example.wearVillage.DAO.userChatDAO;
import com.example.wearVillage.Entity.USER_INFO;

import com.example.wearVillage.Repository.Repository_USER_INFO;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;
import netscape.javascript.JSObject;
import org.apache.catalina.User;
import org.hibernate.type.descriptor.java.ObjectJavaType;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.parameters.P;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@Controller
public class KYHController {
    @Autowired
    private final Repository_USER_INFO rep_user_info;

    //    @Autowired
    private final userChatDAO userDAO;

    KYHController(userChatDAO userDAO, Repository_USER_INFO rep_user_info) {
        this.userDAO = userDAO;
        this.rep_user_info = rep_user_info;
    }

    @PostMapping(value = "/chat")
    public String chat(Model model, @RequestParam String id, @RequestParam String target_id, @RequestParam String post_id, @RequestParam(required = false) String chat_theme) {
        model.addAttribute("id", id);
        model.addAttribute("target_id", target_id);
        model.addAttribute("post_id", post_id);
        model.addAttribute("chat_theme", chat_theme);
        List<Map<String, Object>> chatHistory = userDAO.oracle_to_userChat(id, post_id);
        model.addAttribute("chat_history", chatHistory);
        return "chat.html";
    }
    @PostMapping(value = "/chatroom")
    public String chatroom(HttpSession session){
        session.getAttribute("id");

        return "chatroom.html";
    }

    @ResponseBody
    @GetMapping(value = "/userInfo")
    public List<USER_INFO> userId(HttpSession session) {
        String id = (String) session.getAttribute("id");
        System.out.println(rep_user_info.findByID(id));
        return rep_user_info.findByID(id);
    }

    @PostMapping(value = "/finished_signUp")
    public String signup(@RequestBody String data) {
        JSONObject user = new JSONObject(data);
        String ID=user.getString("ID");
        String PW=user.getString("PW");
        String NICKNAME=user.getString("NICKNAME");
        String EMAIL=user.getString("EMAIL");
        String THEME=user.getString("THEME");
        String PROFILEIMG=user.getString("PROFILEIMG");
        String GENDER=user.getString("GENDER");
        String BIRTH=user.getString("BIRTH");
        rep_user_info.save(new USER_INFO(ID,PW,NICKNAME,EMAIL,PROFILEIMG,THEME,GENDER,BIRTH));
        return "main.html";
    }

}

