### 装饰器模式/包装模式

> 不改变原有对象，为对象添加新功能。

> 挺像静态代理，但装饰器模式更关注于为对象定制功能。
> 代理模式更多是附加一些通用的逻辑。

> 装饰器模式挺好认的，装饰器实现和被装饰的类同样的接口。<br/>
> 并在构造方法中传入该接口的实现类。<br/>
> 也由于这样，装饰器之间允许嵌套。套娃。<br/>
> 然后在需要加功能的接口上，可以先调用被装饰的类的方法，<br/>
> 之后添加其他逻辑。

> 符合开闭原则，但也会多出很多代码

例子
* `Java`的`BufferedReader`、`InputStream`。
* `Spring`的`TransactionAwareCacheDecorator`，看名字就明白了。
* `Mybatis`中的`org.apache.ibatis.cache.decorators`包中，全是对`cache`的装饰。
