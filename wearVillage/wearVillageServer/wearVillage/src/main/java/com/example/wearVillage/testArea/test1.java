package com.example.wearVillage.testArea;

import java.util.regex.Pattern;

public class test1 {
    public static final String test = "^[\\d]*$";
    public static void main(String[] args) {
        boolean testString = Pattern.matches(test,"123");
        System.out.println(testString);

    }
}
