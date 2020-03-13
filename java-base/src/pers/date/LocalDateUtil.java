package pers.date;

import java.time.*;
import java.time.temporal.ChronoUnit;
import java.time.temporal.TemporalAdjusters;
import java.util.Date;

/**
 * LocalDate LocalDateTime Java8中提供的很好用的日期工具
 */
public class LocalDateUtil {

    // 时区
    private static final ZoneId ZONE = ZoneId.systemDefault();
    private static final ZoneOffset ZONE_OFFSET = ZoneOffset.of("+8");

    /**
     * 时间戳转LocalDate
     * @param ms
     * @return
     */
    public static LocalDate ms2ld(Long ms) {
        Date date = new Date(ms);
        return date.toInstant().atZone(ZONE).toLocalDate();
    }

    /**
     * 时间戳转LocalDateTime
     * @param ms
     * @return
     */
    public static LocalDateTime ms2ldt(Long ms) {
        Date date = new Date(ms);
        return date.toInstant().atZone(ZONE).toLocalDateTime();
    }

    /**
     * LocalDate转时间戳
     * @param ld
     * @param isLatest 是否是当天最迟的时间
     * @return
     */
    public static Long ld2ms(LocalDate ld, boolean isLatest) {
        LocalDateTime ldt = null;
        if (isLatest) {
            ldt = ld.atTime(23, 59, 59, 999999999);
        } else {
            ldt = ld.atTime(0, 0, 0);
        }
        return ldt2ms(ldt);
    }

    /**
     * LocalDateTime转时间戳
     * @param ldt
     * @return
     */
    public static Long ldt2ms(LocalDateTime ldt) {
        return ldt.toInstant(ZONE_OFFSET).toEpochMilli();
    }

    /**
     * 获取传入的时间所在的周的周一
     * @param ms
     * @return
     */
    public static LocalDate getMonday(Long ms) {
        return ms2ld(ms).with(DayOfWeek.MONDAY);
    }

    /**
     * 获取传入的日期所在的周的周一
     * @param ld
     * @return
     */
    public static LocalDate getMonday(LocalDate ld) {
        return ld.with(DayOfWeek.MONDAY);
    }

    /**
     * 获取传入的时间所在的周的周一
     * @param ms
     * @return
     */
    public static Long getMondayMs(Long ms) {
        LocalDate monday
                = ms2ld(ms).with(DayOfWeek.MONDAY);
        return ld2ms(monday, false);
    }

    /**
     * 获取传入的时间所在的周的周日
     * @param ms
     * @return
     */
    public static LocalDate getSunday(Long ms) {
        return ms2ld(ms).with(DayOfWeek.SUNDAY);
    }

    /**
     * 获取传入的时间所在的周的周日
     * @param ld
     * @return
     */
    public static LocalDate getSunday(LocalDate ld) {
        return ld.with(DayOfWeek.SUNDAY);
    }

    /**
     * 获取传入的时间所在的周的周日
     * @param ms
     * @return
     */
    public static Long getSundayMs(Long ms) {
        LocalDate monday
                = ms2ld(ms).with(DayOfWeek.SUNDAY);
        return ld2ms(monday, true);
    }

    /**
     * 获取传入的时间所在的月的第一天凌晨毫秒数
     * @return
     */
    public static Long getFirstDayMsOfMonth(Long ms) {
        return ld2ms(ms2ld(ms).with(TemporalAdjusters.firstDayOfMonth()), false);
    }

    /**
     * 获取当前月的第一天凌晨毫秒数
     * @return
     */
    public static Long getFirstDayMsOfMonth() {
        return ld2ms(LocalDate.now().with(TemporalAdjusters.firstDayOfMonth()), false);
    }

    /**
     * 获取传入的时间所在的月的最后一天59分59秒毫秒数
     * @return
     */
    public static Long getLastDayMsOfMonth(Long ms) {
        return ld2ms(ms2ld(ms).with(TemporalAdjusters.lastDayOfMonth()), true);
    }

    /**
     * 获取当前月的最后一天59分59秒毫秒数
     * @return
     */
    public static Long getLastDayMsOfMonth() {
        return ld2ms(LocalDate.now().with(TemporalAdjusters.lastDayOfMonth()), true);
    }

    /**
     * 获取传入的时间所在的月的第一天
     * @return
     */
    public static LocalDate getFirstDayOfMonth(Long ms) {
        return ms2ld(ms).with(TemporalAdjusters.firstDayOfMonth());
    }

