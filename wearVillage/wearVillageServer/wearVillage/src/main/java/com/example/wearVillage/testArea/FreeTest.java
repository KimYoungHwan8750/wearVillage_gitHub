package com.example.wearVillage.testArea;


import lombok.*;
import oracle.sql.TIMESTAMP;
import org.checkerframework.checker.units.qual.C;

import java.sql.Time;
import java.sql.Timestamp;
import java.time.DateTimeException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.function.IntFunction;
import java.util.stream.Stream;

import static java.util.stream.Stream.builder;


public class FreeTest {
    public static void main(String[] args) {
        Stream<String> stream = Stream.<String>builder().add("banana").build();
        Timestamp time = Timestamp.valueOf(LocalDateTime.now());
        LocalDateTime test = time.toLocalDateTime();
        time = Timestamp.valueOf(test);
        System.out.println(time);

        System.out.println(test);
        DateTimeFormatter form = DateTimeFormatter.ofPattern("yyyy@MM@dd");
        System.out.println(test.format(form));

    }
}
@Getter
@Setter
//@RequiredArgsConstructor
@NoArgsConstructor
@AllArgsConstructor
class Class1{
    LocalDateTime date = LocalDateTime.now();
    String data2 = "data22";
    @Override
    public String toString(){
        return date+","+data2;
    }
}
@Getter
@Setter
class Class2{
    String data2 = "data2";
}
