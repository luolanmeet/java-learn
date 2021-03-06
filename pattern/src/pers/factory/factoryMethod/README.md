### 工厂方法模式
> 定义一个用于创建对象的接口（工厂的接口），让子类（工厂接口的实现）决定将哪一个类实例化。
>
> 简单工厂模式：产品的工厂
>
> 工厂方法模式：工厂的工厂

### 优点

* 封装了创建实例的细节（工厂都有的特点）
* 符合开闭原则，新增类型时新增对应的工厂即可。
* 符合单一职责原则，每个工厂都只负责创建一个类型的实例

### 缺点

* 一个类型就需要一个工厂，类的个数成对增加
* 不容易创建复杂的实例

### 适用

* 一个类希望由它的子类来创建具体的实例

