package pers.lambda.function.interface_;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.function.Function;

/**
 * 比较常见的一个函数接口，接收 T 类型参数，返回 R 类型结果
 * @author cck
 * @date 2020/11/1
 */
public class FunctionDemo {

    private static final String PATTERN = "yyyy年MM月dd日 HH:mm:ss";

    public static void main(String[] args) {

        Result<Date> result = new Result<>();
        result.setData(new Date());
        Function<Date, String> function = t -> new SimpleDateFormat(PATTERN).format(t);

        System.out.println(result);
        System.out.println(convertResult(result, function));
    }

    /**
     *  转换结果类型
     */
    public static <T, R> Result<R> convertResult(Result<T> result, Function<T, R> function) {
        Result<R> resultConvert = new Result<>();
        R r = function.apply(result.getData());
        resultConvert.setData(r);
        resultConvert.setSuccess(result.isSuccess());
        return resultConvert;
    }

    static class Result<T> {
        boolean success = true;
        T data;

        @Override
        public String toString() {
            return "Result{" +
                    "success=" + success +
                    ", data=" + data +
                    '}';
        }

        public boolean isSuccess() {
            return success;
        }
        public T getData() {
            return data;
        }
        public void setSuccess(boolean success) {
            this.success = success;
        }
        public void setData(T data) {
            this.data = data;
        }
    }

}
