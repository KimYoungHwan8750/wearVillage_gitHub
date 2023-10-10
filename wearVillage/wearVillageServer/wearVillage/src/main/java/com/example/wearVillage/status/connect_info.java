package com.example.wearVillage.status;

import static com.example.wearVillage.status.local_or_server.status;

public class connect_info {
    public static String user = status=="local"?"c##wearVillage":"admin";
    public static String pw = status=="local"?"wearVillage":"admin12345";
    public static  String url = status=="local"?"jdbc:oracle:thin:@localhost:1521:xe":"jdbc:oracle:thin:@wearvillage.c38c15agkmuv.ap-northeast-2.rds.amazonaws.com:1521:VILLAGE";

}
