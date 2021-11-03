package com.pers.util;

import com.sun.tools.javac.util.Assert;

/**
 * @auther ken.ck
 * @date 2021/10/31 18:48
 */
public final class TianganDizhi {

    private final static String[] TIAN_GAM = new String[] {"甲","乙","丙","丁","戊","己","庚","辛","壬","癸"};
    private final static String[] DI_ZHI = new String[] {"子","丑","寅","卯","辰","巳","午","未","申","酉","戌","亥"};

    public static String getChronicle() {
        return null;
    }

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

    public static void main(String[] args) {

        System.out.println(getYear(4343));

//        for (int i = -10; i < 10; i++) {
//            if (i == 0) continue;
//            System.out.println(i + " " + getYear(i));
//        }
    }

}
