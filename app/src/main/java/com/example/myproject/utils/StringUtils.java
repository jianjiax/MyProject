package com.example.myproject.utils;

import android.text.TextUtils;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Created by Kain on 2016/8/15.
 */
public class StringUtils {


    /**
     * 判断字符串是否相等
     * @param str1
     * @param str2
     * @return
     */
    public static boolean textEquals(String str1,String str2){
        if(!TextUtils.isEmpty(str1) && !TextUtils.isEmpty(str2)){
            return str1.equals(str2);
        }else return TextUtils.isEmpty(str1) && TextUtils.isEmpty(str2);
    }

    // 只能是数字,英文字母和中文
    public static boolean isValidTagAndAlias(String s) {
        Pattern p = Pattern.compile("^[\u4E00-\u9FA50-9a-zA-Z_-]{0,}$");
        Matcher m = p.matcher(s);
        return m.matches();
    }

    /**
     * @param number
     * @return
     * @Title : filterNumber
     * @Type : FilterStr
     * @date : 2014年3月12日 下午7:23:03
     * @Description : 过滤出数字
     */
    public static String filterNumber(String number) {
        number = number.replaceAll("[^(0-9)]", "");
        return number;
    }

    /**
     * @param alph
     * @return
     * @Title : filterAlphabet
     * @Type : FilterStr
     * @date : 2014年3月12日 下午7:28:54
     * @Description : 过滤出字母
     */
    public static String filterAlphabet(String alph) {
        alph = alph.replaceAll("[^(A-Za-z)]", "");
        return alph;
    }

    /**
     * @param chin
     * @return
     * @Title : filterChinese
     * @Type : FilterStr
     * @date : 2014年3月12日 下午9:12:37
     * @Description : 过滤出中文
     */
    public static String filterChinese(String chin) {
        chin = chin.replaceAll("[^(\\u4e00-\\u9fa5)]", "");
        return chin;
    }

    /**
     * @param character
     * @return
     * @Title : filter
     * @Type : FilterStr
     * @date : 2014年3月12日 下午9:17:22
     * @Description : 过滤出字母、数字和中文
     */
    public static String filter(String character) {
        character = character.replaceAll("[^(a-zA-Z0-9\\u4e00-\\u9fa5)]", "");
        return character;
    }

    /**
     * @param character
     * @return
     * @Title : filter
     * @Type : FilterStr
     * @date : 2014年3月12日 下午9:17:22
     * @Description : 过滤出中文和字母
     */
    public static String keepNumFilter(String character) {
        character = character.replaceAll("[^(a-zA-Z\\u4e00-\\u9fa5)]", "");
        return character;
    }

    /**
     * string to json
     *
     * @param str
     * @return
     */
    public static JSONObject string2JSON(String str) {
        try {
            return new JSONObject(str);
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return new JSONObject();
    }

    /**
     * 检查输入的数据中是否有特殊字符
     *
     * @param qString 要检查的数据
     * @param regx    特殊字符正则表达式
     * @return boolean 如果包含正则表达式 <code> regx </code> 中定义的特殊字符，返回true；
     * 否则返回false
     */
    public static boolean hasCrossScriptRisk(String qString) {
        if (qString != null) {
//            qString = qString.trim();
            String regx = "[`~!@#$%^&*()+=|{}':;',\\[\\].<>/?~！@#￥%……&*（）——+|{}【】‘；：”“’。，、？]";
            Pattern p = Pattern.compile(regx, Pattern.CASE_INSENSITIVE);
            Matcher m = p.matcher(qString);
            return m.find() || qString.contains(" ");
        }
        return false;
    }

    // 信息隐藏处理
    public static String dealWithAccount(String oStr){
        if(TextUtils.isEmpty(oStr)){
            return "";
        }

        String nStr;
        // 保留多少位，如=2，则xx*******xx
        int keep = 0;
        int strLength = oStr.length();
        if(strLength > 5 && strLength < 10){
            keep = 2;
        }else if(strLength >= 10){
            keep = 4;
        }else{
            return oStr;
        }
        int end = strLength - keep;
        StringBuilder replaceStr = new StringBuilder();
        for(int i = 0; i < strLength - keep * 2;i ++){
            replaceStr.append("*");
        }
        nStr = oStr.substring(0,keep) + replaceStr + oStr.substring(end,strLength);

        return nStr;
    }
}
