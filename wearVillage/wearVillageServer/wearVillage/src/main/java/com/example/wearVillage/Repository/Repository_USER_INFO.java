package com.example.wearVillage.Repository;


import com.example.wearVillage.Entity.USER_INFO;
import org.springframework.data.jpa.repository.JpaRepository;


import java.util.List;
import java.util.Optional;

@org.springframework.stereotype.Repository
public interface Repository_USER_INFO extends JpaRepository <USER_INFO, String> {

    List<USER_INFO> findByID(String ID);
    Optional<USER_INFO> findAllByID(String ID);
    List<USER_INFO> findByEMAIL(String EMAIL);
    USER_INFO findByNICKNAME(String nickname);

    Boolean existsByEMAIL(String EMAIL);
    List<USER_INFO> findByIDAndPW(String ID, String PW);
    Boolean existsByID(String ID);
    Boolean existsByNICKNAME(String Nickname);


}





