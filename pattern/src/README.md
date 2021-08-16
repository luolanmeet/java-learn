### 设计模式
> 如何高质量地实现需求

| 类型   | 设计模式                                                     |
| ------ | ------------------------------------------------------------ |
| 创建型 | [工厂模式](https://github.com/luolanmeet/java-learn/tree/master/pattern/src/pers/factory) <br />[单例模式](https://github.com/luolanmeet/java-learn/tree/master/pattern/src/pers/singleton) <br />[建造者模式](https://github.com/luolanmeet/java-learn/tree/master/pattern/src/pers/builder) <br />[原型模式](https://github.com/luolanmeet/java-learn/tree/master/pattern/src/pers/prototype) |
| 结构型 | [桥接模式](https://github.com/luolanmeet/java-learn/tree/master/pattern/src/pers/bridge) <br />[适配器模式](https://github.com/luolanmeet/java-learn/tree/master/pattern/src/pers/adapter) <br />[装饰模式](https://github.com/luolanmeet/java-learn/tree/master/pattern/src/pers/decorator) <br />[外观模式](https://github.com/luolanmeet/java-learn/tree/master/pattern/src/pers/facade) <br />[代理模式](https://github.com/luolanmeet/java-learn/tree/master/pattern/src/pers/proxy) <br />[组合模式](https://github.com/luolanmeet/java-learn/tree/master/pattern/src/pers/composite) <br />[享元模式](https://github.com/luolanmeet/java-learn/tree/master/pattern/src/pers/flyweight) |
| 行为型 | [责任链模式](https://github.com/luolanmeet/java-learn/tree/master/pattern/src/pers/chainOfResponsibility) <br />[中介者模式](https://github.com/luolanmeet/java-learn/tree/master/pattern/src/pers/mediator) <br />[备忘录模式](https://github.com/luolanmeet/java-learn/tree/master/pattern/src/pers/memento) <br />[观察者模式](https://github.com/luolanmeet/java-learn/tree/master/pattern/src/pers/observer) <br />[状态模式](https://github.com/luolanmeet/java-learn/tree/master/pattern/src/pers/state) <br />[策略模式](https://github.com/luolanmeet/java-learn/tree/master/pattern/src/pers/strategy) <br />[模版方法模式](https://github.com/luolanmeet/java-learn/tree/master/pattern/src/pers/template) <br />[访问者模式](https://github.com/luolanmeet/java-learn/tree/master/pattern/src/pers/visitor) <br />[委派模式(不在GOF23)](https://github.com/luolanmeet/java-learn/tree/master/pattern/src/pers/delegate) <br />[解释器模式](https://github.com/luolanmeet/java-learn/tree/master/pattern/src/pers/interpreter) |


#### 面向对象设计原则
| 类型 | 说明| 坏味道 |
| --- | --- | --- |
| Liskov Substitution Principle 里氏替换原则 | 子类应该可以替换任何父类能够出现的地方，并且经过替换以后，代码还能正常工作。 | 强制类型转换。子类的方法超出了父类的类型定义范围，为了能使用到子类的方法，只能使用类型强制转换将类型转成子类类型。 |
| | | |

