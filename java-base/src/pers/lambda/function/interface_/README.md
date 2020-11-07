### 函数接口

`java.util.Function`

常用的几个

| 接口              | 函数签名            | 范例                |
| ----------------- | ------------------- | ------------------- |
| UnaryOperator<T>  | T apply(T t)        | String::toLowerCase |
| BinaryOperator<T> | T apply(T t1, T t2) | BigInteger::add     |
| Predicate<T>      | boolean test(T t)   | Collection::isEmpty |
| Function<T, R>    | R apply(T t)        | Arrays::asList      |
| Supplier<T>       | T get()             | Instant::now        |
| Consumer<T>       | void accept(T t)    | System.out::println |

> 其实去看Stream类就能学到很多了

