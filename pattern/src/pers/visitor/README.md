### 访问者模式

> 将数据结构与数据操作分离的设计模式。指封装一些作用于某种数据结构中的各元素的操作，可以在不改变数据结构的前提下定义 这些元素的新的操作。
>
> 被称为最复杂的设计模式。

#### 思想

> 针对系统中拥有固定类型数的对象结构（元素），在其内提供一个accept()方法用来接受访问者对象的访问。不同的访问者对同一元素的访问内容不同，使得相同的元素集合可以产生不同的数据结果。
>
> accept()方法可以接收不同的访问者对象，然后在内部将自己（元素）转发到接收到的访问者对象的visit()方法内。访问者内部对应类型的visit()方法就会得到回调执行，对元素进行操作。
>
> 也就是两次动态分发，第一次是对访问者的分发accept()，第二次是对元素的分发visit()方法。经过两次分发才将一个具体元素传递到一个具体的访问者。
>
> 核心是解耦数据结构与数据操作。

#### 组成

* 抽象访问者（Visitor）:接口或抽象类，该类定义了对每个具体元素（Element）的访问行为visit()方法，其参数就是具体的元素对象。理论上visitor的方法个数与元素个数是相等的。如果元素个数经常变动，那么就不适合访问者模式了。
* 具体访问者（ConcreteVistor）：实现对具体元素的操作。
* 抽象元素（Element）：接口或抽象类，定义了一个接受访问者访问的方法accept()，表示所有元素类型都支持被访问者访问。
* 具体元素（ConcreteElement）：具体元素类型，提供接受访问者的具体实现。通常的实现为`vistor.visit(this)`。
* 结构对象（ObjectStruture）：该类内部维护了元素集合，并提供方法接收访问者对该集合所有元素进行操作。

#### 适用的场景

* 数据结构稳定，作用于数据结构的操作经常变化。
* 需要数据结构与数据操作分离的场景
* 需要对不同数据类型（元素）进行操作，而不使用分支判断具体类型的场景