类需要实现 Cloneable 接口才能使用clone方法，否则会抛出 CloneNotSupportedException 异常。

Cloneable 的作用只是表明一个类的对象是允许克隆的。可以使用clone方法，最终调用到Object提供的clone方法。

所有实现了Cloneable 接口的类都应该覆盖clone方法，并且是共有的方法，
返回类型为类本身。该方法应该先调用super.clone方法，然后修正任何需要修正的域。

需要注意的是，clone方法并不是线程安全的。