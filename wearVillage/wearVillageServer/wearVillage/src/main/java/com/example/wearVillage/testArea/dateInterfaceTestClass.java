package com.example.wearVillage.testArea;

import java.sql.Timestamp;
import java.time.LocalDateTime;

public class dateInterfaceTestClass {
    public static void main(String[] args) {
        dateInterface dt = ()->{
            LocalDateTime test1 = LocalDateTime.now();
            return test1;
        };
        System.out.println(dt.toString());

    }
}
