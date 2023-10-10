package com.example.wearVillage.Controller;

import com.example.wearVillage.chat.ChatDTO;
import com.example.wearVillage.chat.ChatService;
import com.example.wearVillage.chat.ChatroomDTO;
import com.example.wearVillage.chat.ChatroomDTO_toString;
import jakarta.servlet.http.HttpSession;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.tomcat.util.http.parser.HttpParser;
import org.springframework.cglib.core.Local;
import org.springframework.stereotype.Controller;
import org.springframework.stereotype.Repository;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

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
        List<ChatDTO> chat = chatSVC.loadingChatHistory(parseInt(postId),(String) session.getAttribute("nickname"),postWriterId);
        log.info(chat.toString());
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
            model.addAttribute("chat_history", chat);
            return "chat.html";
        } else {
            return "redirect:/login";
        }
    }

    @GetMapping("/chatroomlist")
    public String chatroomlist(HttpSession session,Model model){
        if(session.getAttribute("email")!=null){
            model.addAttribute("chatroomList",chatSVC.loadingChatroom((String) session.getAttribute("nickname")));

            List<ChatroomDTO> copyChatroomDTO = chatSVC.loadingChatroom((String) session.getAttribute("nickname")).stream()
                    .map(m-> {
                        ChatroomDTO_toString chatroomDTO = new ChatroomDTO_toString();
                        LocalDateTime liveTime = LocalDateTime.now();
                        LocalDateTime chatroomTime = m.getRECENTLY_TIME().toLocalDateTime();
                            chatroomDTO.setPOST_ID(m.getPOST_ID());
                            chatroomDTO.setMEMBER1(m.getMEMBER1());
                            chatroomDTO.setMEMBER2(m.getMEMBER2());
                            chatroomDTO.setRECENTLY_MSG(m.getRECENTLY_MSG());
                            if(liveTime.getYear()==chatroomTime.getYear()&&liveTime.getMonth()==chatroomTime.getMonth()){
                                if(liveTime.getDayOfYear()-1==chatroomTime.getDayOfYear()){
                                    chatroomDTO.setRECENTLY_TIME("어제");
                                } else if (liveTime.getDayOfYear()==chatroomTime.getDayOfYear()) {
                                    //TODO
                                }
                            }
                            chatroomDTO.setRECENTLY_TIME(m.getRECENTLY_TIME());
                            chatroomDTO.setCHAT_ROOM_ID(m.getCHAT_ROOM_ID());
                    return chatroomDTO;
                    }).toList();
        } else {
            return "redirect:/login";
        }
        return "chatroomlist.html";
    }
}