### 用zookeeper做配置中心

###目前的效果
拿某些配置的操作

编程时
1. 先构建对应的类，在类中用注解声明配置项。

运行时
1. 初始操作，初始化配置中心对象。
2. 第二步是向配置中心对象传入这个类的类型和配置名称，然后保存返回的此类的实例。
>其中实例的对象属性就是需要的配置。当配置配置中心的配置发生时，
实例的属性会自动更新。

### 测试
1. 启动 zookeeper
2. 用zkCli连接zookeeper
3. 在zkCli执行语句（可以做个管理界面之类的）
    1. `create /zk-config-center acl` 创建基本节点
    2. `create /zk-config-center/db-user-config acl` 创建示例的配置
    配置名称为`/db-user-config`
    3. `create /zk-config-center/db-user-config/host_name 127.0.0.1` 创建示例配置项`host_name`
    4. `create /zk-config-center/db-user-config/port 3306` 创建示例配置项`post`
4. 启动测试目录下的`java.pers.Main.java`，可看到获取到的配置
5. 测试修改配置
    1. 在zkCli执行语句`set /zk-config-center/db-user-config/port 8080`
    2. 观察项目控制台输出
6. 测试删除配置
    1. 在zkCli执行语句`rmr /zk-config-center/db-user-config/port`
    2. 观察项目控制台输出
7. 测试新增配置
    1. 在zkCli执行语句`create /zk-config-center/db-user-config/port 3306`
    2. 观察项目控制台输出
