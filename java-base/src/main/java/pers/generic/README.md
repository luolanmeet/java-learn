### 泛型
声明中具有一个或者多个类型参数（type parameter）的类或者接口，
就是泛型（generic）类或者接口。
泛型类和接口统称为泛型（generic type）


#### 泛型类 generic class
具有一个或多个类型变量的类。
```java
public class Pair<T>   {
    public T get() {...}  // 泛型类里的方法
}
public class Pair<T, U> {...}
```

#### 泛型接口
```java
public interface 接口名<泛型类型> {
    void show(T t);
}
```
类似于泛型类

#### 泛型方法
```java
public <T> T getAll(T t, Class<T> clazz) {
    return t;
}
```

类型变量<T> 放在修饰符（ 这里是 public ) 的后面，返回类型 T 的前面。
调用这样  `obj.<String> getAll("all");` 
这里由于入参也是泛型 T ， 所以编译器可以推断出来类型参数，因此可以这样用  `obj.getAll("all");` 


### 类型擦除 erasure
无论什么时候定义一个泛型类型，都自动提供一个相应的原始类型。
即 擦除类型变量，替换为限定类型（无限定类型则用 Object）
类型擦除也会出现在泛型方法。

需要知道的事实
1. 虚拟机中没有泛型，只有普通的类和方法
2. 所有类型参数都用他们的限定类型替换（没有就是Object）
3. 桥方法被合成来保持多态
4. 为保持类型安全性，必要时插入强制类型转换

