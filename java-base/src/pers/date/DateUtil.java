package pers.date;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.ParsePosition;
import java.text.SimpleDateFormat;
import java.util.Date;
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

    public static void main(String[] args) {

        System.out.println(DateUtil.formatDate(new Date()));

        Optional<Date> dateOp = DateUtil.dateStr2Date("2020年03月13日 21:37:51");
        dateOp.ifPresent(System.out::println);

        dateOp = DateUtil.dateStr2Date("1234 2020年03月13日 21:37:51", 4);
        dateOp.ifPresent(System.out::println);
    }

}
