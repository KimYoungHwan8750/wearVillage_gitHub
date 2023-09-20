package com.example.wearVillage.Entity;


import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Entity
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class USER_INFO {
    @Id

    private String ID;
    private String PW;
    private String NICKNAME;
    private String EMAIL;
    private String PROFILEIMG;
    private String THEMA;
    private String GENDER;
    private String BIRTH;
}
