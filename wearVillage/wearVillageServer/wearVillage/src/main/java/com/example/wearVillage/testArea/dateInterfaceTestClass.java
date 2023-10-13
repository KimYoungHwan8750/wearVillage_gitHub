package com.example.wearVillage.testArea;

import com.example.wearVillage.KyhUtilMethod.dateFormater;

import java.time.LocalDateTime;

public class dateInterfaceTestClass {


    public static void main(String[] args) {

        dateInterface<String,Integer> myInterFace = text->{
            char[] str = text.replaceAll("[^0-9]","").toCharArray();
            int sum = 0;
            for (int i = 0; i < str.length; i++) {
                sum += Integer.parseInt(String.valueOf(str[i]));
            }
            return sum;
        };
        System.out.println(myInterFace.testMethod("sare3a11es1r"));


    }
}

