//package com.example.wearVillage.DAO.ProductBuyDAO;
//
//import lombok.RequiredArgsConstructor;
//import lombok.extern.slf4j.Slf4j;
//import org.springframework.scheduling.annotation.Scheduled;
//import org.springframework.stereotype.Service;
//
//@Slf4j
//@Service
//@RequiredArgsConstructor
//public class ScheduleSVC {

//    private final ProductBuyDAO productBuyDAO;
//    private final ProductFinalForm productFinalForm;

//    @Scheduled(fixedDelay = 5000)
//    public void testCheck(){
//        log.info("스케쥴체크");
//    }
//    @Scheduled(fixedDelay = 86400)
//    public void checkPerDay(){
        //query로 모든 DB를 긁어와서 변수에 저장한다.
        // 받은 변수 List를 DAO에 넣는다.
        // List를 이용해서 rent_check가 0인것과 1인것을 구별하고 거래 일자를 구별해서 돈을 강제 반환시킨다.

        // 반환 버튼을 하나 만들어서 rent_check를 1로 만들수있게한다.

        //체크할시 rent_check 값이 0일때
        //반환이 되어있지 않음

        //체크할시 rent_check 값이 1일때
        //반환이 되어있는 상황
//        productBuyDAO.checkPerDay(productFinalForm);
//    }
//}
