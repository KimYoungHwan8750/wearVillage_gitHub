package com.example.wearVillage.testArea;

import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import jdk.jfr.Enabled;
import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
@Entity
public class KYH {
    String name;
    String age;
}
