### 如果配置文件修改了，可以通过 endpoint 的 refresh 请求[post]去重新获取配置文件
### 但这是有问题的，不能依赖手动刷新

### 可以注入ContextRefresher，然后定时调refresh方法，见demo2

### 如果是用zk做配置中心，则是监听节点变化的事件，然后调用refresh方法

#### Tip 注意有一个 `@RefreshScope`注解。添加`@RefreshScope`的类会在配置更改时得到特殊处理。