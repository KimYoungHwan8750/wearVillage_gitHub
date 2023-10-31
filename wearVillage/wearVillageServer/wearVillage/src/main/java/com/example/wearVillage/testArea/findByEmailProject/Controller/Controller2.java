package com.example.wearVillage.testArea.findByEmailProject.Controller;

import jakarta.servlet.http.HttpSession;
import org.springframework.web.bind.annotation.GetMapping;

@org.springframework.stereotype.Controller

public class Controller2 {
    @GetMapping("/yhsm")
    public String Yhsm()
    {
        return "yhsm.html";
    }


}
