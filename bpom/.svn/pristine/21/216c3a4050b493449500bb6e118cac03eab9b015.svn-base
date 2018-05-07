package com.aspire.bpom.util;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;


/**
 * 对日期+时间进行格式化处理
 */
public class DateTimeUtil {
    private static final LocalDateFormat SDF = new LocalDateFormat("yyyy-MM-dd HH:mm:ss");
    private static final LocalDateFormat SDF2 = new LocalDateFormat("yyyyMMddHHmmss");
    private static final LocalDateFormat SDF3 = new LocalDateFormat("yyyyMMdd");
  
    /**
     * 获取当前时间字符串，默认带分隔符
     * 
     * @return
     */
    public static String getCurrentTime() {
        return getCurrentTime(true);
    }
    
    /**
     * 获取当前时间字符串
     * @param num 1：yyyy-MM-dd HH:mm:ss；2：yyyyMMddHHmmss；3：yyyyMMdd
     * @return
     */
    public static String getCurrentTime2(int num) {
    	SimpleDateFormat sdf = getFormatter2(num);
    	return sdf.format(new Date());
    }

    /**
     * 获取当前时间字符串
     * 
     * @param useDelimiters
     *            是否需要带有分隔符
     * @return
     */
    public static String getCurrentTime(boolean useDelimiters) {
        SimpleDateFormat sdf = getFormatter(useDelimiters);
        return sdf.format(new Date());
    }

    /**
     * 带分隔符格式的字符串转换为Date
     * 
     * @param orderDate
     * @return
     */
    public static Date parse(String orderDate) {
        return parse(orderDate, true);
    }

    /**
     * 字符串转换为Date
     * 
     * @param orderDate
     * @param useDelimiters
     *            字符串是否带有分隔符
     * @return
     */
    public static Date parse(String orderDate, boolean useDelimiters) {
        Date date = null;
        try {
            SimpleDateFormat sdf = getFormatter(useDelimiters);
            date = sdf.parse(orderDate);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return date;
    }

    /**
     * Date转换为带有分隔符的格式的字符串
     * 
     * @param date
     *            需要转换的时间
     * @return
     */
    public static String format(Date date) {
        return format(date, true);
    }

    /**
     * Date转换为字符串
     * 
     * @param date
     *            需要转换的时间
     * @param useDelimiters
     *            是否需要带有分隔符
     * @return
     */
    public static String format(Date date, boolean useDelimiters) {
        if (date == null) {
            return "";
        } else {
            SimpleDateFormat sdf = getFormatter(useDelimiters);
            return sdf.format(date);
        }
    }

    private static SimpleDateFormat getFormatter(boolean useDelimiters) {   	
        return (useDelimiters ? SDF : SDF2).get();
    }
    private static SimpleDateFormat getFormatter2(int num) {
    	SimpleDateFormat sdf = null;
    	if(num == 1){
    		sdf = SDF.get();
    	}else if(num == 2){
    		sdf = SDF2.get();
    	}else if(num == 3){
    		sdf = SDF3.get();
    	}
    	return sdf;
    }

    public static Date getCurrentDate() {
        return new Date();
    }

    public static String format(Date date, String format) {
        if (date == null) {
            return "";
        } else {
            return new SimpleDateFormat(format).format(date);
        }
    }

    public static String format(String date, String preFormat, String afterFormat) {
        if (date == null) {
            return "";
        } else {
            return new SimpleDateFormat(afterFormat).format(parse(date, preFormat));
        }
    }

    public static Date parse(String str, String format) {
        try {
            return new SimpleDateFormat(format).parse(str);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return null;
    }

    private static class LocalDateFormat extends ThreadLocal<SimpleDateFormat> {
        private String format;

        public LocalDateFormat(String format) {
            this.format = format;
        }

        @Override
        protected SimpleDateFormat initialValue() {
            return new SimpleDateFormat(format);
        }
    }
}
