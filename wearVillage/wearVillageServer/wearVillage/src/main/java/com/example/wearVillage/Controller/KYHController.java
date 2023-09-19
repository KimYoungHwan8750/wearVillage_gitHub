package com.example.wearVillage.Controller;

import com.example.wearVillage.DAO.userChatDAO;
import com.example.wearVillage.Entity.USER_INFO;
import com.example.wearVillage.Repository.Repository;
import jakarta.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@Controller
public class KYHController {
    @Autowired
    private final Repository repository;

//    @Autowired
    private final userChatDAO userDAO;
    KYHController(userChatDAO userDAO, Repository repository){
        this.userDAO = userDAO;
        this.repository = repository;
    }
    @PostMapping(value = "/chat")
    public String chat(Model model, @RequestParam String id, @RequestParam String target_id, @RequestParam String post_id, @RequestParam(required = false) String chat_thema) {
        model.addAttribute("id",id);
        model.addAttribute("target_id",target_id);
        model.addAttribute("post_id",post_id);
        model.addAttribute("chat_thema",chat_thema);
        List<Map<String, Object>> chatHistory = userDAO.oracle_to_userChat(id,post_id);
        model.addAttribute("chat_history",chatHistory);
        return "chat.html";
    }

    @ResponseBody
    @GetMapping(value ="/userId")
    public List<USER_INFO> userId(HttpSession session){
        String id = (String) session.getAttribute("login_check");
        System.out.println(repository.findByID(id));
        return repository.findByID(id);
    }
    @PostMapping(value = "/finished_signUp")
    public USER_INFO finished_create_user(@RequestBody USER_INFO user_info) {
        System.out.println(user_info);
        return repository.save(user_info);
    }
}
