package com.example.wearVillage.Controller;

import com.example.wearVillage.DTO.USER_INFO_DTO;
import com.example.wearVillage.Entity.USER_INFO;
import com.example.wearVillage.KyhUtilMethod.dateFormater;
import com.example.wearVillage.Repository.Repository_USER_INFO;
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

import java.net.URLDecoder;
import java.nio.charset.StandardCharsets;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

import static java.lang.Integer.parseInt;

@RequiredArgsConstructor
@Controller
@Slf4j
public class ChatRoomController {
    private final ChatService chatSVC;
    private final Repository_USER_INFO repositoryUserInfo;
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
                       @RequestParam(required = false)String postSubtitle,
                       @RequestParam String postWriterId,
                       @RequestParam(required = false) String postPrice,
                       @RequestParam(required = false) String postRentDefaultPrice,
                       @RequestParam(required = false) String postRentDayPrice,
                       @RequestParam(required = false) String postThumbnailUrl,
                       @RequestParam(required = false) String postMapInfo,
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

            List<ChatroomDTO_toString> copyChatroomDTO = chatSVC.loadingChatroom((String) session.getAttribute("nickname")).stream()
                    .map(m-> {
                        String msg = URLDecoder.decode(m.getRECENTLY_MSG(), StandardCharsets.UTF_8);
                        ChatroomDTO_toString chatroomDTO = new ChatroomDTO_toString();
                        LocalDateTime liveTime = LocalDateTime.now();
                        LocalDateTime chatroomTime = m.getRECENTLY_TIME().toLocalDateTime();
                        chatroomDTO.setPOST_ID(m.getPOST_ID());
                        chatroomDTO.setMEMBER1(m.getMEMBER1());
                        chatroomDTO.setMEMBER2(m.getMEMBER2());
                        chatroomDTO.setRECENTLY_MSG(msg);
                        chatroomDTO.setCHAT_ROOM_ID(m.getCHAT_ROOM_ID());
                        // 날짜 정보 가공하는 코드
                        chatroomDTO.setRECENTLY_TIME(new dateFormater(m.getRECENTLY_TIME().toLocalDateTime()).PeriodCalculator());
                        chatroomDTO.setPOST_WRITER_ID(m.getPOST_WRITER_ID());
                        chatroomDTO.setPOST_SUBTITLE(m.getPOST_SUBTITLE());
                        chatroomDTO.setPOST_PRICE(m.getPOST_PRICE());
                        chatroomDTO.setPOST_RENT_DEFAULT_PRICE(m.getPOST_RENT_DEFAULT_PRICE());
                        chatroomDTO.setPOST_RENT_DAY_PRICE(m.getPOST_RENT_DAY_PRICE());
                        chatroomDTO.setPOST_MAP_INFO(m.getPOST_MAP_INFO());
                        chatroomDTO.setPOST_THUMBNAIL_IMG(repositoryUserInfo.findByNICKNAME(m.getPOST_WRITER_ID()).getPROFILEIMG());
                        log.info(m.getPOST_WRITER_ID()+"POST_WRITER_ID");
                        return chatroomDTO;
                    }).toList();
            model.addAttribute("chatroomList",copyChatroomDTO);
            ;
            model.addAttribute("myId",session.getAttribute("nickname"));

        } else {
            return "redirect:/login";
        }
        return "chatroomlist.html";
    }
}