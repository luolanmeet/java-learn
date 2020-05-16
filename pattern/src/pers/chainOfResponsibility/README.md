### 责任链模式

> 使多个对象都有机会处理（或都处理）请求，从而避免了请求的发送者和接收者之间的耦合关系。
>
> 将这些对象连成一条线（对象内部持下个对象的引用），并沿着这条链传递该请求，直到有一个对象处理它为止。

#### 优缺点

优点

* 请求和处理分离
* 链路构造灵活，可以通过增加或删除结点新增或删除责任

缺点

* 责任链太长会影响性能
* 会出现死循环的可能

#### 例子

* `Java`的`Filter`，`doFilter`方法中持有`FilterChain`对象。
  * `spring` 对`FilterChain`的实现`MockFilterChain`中持有`Filter`的`list`集合。循环`list`调用`doFilter`，和 常见的写法不大一样。
* `Netty`的`ChannelHandler`，在`ChannelPipeline`中将`ChannelHandler`串成链。

