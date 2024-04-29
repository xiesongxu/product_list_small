package com.storems.admin.utils;

import sun.text.resources.FormatData;

import javax.swing.text.DateFormatter;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.UUID;

/**
 * 商品工具类
 */
public class ProductUtil {

    /**
     * 生成流水号
     * @return
     */
    public static String getSerialNo() {
        //获取时间戳
        String str = String.valueOf(System.currentTimeMillis());
        SimpleDateFormat formatData = new SimpleDateFormat("yyyyMMdd");
        //拼接当前时间和时间戳
        str = formatData.format(new Date()) + UUID.randomUUID().toString().replaceAll("-","").substring(0,6) + str.substring(7,str.length());
        return str;
    }

    /**
     * 获取当前时间
     * @return
     */
    public static String getCurDate() {
        SimpleDateFormat formatData = new SimpleDateFormat("yyyy/MM/dd");
        return formatData.format(new Date());
    }

    /**
     * 获取与当前时间的关联时间
     * @param date
     * @param year
     * @param month
     * @param day
     * @param format
     * @return
     */
    public static String getRelativeDate(Date date,int year,int month,int day,String format) {
        //创建时间格式
        SimpleDateFormat formatData = new SimpleDateFormat(format);
        //创建日历对象
        GregorianCalendar calendar = new GregorianCalendar();
        //设置为当前时间
        calendar.setTime(date);
        //调整具体的时间
        calendar.add(1,year);
        calendar.add(2,month);
        calendar.add(5,day);
        //返回关联时间
        return formatData.format(calendar.getTime());
    }

    public static void main(String[] args) {
        System.out.println(getRelativeDate(new Date(),0,0,-1,"yyyy/MM/dd"));
    }
}
