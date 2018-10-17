package com.example.bean;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Locale;
import java.util.Date;
/**
 * Created by ${宁.金珂} on 2018/7/10.
 */

public class TimeTo {
    public static Date date;
    public static String openTime(String string){
        SimpleDateFormat sf1;
        sf1 = new SimpleDateFormat("EEE MMM dd hh:mm:ss z yyyy", Locale.ENGLISH);

        try {
                date = sf1.parse(string);
            } catch (ParseException e) {
                e.printStackTrace();
            }

        SimpleDateFormat sf2 = new SimpleDateFormat("yyyy-MM-dd");
        String format_data = sf2.format(date);
        return format_data;

    }
    //构造方法私有化，这样外界就不能访问了
//    private TimeTo(){
//    };
//    private volatile static TimeTo mSingleMode;
//    public static TimeTo getInstance(){
//        if (mSingleMode == null){
//            synchronized (TimeTo.class){
//                if (mSingleMode == null){  //二次检测
//                    mSingleMode = new TimeTo();
//                }
//            }
//        }
//        return  mSingleMode;
//    }

}
