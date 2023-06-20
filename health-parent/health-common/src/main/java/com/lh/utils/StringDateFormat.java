package com.lh.utils;

public class StringDateFormat {

    public static String format(String date){
        int i = date.lastIndexOf("-");
        String year = date.substring(0, i);
        String month = date.substring(i+1, date.length());
        if(month.substring(0,1).equals("0")){
            return date;
        }
        if(Integer.valueOf(month)>=11){
            return year+"-"+month;
        }else {
            return year+"-0"+month;
        }
    }

//    public static void main(String[] args) {
//        format("2023-06");
//    }
}
