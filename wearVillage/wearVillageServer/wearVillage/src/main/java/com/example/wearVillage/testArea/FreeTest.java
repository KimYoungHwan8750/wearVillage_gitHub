package com.example.wearVillage.testArea;


import com.example.wearVillage.KyhUtilMethod.dateFormater;
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
        dateFormater formating = new dateFormater(LocalDateTime.now().minusDays(2));
        System.out.println(formating.PeriodCalculator());

    }
}
