> 操作系统一次读取64个字节为一个缓存行。（通常情况）<br />
> 使用了 volatile 关键字，x变量对于线程是可见的，x发生变化时会使缓存行失效。

>  T[] ts = new T[2];对象大小很小时，两个对象会在同一个缓存行中。<br />
> 其中某个对象的x发生变化都会使缓存行失效。互相影响。

> 可以通过填充对象大小来避免（Disruptor的实现方式）
> 可以通过使用 @Contended 避免


https://www.javaspecialists.eu/archive/Issue249.html