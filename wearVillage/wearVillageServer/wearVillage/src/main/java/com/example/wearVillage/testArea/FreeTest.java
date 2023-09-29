package com.example.wearVillage.testArea;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Stream;

public class FreeTest {
    public static void main(String[] args) {
//        List<String> test = new ArrayList<>();
//        test.add("data1");
//        test.add("data2");
//        test.add("data3");
//        System.out.println(test.toString());
//        Stream<String> test2 = test.stream();
//        System.out.println(test2.count());
        StringBuffer test = new StringBuffer("test");
        test.replace(0,1,"수정");
        System.out.println(test);
    }
}
