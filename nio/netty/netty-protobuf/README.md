> 一个netty结合序列化框架protobuf的聊天demo

#### 启动
1. 运行`ChatServer`类
2. 将`resources`中的`ROOT`文件放到web容器中，然后访问`localhost/login.html`
> 内置了两个用户 [admin, admin] [guest, guest]  

#### 注意点
> 主要要解决的问题是，解码时如何得知`ByteBuf`应序列化为哪种类型的对象。<br/>
需要了解protobuf的编码规则，具体做法见`CustomProtobufDecoder`类
