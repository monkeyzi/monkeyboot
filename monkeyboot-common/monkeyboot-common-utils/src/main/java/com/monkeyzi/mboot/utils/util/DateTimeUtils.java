package com.monkeyzi.mboot.utils.util;

import lombok.experimental.UtilityClass;
import lombok.extern.slf4j.Slf4j;

import java.time.*;
import java.time.format.DateTimeFormatter;
import java.time.temporal.ChronoUnit;
import java.util.Date;

@Slf4j
@UtilityClass
public class DateTimeUtils {
    /**
     * yyyy-MM-dd
      */
     private static final String DATE_FORMAT_PATTERN_LINE="yyyy-MM-dd";
    /**
     * yyyyMMdd
     */
     private static final String DATE_FORMAT_PATTERN_NO="yyyyMMdd";
     /**
     * yyyy-MM-dd HH:mm:ss
     */
     private static final String DATE_TIME_FORMAT_PATTERN_LINE="yyyy-MM-dd HH:mm:ss";
    /**
     * HH:mm:ss
     */
    private static final String TIME_FORMAT_PATTERN_LINE="HH:mm:ss";

    /**
     * java8 获取今天的日期
      * @return LocalDate
     */
     public LocalDate  nowDate(){
          LocalDate now=LocalDate.now();
          return now;
     }
     /**
     * java8 获取今天的日期
     * @return String  返回格式 yyyyMMdd  20190814
     */
     public String  nowDateString(){
         DateTimeFormatter formatter=DateTimeFormatter.ofPattern(DATE_FORMAT_PATTERN_NO);
         return nowDate().format(formatter);
     }
    /**
     * java8 获取今天的时期
     * @param format 输出的格式-自定义
     * @return  String类型
     */
     public String   nowDateString(String format){
         DateTimeFormatter formatter=DateTimeFormatter.ofPattern(format);
         return nowDate().format(formatter);
     }

    /**
     * java8获取当前的日期时间
     * @return LocalDateTime ---------2019-08-14T10:14:23
     */
     public LocalDateTime nowDateTime(){
         LocalDateTime dateTime=LocalDateTime.now();
         return dateTime;
     }

    /**
     * java8获取当前的日期时间
     * @return String  yyyy-MM-dd HH:mm:ss ----------2019-08-14 10:14:23
     */
     public String  nowDateTimeString(){
         DateTimeFormatter formatter=DateTimeFormatter.ofPattern(DATE_TIME_FORMAT_PATTERN_LINE);
         return nowDateTime().format(formatter);
     }
     /**
     * java8获取当前的日期时间
     * @return String  用户自定义
     */
    public String  nowDateTimeString(String format){
        DateTimeFormatter formatter=DateTimeFormatter.ofPattern(format);
        return nowDateTime().format(formatter);
    }

    /**
     * java8获取当前的时间
     * @return LocalTime --------10:19:38.815
     */
    public LocalTime nowTime(){
        LocalTime nowTime=LocalTime.now();
        return nowTime;
    }

    /**
     * java8获取当前时间
     * @return String HH:mm:ss --------10:24:39
     */
    public String nowTimeString(){
        DateTimeFormatter formatter=DateTimeFormatter.ofPattern(TIME_FORMAT_PATTERN_LINE);
        return nowTime().format(formatter);
    }
    /**
     * java8获取当前时间
     * @return String 自定义格式
     */
    public String nowTimeString(String format){
        DateTimeFormatter formatter=DateTimeFormatter.ofPattern(format);
        return nowTime().format(formatter);
    }

    /**
     * 获取当前日期的年份
     * @return
     */
    public int getNowYear(){
        LocalDate now=LocalDate.now();
        return now.getYear();
    }
    /**
     * 获取当前日期的月份
     * @return
     */
    public int getNowMonth(){
        LocalDate now=LocalDate.now();
        return now.getMonthValue();
    }
    /**
     * 获取当前日期的天
     * @return
     */
    public int getNowDay(){
        LocalDate now=LocalDate.now();
        return now.getDayOfMonth();
    }

    /**
     * 格式化指定的日期时间
     * @param localDateTime 需要格式化的时间
     * @param format 格式化 格式
     * @return
     */
    public String  formatDateTime(LocalDateTime localDateTime,String format){
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern(format);
        return localDateTime.format(formatter);
    }

    /**
     * 将long类型的timeStamp转为LocalDateTime
     * @param timestamp
     * @return LocalDateTime
     */
    public LocalDateTime  timestampToLocalDateTime(long timestamp){
        Instant instant = Instant.ofEpochMilli(timestamp);
        ZoneId zone = ZoneId.systemDefault();
        return LocalDateTime.ofInstant(instant, zone);
    }

    /**
     * 将LocalDateTime转为long类型的timeStamp
     * @param localDateTime
     * @return long
     */
    public long  localDateTimeToTimestamp(LocalDateTime localDateTime){
        ZoneId zone = ZoneId.systemDefault();
        Instant instant = localDateTime.atZone(zone).toInstant();
        return instant.toEpochMilli();
    }

    /**
     * 将LocalDateTime to Date
     * @param localDateTime
     * @return Date
     */
    public Date localDateTimeToDate(LocalDateTime localDateTime){
        ZoneId zone = ZoneId.systemDefault();
        Instant instant = localDateTime.atZone(zone).toInstant();
        return Date.from(instant);
    }

    /**
     * 将时间字符串转为自定义格式的LocalDateTime
     * @param dateTime 字符串时间
     * @param format  格式
     * @return
     */
    public LocalDateTime parseStringToDateTime(String dateTime,String format){
        DateTimeFormatter df = DateTimeFormatter.ofPattern(format);
        return LocalDateTime.parse(dateTime, df);
    }

    /**
     * 将Date转为LocalDateTime
     * @param date
     * @return
     */
    public LocalDateTime dateToLocalDateTime(Date date){
        Instant instant = date.toInstant();
        ZoneId zone = ZoneId.systemDefault();
        return LocalDateTime.ofInstant(instant, zone);
    }

    /**
     * 将 Date 转为 LocalTime
     * @param date
     * @return
     */
    public LocalTime dateToLocalTime(Date date){
        Instant instant = date.toInstant();
        ZoneId zone = ZoneId.systemDefault();
        LocalDateTime localDateTime = LocalDateTime.ofInstant(instant, zone);
        return localDateTime.toLocalTime();
    }

    /**
     * LocalDate 转date
     * @param localDate
     * @return
     */
    public Date localDateToDate(LocalDate localDate) {
        ZoneId zone = ZoneId.systemDefault();
        Instant instant = localDate.atStartOfDay().atZone(zone).toInstant();
        return Date.from(instant);
    }

    /**
     * 给当前时间增加小时
     * @param hours
     * @return
     */
    public LocalTime plusHours(int hours){
        LocalTime localTime=LocalTime.now();
        return localTime.plusHours(hours);
    }

    /**
     * 当前时间增加n周
     * @param weeks
     * @return
     */
    public LocalDate plusWeeks(int weeks){
        LocalDate now=LocalDate.now();
        return now.plus(weeks,ChronoUnit.WEEKS);
    }

      public static void main(String[] args) {

          System.out.println(plusWeeks(1));

      }
}
