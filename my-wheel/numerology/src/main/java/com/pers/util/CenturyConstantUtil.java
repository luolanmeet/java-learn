package com.pers.util;

/**
 * 计算日柱
 * 世纪常数工具类
 * @auther ken.ck
 * @date 2021/11/7 22:21
 */
public class CenturyConstantUtil {

    /**
     * 世纪常数
     */
    private static final CenturyConstant[] CENTURY_CONSTANT = new CenturyConstant[]{
            new CenturyConstant(1601, 1700, 17, 3),
            new CenturyConstant(1701, 1800, 18, 47),
            new CenturyConstant(1801, 1900, 19, 31),
            new CenturyConstant(1901, 2000, 20, 15),
            new CenturyConstant(2001, 2100, 21, 0),
            new CenturyConstant(2101, 2100, 22, 44),
            new CenturyConstant(2201, 2300, 23, 28),
            new CenturyConstant(2301, 2400, 24, 12),
            new CenturyConstant(2401, 2500, 25, 57),
            new CenturyConstant(2501, 2600, 26, 41),
    };

    /**
     * 月基数表
     */
    public static final int[] MONTH_BASE = new int[] {0, 31, -1, 30, 0, 31, 1, 32, 3, 33, 4, 34};

    /**
     * 获取世纪常数
     * @param year
     * @return
     */
    public static CenturyConstant getCenturyConstant(int year) {

        for (CenturyConstant centuryConstant : CENTURY_CONSTANT) {
            if (centuryConstant.inRange(year)) {
                return centuryConstant;
            }
        }
        return null;
    }

    public static class CenturyConstant {

        /** 年限范围 */
        private final Range range;
        /** 世纪数 */
        private final int n;
        /** 世纪常数 */
        private final int x;

        public CenturyConstant(int leftYear, int rightYear, int n, int x) {
            this.range = Range.range(leftYear, rightYear);
            this.n = n;
            this.x = x;
        }

        public boolean inRange(int year) {
            return range.isContain(year);
        }

        public int getX() {
            return x;
        }
    }

}
