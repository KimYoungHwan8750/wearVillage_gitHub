package com.example.wearVillage.KyhUtilMethod;

import java.time.LocalDateTime;

/*

 메서드 정리:
 포매팅을 원하는 LocalDateTime 타입의 객체를 주입한다.

    AmOrPm : 오전, 오후 값만 반환

    Hours : 24시간을 12시간으로 표현. AmOrPm메서드와 조합해서 사용해야한다.

    ConcatZeroToMinute : 10분 미만일 경우 0을 추가해 09분과 같은 형태로 표시한다.

    PeriodCalculator : AmOrPm과 Horus, ConcatZeroToMinute를 조합한 형태이다.
    오후 12:03와 같이 최종형으로 표시되며, 주입받은 LocalDateTime객체가 실시간과 하루 차이가 나면 어제라고 표시되고
    이틀 이상 차이가 나면 3월 17일과 같은 형태로 표시된다.

*/

public class dateFormater{
    private LocalDateTime format;
    public dateFormater(LocalDateTime localDateTime){
        this.format = localDateTime;
    }

    // LocalDateTime 타입을 넣으면 시간을 계산해서 오전, 오후를 반환해줍니다.
    public String AmOrPm(){
        String str = null;
        if(format.getHour()>=12){
            str = "오후";
        } else {
            str = "오전";
        }
        return str;
    }

    // 13시부터 시간에 12를 빼서 오후 1시, 오후 2시와 같은 형태로 환산합니다.
    public String Hours(){
        if(format.getHour()>=13){
            return String.valueOf(format.getHour()-12);
        } else {
            return String.valueOf(format.getHour());
        }
    }

    // 10분 미만일 경우 0을 붙여서 05분과 같이 표기해줍니다.
    public String ConcatZeroToMinute(){
        if(format.getMinute()<10){
            return "0" + String.valueOf(format.getMinute());
        } else {
            return String.valueOf(format.getMinute());
        }
    }

    //실시간과 사용자가 입력한 시간을 대조합니다.
    //같은 날 : 사용자가 입력한 시간을 오전 4:02 와 같은 형태로 표현해줍니다.
    //어제 : "어제"로 표현합니다.
    //2일 이상 경과 : 4월 30일과 같은 형태로 표현합니다.
    public String PeriodCalculator(){
        LocalDateTime nowTime = LocalDateTime.now();
        LocalDateTime yesterDay = LocalDateTime.of(nowTime.getYear(),nowTime.getMonthValue(), nowTime.getDayOfMonth(),nowTime.getHour(),nowTime.getMinute(),nowTime.getSecond()).minusDays(1);
        // 같은 날
            if(nowTime.getYear()==format.getYear() &&
            nowTime.getMonthValue()==format.getMonthValue() &&
            nowTime.getDayOfYear()==format.getDayOfYear()){

            return AmOrPm() + " " + Hours() + ":" + ConcatZeroToMinute();

        } else if (yesterDay.getYear()==format.getYear() &&
            yesterDay.getMonthValue()==format.getMonthValue() &&
            yesterDay.getDayOfYear()==format.getDayOfYear()){

            return "어제";

        } else {

            return format.getMonthValue()+"월 "+format.getDayOfMonth()+"일";

        }
    }

}
