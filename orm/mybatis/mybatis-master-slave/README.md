### 背景

#### 需求背景

> 目前线上数据库部署为一主一从。在程序中希望读取数据读从库，写数据用主库。

#### 技术背景

> 项目使用`spring boot`、`mybatis`、`tk-mybatis`。

### 想法

#### 想法一

做法

* 直接写两套代码，即另外写一份接口和`xml`。

结论

> 可以实现，但缺点明显。
>
> 优点
>
> * 简单易实现
> * 测试充分的情况下几乎不会有风险
>
> 缺点
>
> * 维护麻烦。出现新增或修改时，需要同步两份代码，保证其一致。

#### 想法二

做法

* 使用`AbstractRoutingDataSource`类，在每次调用`Mapper`的方法前会<br>自动获取存放在`ThreadLocal`中标识的数据源，动态设置数据源。
* 通过注解结合`spring aop`，做到使用了注解的方法自动使用从库数据源。<br>方法结束后自动还原为默认数据源。<br>对于需要灵活指定的也可直接修改`ThreadLoadl`中的配置达到目的。

结论

> 可以实现，但有缺点。
>
> 优点
>
> * 解决了需要特意为从库数据源写多一份代码的问题
> * 对于整个方法都是使用从数据源的情况，可以做到代码无侵入
>
> 缺点
>
> * 存在风险。使用该方式，是为整个线程设置了数据源，稍有不慎未将数据源还原，<br>会使程序出现错误。如出现写数据到从数据库的情况。
> * 不好维护。当一个方法中既有读也有写时，需要手动设置数据源，对于侵入的这部分代码，<br>需要知道是出于什么目的，为`Mapper`设置数据源时需要知道在哪个数据库。

#### 想法三

做法

* 为`Mapper`再做一层代理，也就是代理`MapperProxy`，通过解析`SQL`来配置数据源。

结论

> 无法实现。
>
> * 由于使用了`spring`的原因，所有的`Mapper`都是单例的（准确的说是`MapperProxy`对象是单例的），<br>修改一个单例对象的属性会对所有使用该对象的线程产生影响。从根本上就不可行。

#### 想法四

做法

* 在`spring ioc`容器中创建两个`Mapper`对象，除了数据源不同，其他都相同。
* 在使用时，根据需要注入对应的`Mapper`即可。效果与写两份代码是一样的。

结论

> 自测可行。(未深入验证)

技术细节

>
> 在`spring`中`Mapper`之所以可以是单例，是因为`spring`替换了`Mapper`原本持有的`SqlSession`，转为`SqlSessionTemplate`。
> `SqlSessionTemplate`每次都会拿新的`sqlSession`。
>
> 想法：动态创建新的`Mapper`对象，使用配置了从数据库源的`SqlSessionTemplate`对象。
>
> ```
> @MapperScan(sqlSessionTemplateRef = "imageSqlSessionTemplate")
> ```
>
> 在使用`@MapperScan`注解指定了`sqlSessionTemplateRef`后，`Mapper`接口的`BeanDefinition`对象的`propertyValues`会多一个
>
> <`sqlSessionTemplate`，`RuntimeBeanReference`>的值，用于指定具体使用的`sqlSessionTemplate`。
>
> 我们只需要在实现了`BeanDefinitionRegistryPostProcessor`的类中，为需要使用从数据源的`Mapper`再注册一个`BeanDefinition`，
> 并设置上配置了从数据源的`sqlSessionTemplate`即可。

使用

> 和往常一样配置好从数据源。
>
> 在配置文件中为`Mapper`所在的包配置指定的`sqlSessionTemplate`。
>
> 在`BeanDefinitionRegistryPostProcessor`中扫描这些包，获取到`Mapper`默认的名字。
>
> 通过名字获取已经创建的`BeanDefinition`，复制一份，修改`propertyValues`中的`key`为`sqlSessionTemplate`的值为指定的`sqlSessionTemplate`。

tip
> `Mapper`的`BeanDefinition`其实是`MapperFactoryBean`，`MapperFactoryBean`继承了
>
> `SqlSessionDaoSupport`，这个类比较重要。

