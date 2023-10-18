package com.example.wearVillage.DAO.myPagePWChangeDAO;

import jakarta.validation.constraints.Pattern;
import lombok.Data;

@Data
public class ChangeUserPWForm {
    private String PW;
    @Pattern(regexp = "^(?=.*[A-Z])(?=.*[a-z])(?=.*\\d)(?=.*[@$!%*?&])[A-Za-z\\d@$!%*?&]{6,20}$", message = "비밀번호는 6~20, 영대소문자1개, 특수문자1개, 숫자1개가 포함되어야합니다.")
    private String newPW;
}
