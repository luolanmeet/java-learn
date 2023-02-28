基本概念：确保一个类在任何情况下都只有一个实例，并且提供全局访问点。

单例模式套路
1. 私有化构造方法
2. 提供全局访问方法
3. 注意反射破坏单例（重写构造方法，在构造方法中判断实例是否创建）
4. 注意序列化破坏单例（重写readResolve方法，返回实例）

| 例子                                                         |
| ------------------------------------------------------------ |
| `spring`的`AbstractBeanFactory#getBean`                      |
| `guava`的`MoreExecutors中`创建`DirectExecutor`使用的就是枚举式的单例 |
