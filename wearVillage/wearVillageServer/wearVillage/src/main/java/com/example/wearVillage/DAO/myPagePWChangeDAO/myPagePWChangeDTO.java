package com.example.wearVillage.DAO.myPagePWChangeDAO;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class myPagePWChangeDTO {
    private String userId;
    private String PW;
    private String newPW;
    private String newPWConfirm;

    public myPagePWChangeDTO(String userId, ChangeUserPWForm form){
        this.userId = userId;
        this.PW = form.getPW();
        this.newPW = form.getNewPW();
        this.newPWConfirm = form.getNewPWConfirm();
    }
}
