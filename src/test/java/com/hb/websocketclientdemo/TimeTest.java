package com.hb.websocketclientdemo;

import org.joda.time.DateTime;
import org.joda.time.Duration;
import org.joda.time.LocalTime;
import org.joda.time.format.DateTimeFormat;
import org.joda.time.format.DateTimeFormatter;

import java.util.Date;
import java.util.Locale;

public class TimeTest {

    public static void main(String[] args) {

        String time1 = "16:53:28";
        LocalTime lt = new LocalTime(time1);
        System.out.println(lt);
        LocalTime now = new LocalTime();
        DateTime dt12 = lt.toDateTimeToday();
        DateTime now1 = new DateTime();
        int secdelta = (int) new Duration(dt12, now1).getStandardSeconds();
        System.out.println(now);
        int x = now.getSecondOfMinute()-lt.getSecondOfMinute();


        DateTimeFormatter format = DateTimeFormat.forPattern("HH:mm:ss");

        DateTime strDt = DateTime.parse(time1, format);
        System.out.println(strDt);
        DateTime dt = new DateTime();

        DateTime dt1 = new DateTime(new Date());

// 创建指定日期时间如：2017-11-27 14：30：50：500
        DateTime dt2 = new DateTime(2017, 11, 27, 14, 30, 50, 500);

        System.out.println(dt.toString("yyyy-MM-dd HH:mm:ss"));

        dt = new DateTime(2000, 11, 27, 0, 0, 0);
        System.out.println(dt);
        dt = dt.withYear(2017);// 设置年份为2017
        System.out.println(dt);


        int year = dt.getYear();// 年
        int month = dt.getMonthOfYear();// 月
        int day = dt.getDayOfMonth();// 日
        int hour = dt.getHourOfDay();// 小时
        int minute = dt.getMinuteOfHour();// 分钟
        int second = dt.getSecondOfMinute();// 秒
        int millis = dt.getMillisOfSecond();// 毫秒

        System.out.println(year + "-" + month + "-" + day + " " + hour + ":" + minute + ":" + second + ":" + millis);

        String month2 = dt.monthOfYear().getAsText();
        String day2 = dt.dayOfWeek().getAsShortText();
        String day3 = dt.dayOfWeek().getAsShortText(Locale.CHINESE); // 以指定格式获取
        System.out.println(month2);
        System.out.println(day2);
        System.out.println(day3);

        dt = dt.plusDays(1);// 加一天
        dt = dt.plusHours(1);// 加一小时
        dt = dt.plusYears(-1);// 减一年
        System.out.println(dt.toString("yyyy-MM-dd HH:mm:ss"));

        dt = dt.minusYears(1);// 减一年
        dt = dt.minusMinutes(-30);// 加半个小时
        System.out.println(dt.toString("yyyy-MM-dd HH:mm:ss"));
    }


}