    /**
     * 获取当前月的第一天
     * @return
     */
    public static LocalDate getFirstDayOfMonth() {
        return LocalDate.now().with(TemporalAdjusters.firstDayOfMonth());
    }

    /**
     * 获取传入的时间所在的月的最后一天
     * @return
     */
    public static LocalDate getLastDayOfMonth(Long ms) {
        return ms2ld(ms).with(TemporalAdjusters.lastDayOfMonth());
    }

    /**
     * 获取当前月的最后一天
     * @return
     */
    public static LocalDate getLastDayOfMonth() {
        return LocalDate.now().with(TemporalAdjusters.lastDayOfMonth());
    }

    /**
     * 获取传入的时间所在的月的天数
     * @return
     */
    public static Integer getLengthOfMonth(Long ms) {
        return ms2ld(ms).lengthOfMonth();
    }

    /**
     * 获取当前月的天数
     * @return
     */
    public static Integer getLengthOfMonth() {
        return LocalDate.now().lengthOfMonth();
    }

    /**
     * 获取两个日期之间相差多少天
     * @param ld1
     * @param ld2
     * @return
     */
    public static Integer betweenDay(LocalDate ld1, LocalDate ld2) {
        return (int) (ld2.toEpochDay() - ld1.toEpochDay());
    }

    /**
     * 获取两个时间之间相差多少天
     * @param ms1
     * @param ms2
     * @return
     */
    public static Integer betweenDay(Long ms1, Long ms2) {
        return betweenDay(ms2ld(ms1), ms2ld(ms2));
    }

    /**
     * 是否是周六
     * @param ld
     * @return
     */
    public static boolean isSaturday(LocalDate ld) {
        return  ld.getDayOfWeek().getValue() == 6;
    }

    /**
     * 是否是星期六
     * @param ms
     * @return
     */
    public static boolean isSaturday(Long ms) {
        return isSaturday(ms2ld(ms));
    }

    /**
     * 获取传入的时间戳所在天的0时0分0秒时间戳
     * @return
     */
    public static Long ms2EarlyMs(Long ms) {
        return ld2ms(ms2ld(ms), false);
    }

    /**
     * 获取传入的时间戳所在天的23时59分59秒时间戳
     * @return
     */
    public static Long ms2LateMs(Long ms) {
        return ld2ms(ms2ld(ms), true);
    }

    public static void main(String[] args) {

        System.out.println(isSaturday(1546642800000L)); // 2019-01-05 07:00:00
        System.out.println(isSaturday(1546617600000L)); // 2019-01-05 00:00:00
        System.out.println(isSaturday(System.currentTimeMillis()));
        System.out.println();

        System.out.println(getLengthOfMonth(1546066256000L));
        System.out.println();

        System.out.println(getFirstDayOfMonth(1546066256000L));
        System.out.println(getLastDayOfMonth(1546066256000L));
        System.out.println();

        System.out.println(getFirstDayMsOfMonth(1546066256000L));
        System.out.println(getLastDayMsOfMonth(1546066256000L));
        System.out.println();

        System.out.println(ms2EarlyMs(System.currentTimeMillis()));
        System.out.println(ms2LateMs(System.currentTimeMillis()));
        System.out.println();

        LocalDate l1 = LocalDate.now();
        LocalDate l2 = l1.plusMonths(1L);
        System.out.println(betweenDay(l1, l2));
        System.out.println(betweenDay(l2, l1));
        System.out.println(betweenDay(l1, l1));
        System.out.println();

        System.out.println(getLengthOfMonth());
        System.out.println();

        System.out.println(getFirstDayMsOfMonth());
        System.out.println();

        System.out.println(getLastDayMsOfMonth());
        System.out.println();

        System.out.println(ld2ms(LocalDate.now(), false));
        System.out.println();

        System.out.println(getMondayMs(1543398920136L));
        System.out.println(getSundayMs(1543398920136L));
        System.out.println();

        System.out.println(ldt2ms(LocalDateTime.now()));
        System.out.println();

        System.out.println(ld2ms(LocalDate.now(), true));
        System.out.println(ld2ms(LocalDate.now(), false));
        System.out.println();

        System.out.println(ms2ld(System.currentTimeMillis()));
        System.out.println(ms2ld(System.currentTimeMillis() - ChronoUnit.DAYS.getDuration().toMillis()));
        System.out.println();
    }

}
