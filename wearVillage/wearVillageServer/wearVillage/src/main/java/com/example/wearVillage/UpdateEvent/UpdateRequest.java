package com.example.wearVillage.UpdateEvent;

import lombok.Data;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class UpdateRequest {
    private String postSubtitle;
    private String postPrice;
    private String postRentDefaultPrice;
    private String postRentDayPrice;
    private String postTagTop;
    private String postTagMiddle;
    private String postMapInfo;
    private String postText;
    private String postModifyDate;
}
