package com.example.wearVillage.DTO;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Setter
@Getter
@ToString
public class CreateUserDTO {
  String email;
  String profile_image;
  String nickname;
  String birth;
  String gender;

}
