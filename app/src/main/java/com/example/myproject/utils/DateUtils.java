package com.example.myproject.utils;

import android.text.TextUtils;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Locale;

/**
 * Created by Kain on 2016/8/8.
 */
public class DateUtils {

    private static ThreadLocal<SimpleDateFormat> DateLocal = new ThreadLocal<SimpleDateFormat>();

    /**
     * 获取格式化对象
     * @return
     */
    public static SimpleDateFormat getDateFormat() {
        if (null == DateLocal.get()) {
            DateLocal.set(new SimpleDateFormat("yyyy-MM-dd", Locale.CHINA));
        }
        return DateLocal.get();
    }

    /**
     * 根据格式时间得到linux时间戳
     * @param time
     * @return
     */
    public static String date2TimeStamp(String time){
        if(TextUtils.isEmpty(time)){
            return "";
        }
        SimpleDateFormat format =  new SimpleDateFormat("yyyy-MM-dd");
        Date date = null;
        try {
            date = format.parse(time);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return String.valueOf(date.getTime() / 1000);
    }

    /**
     * 日期格式化
     *
     * @param paramLong
     * @return
     */
    public static String getTimeFromSec(long paramLong) {
        Long localLong = Long.valueOf(1000L * Long.valueOf(paramLong).longValue());
        return new SimpleDateFormat("yyyy-MM-dd HH:mm").format(new Long(localLong.longValue()));
    }

    /**
     * 根据linux时间戳得到格式时间
     * @param str
     * @return
     */
    public static String timeStampFormat(String str) {
        try {
            if (TextUtils.isEmpty(str)) {
                return "";
            } else {
                SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm");
                Date date = new Date(Long.parseLong(str) * 1000);
                return sdf.format(date);
            }
        } catch (NumberFormatException e) {
            return "";
        }
    }

    /**
     * 日期格式化
     *
     * @return
     */
    public static String timeStampFormat(String str,String pattern) {
        if(TextUtils.isEmpty(str)){
            return "";
        }
        if(TextUtils.isEmpty(pattern)){
            pattern = "yyyy-MM-dd HH:mm";
        }
        try{
            SimpleDateFormat sdf = new SimpleDateFormat(pattern);
            Date date = new Date(Long.parseLong(str) * 1000);
            return sdf.format(date);
        }catch (NumberFormatException e){
            return "";
        }
    }

    /**
     * 获取该时间与今天的时间差
     * E.x yesterday = -1;tomorrow = 1
     * @param day 传入的 时间  "2016-06-28 10:10:30" "2016-06-28" 都可以
     * @return
     * @throws ParseException
     */
    public static int getDatePre(String day) throws ParseException {
        if(TextUtils.isEmpty(day)){
            return 0;
        }
        Calendar pre = Calendar.getInstance();
        Date predate = new Date(System.currentTimeMillis());
        pre.setTime(predate);

        Calendar cal = Calendar.getInstance();
        Date date = getDateFormat().parse(day);
        cal.setTime(date);

        if (cal.get(Calendar.YEAR) == (pre.get(Calendar.YEAR))) {
            int diffDay = cal.get(Calendar.DAY_OF_YEAR)
                    - pre.get(Calendar.DAY_OF_YEAR);

            return diffDay;
        }
        return -1;
    }

    /**
     * 获取时间差的字符串形式
     * 如：1天1时1分1秒
     *
     * @param time 秒
     * @return
     */
    public static String timeFormat(long time) {
        String str = "";
        time = time / 1000;
        //1天之内  只显示小时  1小时之内的 只显示分钟  1分钟之内 的只显示秒
        int s = (int) (time % 60);
        int m = (int) (time / 60 % 60);
        int h = (int) (time / 3600);
        int days = ((int) time) / (3600 * 24);
        if (days > 0) {
            str = days + "天";
            h = h - (days * 24);
        }
        str += h + "小时" + m + "分" + s + "秒";
        return str;
    }

    /**
     * 获取时间差的字符串形式
     * 如：1天 或 1时 或 1分 或 1秒
     *
     * @param time 秒数
     * @return
     */
    public static String timeDifferentFormat(long time) {
        if (time <= 0) {
            return "";
        }
        String str = "";
        time = time / 1000;
        //1天之内  只显示小时  1小时之内的 只显示分钟  1分钟之内 的只显示秒
        int s = (int) (time % 60);
        int m = (int) (time / 60 % 60);
        int h = (int) (time / 3600);
        int days = ((int) time) / (3600 * 24);
        if (days > 0) {
            str = days + "天";
        } else if (h > 0) {
            str = h + "小时";
        } else if (m > 0) {
            str = m + "分";
        } else if (s > 0) {
            str = s + "秒";
        }
//        str += h + "小时" + m + "分" + s + "秒";
        return str;
    }

    /**
     * 获取当前时间x天前的日期(不包括当前)
     * @param days x天前
     * @return
     */
    public static String getPreDate(int days){
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");

        Calendar c = Calendar.getInstance();
        c.add(Calendar.DATE, - (days + 1));
        Date monday = c.getTime();
        String preMonday = sdf.format(monday);
        return preMonday;
    }

    /**
     * 根据开始时间和结束时间返回时间段内的时间集合
     *
     * @param start
     * @param end
     * @return List
     */
    public static ArrayList<String> getDatesBetweenTwoDate(String start, String end) {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        Date dBegin = null;
        Date dEnd = null;
        try {
            dBegin = sdf.parse(start);
            dEnd = sdf.parse(end);
        } catch (ParseException e) {
            e.printStackTrace();
        }

        List<Date> lDate = new ArrayList<Date>();

        Calendar cal = Calendar.getInstance();
        // 使用给定的 Date 设置此 Calendar 的时间
        cal.setTime(dBegin);
        boolean bContinue = true;
        while (bContinue) {
            // 根据日历的规则，为给定的日历字段添加或减去指定的时间量
            cal.add(Calendar.DAY_OF_MONTH, 1);
            // 测试此日期是否在指定日期之后
            if (dEnd.after(cal.getTime())) {
                lDate.add(cal.getTime());
            } else {
                break;
            }
        }
        lDate.add(dEnd);// 把结束时间加入集合

        ArrayList<String> dateList = new ArrayList<>();
        for(int i=0;i<lDate.size();i++){
            dateList.add(new SimpleDateFormat("MM-dd").format(lDate.get(i)));
        }
        return dateList;
    }
}
