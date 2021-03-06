package pers.date;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.ParsePosition;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.LinkedHashMap;
import java.util.Optional;

public class DateUtil {

    private static final String PATTERN = "yyyy年MM月dd日 HH:mm:ss";

    /**
     * 日期对象按照格式转化为字符串
     * 注意！SimpleDateFormat并不是线程安全的
     * @param date
     */
    public static String formatDate(Date date) {

        // 自定义格式
//        SimpleDateFormat format = new SimpleDateFormat(PATTERN);
        // 默认时间格式： 22:13:32
//        DateFormat format = SimpleDateFormat.getTimeInstance();
        // 默认日期格式：2020-3-13
//        DateFormat format = SimpleDateFormat.getDateInstance();
        // 默认日期时间格式：2020-3-13 22:15:53
        DateFormat format = SimpleDateFormat.getDateTimeInstance();

        return format.format(date);
    }

    /**
     * 日期字符串转为日期对象
     * @param dateStr
     * @return
     */
    public static Optional<Date> dateStr2Date(String dateStr) {

        SimpleDateFormat dateFormat = new SimpleDateFormat(PATTERN);

        try {
            return Optional.of(dateFormat.parse(dateStr));
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return Optional.empty();
    }

    /**
     * 从指定位置解析
     * @param dateStr
     * @param index
     * @return
     */
    public static Optional<Date> dateStr2Date(String dateStr, Integer index) {
        SimpleDateFormat dateFormat = new SimpleDateFormat(PATTERN);
        // 直接使用parse方法，内部也是创建了一个ParsePosition对象
        return Optional.of(dateFormat.parse(dateStr, new ParsePosition(index)));
    }

    /**
     * 获取 10分钟整的日期
     * @return
     */
    public static Date getTenMinData() {

        // 每10分钟执行一次，获取 10 的整数倍分钟的时间
        Calendar ca = Calendar.getInstance();
        int minute = ca.get(Calendar.MINUTE);
        ca.set(Calendar.MINUTE, minute / 10 * 10);
        ca.set(Calendar.SECOND, 0);
        ca.set(Calendar.MILLISECOND, 0);
        return ca.getTime();
    }

    /**
     * Date 转 Calendar
     * @param date
     * @return
     */
    public static Calendar date2Cal(Date date) {
        Calendar cal = Calendar.getInstance();
        cal.setTime(date);
        return cal;
    }

    /**
     * Calendar 转 Date
     * @param cal
     * @return
     */
    public static Date cal2Date(Calendar cal) {
        return cal.getTime();
    }

    private static final long ONE_DAY_MILLI = 24 * 60 * 60 * 1000L;

    /**
     * 分割日期，将时间范围分割为 以天为单位的时间范围
     * @param leftDayStr yyyy-MM-dd HH:mm:ss
     * @param rightDayStr yyyy-MM-dd HH:mm:ss
     * @return
     */
    public static LinkedHashMap<String, String> spiltDate(String leftDayStr, String rightDayStr) throws ParseException {

        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");

        Date leftDate = dateFormat.parse(leftDayStr);
        Calendar leftCal = Calendar.getInstance();
        leftCal.setTime(leftDate);

        Date rightDate = dateFormat.parse(rightDayStr);
        Calendar rightCal = Calendar.getInstance();
        rightCal.setTime(rightDate);

        LinkedHashMap<String, String> result = new LinkedHashMap<>();
        if (rightCal.getTime().getTime() - leftCal.getTime().getTime() < ONE_DAY_MILLI) {
            result.put(dateFormat.format(leftCal.getTime()), dateFormat.format(rightCal.getTime()));
            return result;
        }

        String key = dateFormat.format(leftCal.getTime());
        leftCal.add(Calendar.DATE, 1); // 加1天
        leftCal.set(Calendar.HOUR_OF_DAY, 0);
        leftCal.set(Calendar.MINUTE, 0);
        leftCal.set(Calendar.SECOND, 0);
        leftCal.set(Calendar.MILLISECOND, 0);
        leftCal.add(Calendar.SECOND, -1); // 减1秒，变成昨天
        String value = dateFormat.format(leftCal.getTime());
        result.put(key, value);
        leftCal.add(Calendar.SECOND, 1); // 加1秒，变成明天

        while (rightCal.getTime().getTime() - leftCal.getTime().getTime() >= ONE_DAY_MILLI) {
            key = dateFormat.format(leftCal.getTime());
            leftCal.add(Calendar.DATE, 1);
            leftCal.add(Calendar.SECOND, -1);
            value = dateFormat.format(leftCal.getTime());
            result.put(key, value);
            leftCal.add(Calendar.SECOND, 1);
        }

        result.put(dateFormat.format(leftCal.getTime()), dateFormat.format(rightCal.getTime()));
        return result;
    }

    public static void main(String[] args) throws ParseException {

        System.out.println(DateUtil.formatDate(new Date()));

        Optional<Date> dateOp = DateUtil.dateStr2Date("2020年03月13日 21:37:51");
        dateOp.ifPresent(System.out::println);

        dateOp = DateUtil.dateStr2Date("1234 2020年03月13日 21:37:51", 4);
        dateOp.ifPresent(System.out::println);

        System.out.println(getTenMinData());

        spiltDate("2021-03-30 21:37:51", "2021-04-04 00:00:00")
                .forEach((k, v) ->  System.out.println(k + " 至 " + v));
    }

}
