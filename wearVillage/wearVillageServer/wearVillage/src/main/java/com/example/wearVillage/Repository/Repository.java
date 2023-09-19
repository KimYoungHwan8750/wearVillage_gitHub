package com.example.wearVillage.Repository;


import com.example.wearVillage.Entity.USER_INFO;
import org.springframework.data.jpa.repository.JpaRepository;


import java.util.List;

@org.springframework.stereotype.Repository
public interface Repository extends JpaRepository <USER_INFO, String> {

    List<USER_INFO> findByID(String ID);

}
