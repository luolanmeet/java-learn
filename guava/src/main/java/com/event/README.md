### 源码阅读

```
EventBus eventBus = new EventBus();
eventBus.register(listener);
```

每个`EventBus`都持有一个`SubscriberRegistry`对象（订阅者）。

#### 订阅者 `SubscriberRegistry`

`SubscriberRegistry`中用一个`ConcurrentHashMap`存放具体的订阅者。

```
ConcurrentMap<Class<?>, CopyOnWriteArraySet<Subscriber>> subscribers =
    Maps.newConcurrentMap();

key是类类型对象，即事件类型。某个事件可以有多个订阅者，因此value是一个Subscriber集合。    
```

`Subscriber`

```
Subscriber封装了EventBus，订阅者对象，以及对应的方法。
Subscriber create(EventBus bus, Object listener, Method method)
```

```
处理事件时，只需要使用反射即可
void invokeSubscriberMethod(Object event) {
      method.invoke(target, checkNotNull(event));
}      

Subscriber是线程不安全的。 
SynchronizedSubscriber是Subscriber的子类，反射调用方法时会加上同步关键字。是线程安全的

synchronized (this) {
    super.invokeSubscriberMethod(event);
}

在创建Subscriber对象的时候会判断方法上是否有添加AllowConcurrentEvents注解，若添加了，
则表示该方法是线程安全的，直接创建Subscriber对象即可，否则创建线程安全的SynchronizedSubscriber。
```

#### 注册订阅者的过程

* 找出订阅者所有的添加了Subscribe注解的方法，封装为一个`Multimap`

  * 判断方法是不是只有一个参数，不是则报错

* 将上边获取到的`Map`添加到已经subscribers的`Map`中。

  ```
  Subscriber重写了hashCode和equal方法，同一个对象注册两次也只有一次的效果。
  ```

#### 推送事件的过程

* 交由`dispatcher`遍历`subscribers`。

* `dispatcher`有多种实现。`PerThreadQueuedDispatcher`是公平的事件分发器。

  事件会入队，然后按照顺序一次处理。

* 最后都是会调到`Subscriber#dispatchEvent`