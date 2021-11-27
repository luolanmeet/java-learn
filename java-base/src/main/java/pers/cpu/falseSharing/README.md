> 操作系统一次读取64个字节为一个缓存行。（通常情况）<br />
> 使用了 volatile 关键字，x变量对于线程是可见的，x发生变化时会使缓存行失效。

>  T[] ts = new T[2];对象大小很小时，两个对象会在同一个缓存行中。<br />
> 其中某个对象的x发生变化都会使缓存行失效。互相影响。


1. 最简单粗暴的避免伪共享的解决方案 : 让每个线程操作的变量独自占有一个缓存行。
    * 填充（Padded）
此方法成立的前提是，类中同类型变量在内存中顺序，与类中同类型变量的定义顺序保持一致。由于JVM的设计规范里没有强制要求这一点，不同的JVM实现可能会有不同的表现。
    * 继承（Hierarchy）
利用在内存中，父类中定义的变量必然会在子类中定义的变量之前出现这一机制，通过继承使需要操作的变量独自占有一个缓存行。
具体原理可参考JOL中Sample_04与Sample_05。这种方法在一定程度上依赖了低版本HotSpot实现中的对象内存布局生成算法，此算法在JDK15中已被重新优化，导致此方法有一定概率会失效（详情可参考：https://bugs.openjdk.java.net/browse/JDK-8024913、https://bugs.openjdk.java.net/browse/JDK-8237767、https://bugs.openjdk.java.net/browse/JDK-8024912）。
    * @Contended
JDK8中新增了@Contended注解来解决伪共享问题，内部实现机制与第一种填充方法一致，需要通过JVM参数配置启用：-XX:-RestrictContended。
一些JDK内部使用此注解的类： Stripped64 Thread ForkJoinPool ConcurrentHashMap Exchanger。 https://www.javaspecialists.eu/archive/Issue249.html
2. Array Trick
  
