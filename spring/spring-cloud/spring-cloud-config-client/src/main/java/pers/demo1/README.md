### 如果配置文件修改了，可以通过 endpoint 的 refresh 请求[post]去重新获取配置文件
### 一般配置文件改变了重启服务会好点

### 不想依赖手动刷新
### 可以注入ContextRefresher，然后定时调refresh方法，见demo2

### 如果是用zk做配置中心，则是监听节点变化的事件，然后调用refresh方法

#### Tip 注意有一个 `@RefreshScope`注解。添加`@RefreshScope`的类会在配置更改时得到特殊处理。
#### 适合那些用来做 开关、阈值的bean。 用于DB配置的那些还不要的好，不确定跟其他bean有什么关联。