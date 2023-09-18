package com.example.wearVillage.Controller;

import com.example.wearVillage.DAO.userChatDAO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import java.util.List;
import java.util.Map;

@Controller
public class KYHController {
//    @Autowired
    private final userChatDAO userDAO;
    KYHController(userChatDAO userDAO){
        this.userDAO = userDAO;
    }
    @PostMapping(value = "/chat")
    public String chat(Model model, @RequestParam String id, @RequestParam String target_id, @RequestParam String post_id, @RequestParam(required = false) String chat_thema) {
        model.addAttribute("id",id);
        model.addAttribute("target_id",target_id);
        model.addAttribute("post_id",post_id);
        model.addAttribute("chat_thema",chat_thema);
        List<Map<String, Object>> chatHistory = userDAO.oracle_to_userChat(id,target_id,post_id);
        model.addAttribute("chat_history",chatHistory);
        return "chat.html";
    }
}
