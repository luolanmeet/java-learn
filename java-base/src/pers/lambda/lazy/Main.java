package pers.lambda.lazy;

/**
 * @auther ken.ck
 * @date 2021/11/11 08:19
 */
public class Main {

    public static void main(String[] args) {

        // 构造一个惰性值
        Lazy<Integer> lazy = Lazy.of(() -> 1 + 1);
        System.out.println(lazy.get());

        // 用惰性值计算出另一个惰性值
        Lazy<String> map = lazy.map(param -> param.toString() + "a");
        System.out.println(map.get());

        // 需要用到两个惰性值计算的情况
        // 会有Lazy嵌套的情况
        Lazy<Lazy<String>> map1 = map.map(param1 ->
                lazy.map(param2 -> param1 + param2));
        System.out.println(map1.get().get());

        // 更好的写法
        // 最后一次取值用 map，其他都用 flatmap
        Lazy<String> flatMap = map.flatMap(parpam1 ->
                lazy.map(param2 -> parpam1 + param2));
        System.out.println(flatMap.get());
    }

}
