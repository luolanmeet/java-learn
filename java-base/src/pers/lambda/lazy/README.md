### 函子 Functor

#### 快速理解
类似 Java 中的 stream api 或者 Optional 中的 map 方法。 函子可以理解为一个接口，而 map 可以理解为接口中的方法。

#### 函子的计算对象
Java 中的 `Collection<T>`，`Optional<T>`，都有一个共同特点，就是他们都有且仅有一个泛型参数，好像一个万能的容器，可以任意类型打包进去。

#### 函子的定义
函子运算可以将一个 T 映射到 S 的 function 应用到`Optional<T>` 上，让其成为`Optional<S>`

#### 函子定律
1. 单位元律：在应用了恒等函数后，值不会改变
> Optional.of(a) == Optional.of(a).map(Function.identity())
2. 复合律：假设有两个函数 f1 和 f2，map(x -> f2(f1(x))) 和 map(f1).map(f2) 始终等价


### 单子 Monad
#### 快速理解
和 Java stream api 以及 Optional 中的 flatmap 功能类似

