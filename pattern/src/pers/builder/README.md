### 建造者模式

> 简化一个对象的构造过程。<br>
对用户提供创建对象时需要的步骤，如设值，这些步骤的顺序可以随意改变。<br>
最后由建造者按照规则（或没有规则）创建需要的对象，<br>
用户无需关注这些规则，只需要按照需要调用提供的步骤。<br>
（构造sql语句，关键字的顺序是有规则的）

> 一般都是使用方式都链式的。比较优雅。<br>
> 建造者模式不符合开闭原则。而且会多出一个类。

> 建造者模式在很多地方都有使用到，比如<br>
`JPA`的`SQL`构造。<br>
`tk-mybatis`的`SQL`构造。<br>
`mybatis`的`CacheBuilder`、`SqlSessionFactoryBuilder`。<br>
`java`的`StringBuilder`。<br>
`spring`的`SpringApplicationBuilder`、`BeanDefinitionBuilder`。<br>
日常开发中使用的`lombok @Data`注解的实体类。