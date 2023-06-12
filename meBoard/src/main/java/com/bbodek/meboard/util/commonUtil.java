package com.bbodek.meboard.util;

import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.util.*;

public class commonUtil {
    public static List<?> convertObjectToList(Object obj) {
        List<?> list = new ArrayList<>();
        if (obj.getClass().isArray()) {
            list = Arrays.asList((Object[]) obj);
        } else if (obj instanceof Collection) {
            list = new ArrayList<>((Collection<?>) obj);
        }
        return list;
    }

    public static String convertDataKeyString(String Type) {
        String keyString = null;
        SimpleDateFormat transFormat = new SimpleDateFormat("yyyyMMddHHmmss");
        Calendar calendar = Calendar.getInstance();
        Date date = calendar.getTime();
        transFormat.setTimeZone(TimeZone.getTimeZone("Asia/Seoul"));;
        String dateString = transFormat.format(date);
        keyString = Type + "_" + dateString;
        return keyString;
    }


    public static String convertDataKeyKIDSString(String Type) {
        String keyString = null;
        SimpleDateFormat transFormat = new SimpleDateFormat("MMddHH");
        Calendar calendar = Calendar.getInstance();
        Date date = calendar.getTime();
        transFormat.setTimeZone(TimeZone.getTimeZone("Asia/Seoul"));;
        String dateString = transFormat.format(date);
        keyString = Type + "_" + dateString;
        return keyString;
    }

    public static int dateTimeKey() {
        ZonedDateTime nowSeoul = ZonedDateTime.now(ZoneId.of("Asia/Seoul"));
        LocalDateTime dateTime = LocalDateTime.now(ZoneId.from(nowSeoul));
        return dateTime.getHour();
    }


    public static LocalDate convertDateFabSetting() {
        SimpleDateFormat transFormat = new SimpleDateFormat("yyyy-MM-dd");
        Calendar calendar = Calendar.getInstance();
        Date date = calendar.getTime();
        String dateString = transFormat.format(date);
        return LocalDate.parse(dateString);
    }


}
