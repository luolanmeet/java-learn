### 抽象工厂模式
> 每个工厂生产一系列的产品。
> 复杂产品的工厂

### 优点

* 符合单一职责原则。

### 缺点

* 新生一种类型的产品时，需要修改工厂接口，导致所有工厂的实现都要修改（java8默认方法可以改善这个情况），不符合开闭原则。


### 例子
* `spring`的`ApplicationContext` 内部需要创建不同的对象，那些有返回值的都可以看成是创建了对象）。<br>然后有不同的实现类。`ClassPathXmlApplicationContext`或`AnnotationConfigApplicationContext` |



