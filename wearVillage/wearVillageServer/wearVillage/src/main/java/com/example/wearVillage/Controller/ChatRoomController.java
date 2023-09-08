//package com.example.wearVillage.Controller;
//
//import lombok.RequiredArgsConstructor;
//import org.springframework.ui.Model;
//import org.springframework.web.bind.annotation.GetMapping;
//import org.springframework.web.bind.annotation.PathVariable;
//import org.springframework.web.bind.annotation.RequestMapping;
//
//@RequiredArgsConstructor
//@RequestMapping("/chat")
//@org.springframework.stereotype.Controller
//public class ChatRoomController {
//    @GetMapping("/test")
//    public String chat(@PathVariable String username, Model model){
//        System.out.println("확인1");
//        model.addAttribute("username", username);
//        return "chat.html";
//    }
//}