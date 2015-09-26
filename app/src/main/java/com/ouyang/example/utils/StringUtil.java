package com.ouyang.example.utils;

import java.security.MessageDigest;
import java.text.DecimalFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * String帮助类
 * @author ouyangzn
 */
public class StringUtil {
    
    private static final String TAG = StringUtil.class.getSimpleName();

    /**
     * 判断传入的字符串是否为空
     * @param str
     * @return 传入的字符串为空返回true，反之返回flase
     */
    public static boolean isEmpty(String str) {
        if (str == null || "".equals(str)) {
            return true;
        }
        else {
            return false;
        }
    }

    /**
     * 判断传入的字符串是否不为空
     * @param str
     * @return 传入的字符串不为空返回true，反之返回false
     */
    public static boolean isNotEmpty(String str) {
        return !isEmpty(str);
    }

    /**
     * 判断传入的字符串是否是字母
     * @param str
     * @return
     */
    public static boolean isLetters(String str) {
        if (isNotEmpty(str) && str.matches("^[A-Za-z]+$")) {
            return true;
        }
        return false;
    }

    /**
     * 获取元素在数组的下标
     * @param arr String数据
     * @param item 元素名
     * @return 数组下标，如果数组中找不到则返回-1
     */
    public static int getIndex(String[] arr, String item) {
        int index = -1;
        for (int i = 0; i < arr.length; i++) {
            if (arr[i].equals(item)) {
                index = i;
                break;
            }
        }
        return index;
    }

    /**
     * 验证传入的11位手机号是否正确
     * @param mobiles
     * @return
     */
    public static boolean isMobileNum11(String mobiles) {
        Pattern p = Pattern
            .compile("^((13[0-9])|(15[^4,\\D])|(18[^4,\\D])|(147)|(17[0-9]))\\d{8}$");
        Matcher m = p.matcher(mobiles);
        return m.matches();
    }

    /**
     * MD5加密，32位
     * @param str
     * @return
     */
    public static String MD5(String str) {
        MessageDigest md5 = null;
        try {
            md5 = MessageDigest.getInstance("MD5");
        }
        catch (Exception e) {
            e.printStackTrace();
            return "";
        }
        char[] charArray = str.toCharArray();
        byte[] byteArray = new byte[charArray.length];
        for (int i = 0; i < charArray.length; i++) {
            byteArray[i] = (byte) charArray[i];
        }
        byte[] md5Bytes = md5.digest(byteArray);
        StringBuffer hexValue = new StringBuffer();
        for (int i = 0; i < md5Bytes.length; i++) {
            int val = ((int) md5Bytes[i]) & 0xff;
            if (val < 16) {
                hexValue.append("0");
            }
            hexValue.append(Integer.toHexString(val));
        }
        return hexValue.toString();
    }

    /**
     * 判断是否是数字
     * @param str
     * @return
     */
    public static boolean isNumeric(String str) {
        Pattern pattern = Pattern.compile("[0-9]*");
        return pattern.matcher(str).matches();
    }

    /**
     * 去掉中文字符
     * @param chines
     * @return
     */
    public static String replaceString(String chines) {
        return chines.replace("《", "").replace("》", "").replace("！", "")
            .replace("￥", "").replace("【", "").replace("】", "")
            .replace("（", "").replace("）", "").replace("－", "")
            .replace("；", "").replace("：", "").replace("”", "")
            .replace("“", "").replace("。", "").replace("，", "")
            .replace("、", "").replace("？", "").replace(" ", "")
            .replace("-", "");
    }

    /**
     * 将日期Time转换为日期字符串
     * @param time
     * @param format 格式 eg: MM月dd日 HH:mm
     * @return
     */
    public static String formatDate2String(long time, String format) {
        SimpleDateFormat sdf = new SimpleDateFormat(format, Locale.CHINA);
        return sdf.format(new Date(time));
    }

    /**
     * 获取当前时间
     * @param format 格式
     * @return
     */
    public static String getNowDate(String format) {
        SimpleDateFormat sdf = new SimpleDateFormat(format, Locale.CHINA);
        sdf.format(new Date());
        return sdf.format(new Date());
    }

    /**
     * 转换时间格式
     * @param str 时间字符串
     * @param format 原时间格式
     * @param newFormat 转换后的格式
     * @return 转换格式后的时间字符串,如果原字符串不是时间，则返回空字符串
     */
    public static String formatDate(String str, String format, String newFormat) {
        SimpleDateFormat sdf = new SimpleDateFormat(format, Locale.CHINA);
        String date = "";
        try {
            Date d = sdf.parse(str);
            date = formatDate2String(d.getTime(), newFormat);
        }
        catch (ParseException e) {
            Log.e(TAG, "转换格式出错:", e);
        }
        return date;
    }

    /**
     * 将日期转换为time
     * @param date 时间字符串
     * @param format 时间格式
     * @return
     */
    public static long date2time(String date, String format) {
        long dtime = 0L;
        SimpleDateFormat sdf = new SimpleDateFormat(format, Locale.CHINA);
        try {
            Date d = sdf.parse(date);
            dtime = d.getTime();
        }
        catch (ParseException e) {
            Log.e(TAG, "将日期转换为time出错:", e);
        }
        return dtime;
    }
    
    /**
     * 返回byte的数据大小对应的(KB/MB/GB/TB)文本,有小数则保留2位小数  eg:1.11KB
     * @param size
     * @return
     */
    public static String getDataSize(long size) {
        if (size < 0) {
            size = 0;
        }
        DecimalFormat formater = new DecimalFormat("####0.00");
        if (size < 1024) {
            return size + "B";
        }
        else if (size < 1024 * 1024) {
            float kbsize = size / 1024f;
            return formater.format(kbsize) + "KB";
        }
        else if (size < 1024 * 1024 * 1024) {
            float mbsize = size / 1024f / 1024f;
            return formater.format(mbsize) + "MB";
        }
        else if (size < 1024 * 1024 * 1024 * 1024) {
            float gbsize = size / 1024f / 1024f / 1024f;
            return formater.format(gbsize) + "GB";
        }
        else {
            float tbsize = size / 1024f / 1024f / 1024f / 1024f;
            return formater.format(tbsize) + "TB";
        }
    }

    /**
     * 判断字符串是否有汉字
     * @param str
     * @return
     */
    public static boolean hasChinese(String str) {
        return str.length() == str.getBytes().length ? false : true;
    }

}