package com.pers.util;

import com.sun.tools.javac.util.Assert;

/**
 * https://wannianli.tianqi.com/huangli
 *
 * @auther ken.ck
 * @date 2021/10/31 18:48
 */
public final class TianganDizhi {

    private final static String[] TIAN_GAM = new String[] {"甲","乙","丙","丁","戊","己","庚","辛","壬","癸"};
    private final static String[] DI_ZHI = new String[] {"子","丑","寅","卯","辰","巳","午","未","申","酉","戌","亥"};

    public static String getChronicle(int year, int month, int day, int hour) {
        return null;
    }

    /**
     * 干支纪年
     * @param year
     * @return
     */
    public static String getYear(int year) {

        Assert.check(year != 0);

        int add = 0;
        if (year < 0) {
            add = 1;
        }
        year -= 3;
        int a = (year % 10 + 9 + add) % 10;
        int b = (year % 12 + 11 + add) % 12;
        return TIAN_GAM[a] + DI_ZHI[b];
    }

    /**
     * 干支纪月
     * @param month
     * @return
     */
    public static String getMonth(int year, int month) {

        String yearTg = getYear(year).substring(0, 1);
        int monthTg = 0;
        switch (yearTg) {
            case "甲": case "己": monthTg = 2; break;
            case "乙": case "庚": monthTg = 4; break;
            case "丙": case "辛": monthTg = 6; break;
            case "丁": case "壬": monthTg = 8; break;
            case "戊": case "癸": monthTg = 0; break;
            default: throw new RuntimeException("error yearTg: " + yearTg);
        }

        monthTg = (monthTg + month + 9) % 10;

        return TIAN_GAM[monthTg] + DI_ZHI[(month + 1) % 12];
    }

    /**
     * 干支纪日
     * @param year
     * @param month
     * @param day
     * @return
     */
    public static String getDay(int year, int month, int day) {

        CenturyConstantUtil.CenturyConstant centuryConstant
                = CenturyConstantUtil.getCenturyConstant(year);
        Assert.checkNonNull(centuryConstant);

        int add = isLeapYear(year) && month > 2 ? 1 : 0;

        int s = (year % 100 - 1) / 4;
        int u = (year - 1) % 4;
        int r = s * 6 + (s * 3 + u) * 5
                + CenturyConstantUtil.MONTH_BASE[month - 1]
                + day + centuryConstant.getX() + add;

        return getJiaZi(r % 60);
    }

    /**
     * 干支计时
     * @param hour
     * @return
     */
    public static String getHour(int year, int month, int day, int hour) {

        String dayTg = getDay(year, month, day).substring(0, 1);
        int hourTg = 0;
        switch (dayTg) {
            case "甲": case "己": hourTg = 0; break;
            case "乙": case "庚": hourTg = 2; break;
            case "丙": case "辛": hourTg = 4; break;
            case "丁": case "壬": hourTg = 6; break;
            case "戊": case "癸": hourTg = 8; break;
            default: throw new RuntimeException("error dayTg: " + dayTg);
        }

        if (hour == 0 || hour == 23) {
            return TIAN_GAM[hourTg] + DI_ZHI[0];
        }

        int tmp = 1;
        for (int i = 1; i < DI_ZHI.length; i++) {
            if (hour >= tmp && hour <= tmp + 1) {
                hourTg = (hourTg + i) % 10;
                return TIAN_GAM[hourTg] + DI_ZHI[i];
            }
            tmp += 2;
        }
        throw new RuntimeException("error hour : " + hour);
    }

    /**
     * 根据数字获取甲子
     * @param num
     * @return
     */
    public static String getJiaZi(int num) {

        Assert.check(num > 0 && num < 61, num);

        int a = (num % 10 + 9) % 10 ;
        int b = (num % 12 + 11) % 12 ;
        return TIAN_GAM[a] + DI_ZHI[b];
    }

    /**
     * 判断是否为闰年
     * @param year
     * @return
     */
    public static boolean isLeapYear(int year) {
        return
                   (year % 4 == 0 && year % 100 != 0)
                || (year % 400 == 0 && year % 3200 != 0)
                || (year % 172800 == 0);
    }

    public static void main(String[] args)  {

//        System.out.println(getMonth(2021, 11)); // 己亥
        System.out.println(getMonth(2021, 8)); // 丙申

//        System.out.println(getDay(1949, 10, 1)); // 甲子
//        System.out.println(getDay(2008, 5, 12)); // 壬子
//        System.out.println(getDay(1895, 4, 17)); // 甲午
//        System.out.println(getDay(2021,  11, 14)); // 丙寅
//        System.out.println(getDay(2020,  5, 6)); // 己酉

//        for (int i = 1; i <= 60; i++) {
//            System.out.println(i + " " + getJiaZi(i));
//        }

//        for (int i = 0; i < 24; i++) {
//            System.out.println(i + " " + getHour(2021, 11, 8, i));
//        }

//        for (int i = -10; i < 10; i++) {
//            if (i == 0) continue;
//            System.out.println(i + " " + getYear(i));
//        }
    }

}
