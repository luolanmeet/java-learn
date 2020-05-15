### 委派模式

> 基本作用就是负责任务的分配。

#### 组成

* 抽象任务角色（task）：定义一个抽象接口，有若干实现类

* 委派者角色（delegate）：负责在各个具体角色之间进行选择，并调用具体实现的方法

* 具体任务角色（concrete）：真正执行任务的角色

#### 例子

* `Java`类加载器，双亲委派模式
* `spring MVC`的`DispatcherServlet`，根据`url`选择不同的`controller`处理请求
* `spring IOC`的`BeanDefinitionParserDelegate`，根据不同的节点类型进行解析

