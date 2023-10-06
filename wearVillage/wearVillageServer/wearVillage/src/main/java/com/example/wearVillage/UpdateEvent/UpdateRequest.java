package com.example.wearVillage.UpdateEvent;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class UpdateRequest {
    @NotBlank(message = "공백은 불가능합니다")
    @Size(min=1, max=20,message = "한글자 이상, 20글자 미만으로 입력해주세요")
    private String postSubtitle;
    @NotBlank(message = "공백은 불가능합니다")
    private String postPrice;
    private String postRentDefaultPrice;
    private String postRentDayPrice;
    @NotBlank(message = "공백은 불가능합니다")
    private String postTagTop;
    @NotBlank(message = "공백은 불가능합니다")
    private String postTagMiddle;
    @NotBlank(message = "공백은 불가능합니다")
    private String postMapInfo;
    @NotBlank(message = "공백은 불가능합니다")
    private String postText;
    private String postModifyDate;
}