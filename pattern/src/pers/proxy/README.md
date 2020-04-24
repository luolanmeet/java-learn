### 代理模式

* 静态代理
* 动态代理（框架用得多）
    * `Java`动态代理依赖接口
    * `CGLIB`不依赖接口，生成的类会继承被代理类


例子
* `Mybatis`的`MapperProxy`就是使用了`Java`的动态代理
* `spring aop`