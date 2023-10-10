package com.example.wearVillage.testArea;

public enum testEnum {
    test1(100), test2(200),test3(300),test4(400);
    private final int value;

    private testEnum(int value){
        this.value = value;
    }

    public int testPrice(int value)
    {return value;}}
