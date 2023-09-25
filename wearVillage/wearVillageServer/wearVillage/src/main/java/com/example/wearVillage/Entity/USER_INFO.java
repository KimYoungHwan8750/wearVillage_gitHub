package com.example.wearVillage.Entity;


import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import lombok.*;

@Getter
@Entity
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Setter
public class USER_INFO {
    @JsonProperty("ID")
    @Id
    private String ID;
    @JsonProperty("PW")
    private String PW;
    @JsonProperty("NICKNAME")
    private String NICKNAME;
    @JsonProperty("EMAIL")
    private String EMAIL;
    @JsonProperty("PROFILEIMG")
    private String PROFILEIMG;
    @JsonProperty("THEME")
    private String THEME;
    @JsonProperty("GENDER")
    private String GENDER;
    @JsonProperty("BIRTH")
    private String BIRTH;
    @Override
    public String toString(){
        return
                ID + "구분" +
                        PW + "구분" +
                        EMAIL + "구분" +
                        NICKNAME + "구분" +
                        PROFILEIMG + "구분" +
                        GENDER + "구분";
    }


}
