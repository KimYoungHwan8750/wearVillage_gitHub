package com.example.wearVillage.Controller;

import com.example.wearVillage.chat.ChatService;
import com.example.wearVillage.chat.ChatroomDTO;
import jakarta.servlet.http.HttpSession;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.stereotype.Repository;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import static java.lang.Integer.parseInt;

@RequiredArgsConstructor
@Controller
@Slf4j
public class ChatRoomController {
    private final ChatService chatSVC;
    @PostMapping("/createChatroom")
    @ResponseBody
    public boolean createChatroomIfThereIsNo(
            @RequestBody ChatroomDTO chatroomdto){
        log.info("createChatroomIfThereIsNo 메서드 : "+chatroomdto.toString());
        int chatroom = chatroomdto.getPOST_ID();
        String sender= chatroomdto.getMEMBER1();
        String addressee = chatroomdto.getMEMBER2();

        //채팅방이 만들어졌으면 true반환, 아니면 false
        return chatSVC.isThereChatroom(sender,addressee,chatroom);

    }

    @PostMapping(value = "/chat")
    public String chat(Model model,
                       HttpSession session,
                       @RequestParam String postSubtitle,
                       @RequestParam String postWriterId,
                       @RequestParam String postPrice,
                       @RequestParam String postRentDefaultPrice,
                       @RequestParam String postRentDayPrice,
                       @RequestParam String postThumbnailUrl,
                       @RequestParam String postMapInfo,
                       @RequestParam String postId) {
        chatSVC.loadingChatHistory(parseInt(postId),(String) session.getAttribute("nickname"),postWriterId);
        if(session.getAttribute("email")!=null) {
            model.addAttribute("postSubtitle", postSubtitle);
            model.addAttribute("postWriterId", postWriterId);
            model.addAttribute("postPrice", postPrice);
            model.addAttribute("postRentDefaultPrice", postRentDefaultPrice);
            model.addAttribute("postRentDayPrice", postRentDayPrice);
            model.addAttribute("postThumbnailUrl", postThumbnailUrl);
            model.addAttribute("postMapInfo", postMapInfo);
            model.addAttribute("postId", postId);
            model.addAttribute("myId", session.getAttribute("nickname"));
            model.addAttribute("theme", session.getAttribute("theme"));

            return "chat.html";
        } else {
            return "redirect:/login";
        }
    }
}