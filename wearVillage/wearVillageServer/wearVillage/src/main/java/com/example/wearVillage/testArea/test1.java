package com.example.wearVillage.testArea;

import java.util.HashSet;
import java.util.Set;
import java.util.regex.Pattern;

public class test1 {
    public static final String test = "^[\\d]*$";
    public static void main(String[] args) {
        Set<String> testSet = new HashSet<>();
        testSet.add("321");
        testSet.add("369+");
        System.out.println(testSet.size());

    }
}
