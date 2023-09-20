package com.example.wearVillage.Entity;


import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import lombok.AllArgsConstructor;
import lombok.Builder;
// import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Entity
@Setter
@Builder
@NoArgsConstructor  // No-args constructor is still required by JPA.
@AllArgsConstructor

public class USER_INFO {
    @Id
    private String ID;
    private String PW;
    private String NICKNAME;
    private String EMAIL;
    private String PROFILEIMG;
    private String THEME;  
    private String GENDER; 
    private String BIRTH;  

}