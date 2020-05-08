### 适配器模式

> 将一个类的接口变成客户端所期望的另一种接口，从而使原本因接口不匹配而导致无法在一起工作的两个类能够一起工作。

* 目标角色（target）：期望的接口
* 源角色（adaptee）：存在于系统中，内容满足客户需求，但接口不匹配的接口实例。
* 适配器（adapter）：讲源角色（adaptee）转化为目标角色（target）的类实例。

### 源码例子

* `spring aop`的`AdvisorAdapter`
* `spring mvc`的`HandlerAdapter`

