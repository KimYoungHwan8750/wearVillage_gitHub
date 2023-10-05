package com.example.wearVillage.Controller;

import com.example.wearVillage.chat.ChatService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.stereotype.Repository;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@RequiredArgsConstructor
@Controller
public class ChatRoomController {
    private final ChatService chatSVC;
    @PostMapping("/createChatroom")
    @ResponseBody
    public void testMethod(
            @RequestParam String sender,
            @RequestParam String addressee,
            @RequestParam int chatroom){
        System.out.println(sender);
        System.out.println(addressee);
        System.out.println(chatroom);
    chatSVC.isThereChatroom(sender,addressee,chatroom);
    }
}