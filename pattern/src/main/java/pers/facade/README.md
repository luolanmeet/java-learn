### 门面模式/外观模式

> 提供一个整合了其他资源的类对外提供服务。

> 适合整合复杂的子系统，对外提供简单的接口，简单调用

例子
* 日常开发中都会无意间使用到的。
* `MVC`中的`Controller`
* `Mybatis`的`Configuration`，其中许多方法的实际逻辑都是其他类提供
* `Tomcat`的`RequestFacade`
* 挺多源码中都有以`Facade`结尾的类
