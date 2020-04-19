### 工厂模式

### 简单概念
* 简单工厂模式：产品的工厂
* 工厂方法模式：工厂的工厂
* 抽象工厂模式：系列产品的工厂

### 案例

| 例子                              | 类型                                                         |
| --------------------------------- | ------------------------------------------------------------ |
| `LoggerFactory.getLogger("cck");` | 简单工厂模式，根据参数返回不同的实例。<br>比较明显的简单工厂模式 |
| `Calendar.getInstance();`         | 简单工厂模式<br>方法内会根据当前的环境返回不同对象。         |
| `spring`的`ApplicationContext`    | 抽象工厂模式，内部需要创建不同的对象，<br>那些有返回值的都可以看成是创建了对象）。<br>然后有不同的实现类。`ClassPathXmlApplicationContext`或`AnnotationConfigApplicationContext` |
| 数据库连接池                      | 工厂模式                                                     |





