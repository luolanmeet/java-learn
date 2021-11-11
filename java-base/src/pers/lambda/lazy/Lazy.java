package pers.lambda.lazy;

import java.util.function.Function;
import java.util.function.Supplier;

/**
 * @auther ken.ck
 * @date 2021/11/11 07:50
 */
public class Lazy<T> implements Supplier<T> {

    private final Supplier<? extends T> supplier;

    /**
     * 缓存值 supplier 的值
     */
    private T value;

    /**
     * 构造一个惰性值
     * @param supplier
     * @param <T>
     * @return
     */
    public static <T> Lazy<T> of (Supplier<? extends T> supplier) {
        return new Lazy<>(supplier);
    }

    @Override
    public T get() {

        if (value != null) {
            return value;
        }

        value = supplier.get();
        if (value == null) {
            throw new IllegalStateException("Lazy value can not be null!");
        }

        return value;
    }

    /**
     * 用惰性值获取另一个惰性值
     * 函子
     * @param function
     * @param <S>
     * @return
     */
    public <S> Lazy<S> map(Function<? super T, ? extends S> function) {
        return Lazy.of(() -> function.apply(get()));
    }

    /**
     * 用惰性值获取另一个惰性值
     * 单子
     * @param function
     * @param <S>
     * @return
     */
    public <S> Lazy<S> flatMap(Function<? super T, Lazy<? extends S>> function) {
        return Lazy.of(() -> function.apply(get()).get());
    }

    private Lazy(Supplier<? extends T> supplier) {
        this.supplier = supplier;
    }

}
