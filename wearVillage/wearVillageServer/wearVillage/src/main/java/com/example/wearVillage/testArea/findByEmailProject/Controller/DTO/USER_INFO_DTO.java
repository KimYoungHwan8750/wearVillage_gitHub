package com.example.wearVillage.testArea.findByEmailProject.Controller.DTO;

import com.example.wearVillage.Entity.USER_INFO;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class USER_INFO_DTO {
    String ID;
    String EMAIL;

    public USER_INFO_DTO(USER_INFO user_info){
        this.ID = user_info.getID();
        this.EMAIL = user_info.getEMAIL();
    }
    public USER_INFO_DTO InsertEntity(USER_INFO user_info){
        this.ID = user_info.getID();
        this.EMAIL = user_info.getEMAIL();
        return this;
    }
}
