package com.aspire.bpom.util;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

public class DateUtil {
	
	/**获取指定Date时间戳
	 * 格式默认 yyyyMMddHHmmssSSS
	 * @param date
	 * @return
	 */
	public static String getTimeStamp(Date date){
		return getTimeStamp(date, "yyyyMMddHHmmssSSS");
	}
	public static String getForTimeStamp(Date date){
        return getTimeStamp(date, "yyyyMMddHHmmss");
    }
	
	public static String getTimeStamp(Date date, String format){
		SimpleDateFormat sdf = new SimpleDateFormat(format);
		return sdf.format(date);
	}
	
	public static String getCurrentTimeStamp(String format){
		return getTimeStamp(new Date(), format);
	}
	
	/**获取当前时间时间戳
	 * 格式默认 yyyyMMddHHmmssSSS
	 * @param date
	 * @return
	 */
	public static String getCurrentTimeStamp(){
		return getCurrentTimeStamp("yyyyMMddHHmmssSSS");
	}
	
	public static Date getDate(String date, String format) throws ParseException{
		SimpleDateFormat sdf = new SimpleDateFormat(format);
		return sdf.parse(date);
	}
	
	public static Date getDate(String date) throws ParseException{
		return getDate(date, "yyyyMMddHHmmssSSS");
	}
	
	/**
	 * 获取今天的日期
	 */
	public static String getToday(){
	    Calendar cal = Calendar.getInstance();
	    Date date = cal.getTime();
	    return DateTimeUtil.format(date, "yyyyMMdd");
	}
    /**
     * 获取前一天的日期
     * @return
     */
	public static  String getYesteday() {
        Calendar cal = Calendar.getInstance();
        cal.add(Calendar.DAY_OF_MONTH, -1);
        Date date = cal.getTime();
        return DateTimeUtil.format(date, "yyyyMMdd");
    }
    
	 /**
     * @param date
     * @param n
     * @return
     * @方法名称：对某日期加减分钟
     */
    public static Date addMinute(Date date, int n) {
        Calendar calendar = Calendar.getInstance();
        calendar.setTimeInMillis(date.getTime());

        calendar.add(Calendar.MINUTE, n);

        return new Date(calendar.getTimeInMillis());
    }
    
    /**
     * @param date
     * @param n
     * @return
     * @方法名称：对某日期加减天
     */
    public static Date addDay(Date date, int n) {
        Calendar calendar = Calendar.getInstance();
        calendar.setTimeInMillis(date.getTime());
        
        calendar.add(Calendar.DAY_OF_MONTH, n);

        return new Date(calendar.getTimeInMillis());
    }
    
	/**
	 * 获取前一天的日期
	 * @return  yyyyMMdd
	 */
	public static String getTime() {
		Calendar cal = Calendar.getInstance();
		cal.add(Calendar.DAY_OF_MONTH, -1);
		Date date = cal.getTime();
		return DateTimeUtil.format(date, "yyyyMMdd");
	}
	
    /**
     * @param date
     * @param n
     * @return
     * @方法名称：对某日期加减天
     */
    public static Date addMonth(Date date, int n) {
        Calendar calendar = Calendar.getInstance();
        calendar.setTimeInMillis(date.getTime());

        calendar.add(Calendar.MONTH, n);

        return new Date(calendar.getTimeInMillis());
    }
}
