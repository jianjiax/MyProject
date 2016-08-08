package com.example.myproject.utils;

import android.text.TextUtils;

import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * Created by Kain on 2016/8/8.
 */
public class DateUtils {

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
}
