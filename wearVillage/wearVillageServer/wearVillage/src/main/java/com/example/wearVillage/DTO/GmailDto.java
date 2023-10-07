package com.example.wearVillage.DTO;

import  lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class GmailDto{
    private String address;
    private String title;
    private String content;
}