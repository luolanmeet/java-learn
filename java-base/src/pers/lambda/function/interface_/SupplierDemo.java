package pers.lambda.function.interface_;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.function.Supplier;

/**
 * Supplier：工厂方法，不接收参数，只放回结果
 * @author cck
 * @date 2020/11/1
 */
public class SupplierDemo {

    private static final String PATTERN = "yyyy年MM月dd日 HH:mm:ss";

    public static void main(String[] args) {
        Supplier<DateFormat> dateFormatSupplier = () -> new SimpleDateFormat(PATTERN);
        System.out.println(dateFormatSupplier.get().format(new Date()));
    }

}
