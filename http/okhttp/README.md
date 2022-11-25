## 问题记录

现象：okhttp 发起请求后，返回以下报错。 检查后发现，被调的服务是正常的。
> java.io.IOException: unexpected end of stream on http://localhost:8080/...

问题：客户端 tcp 链接（okhttp）未失效，服务端的 tcp 链接失效，且链接失效时客户端没有重试。
复现步骤：
1. 启动客户端（客户端 okhttp 链接超时时间设置长一些）、启动服务端（随便一个web服务），
2. 触发客户端请求服务端，此时客户端和服务端建立了 tcp 链接。
3. 重启服务端，此时在服务端侧，与客户端的 tcp 链接必定断开。
4. 重新触发端请求服务端，可以复现。

处理：retryOnConnectionFailure 设置为 true
```dtd
return new OkHttpClient.Builder()
                .sslSocketFactory(sslSocketFactory(), x509TrustManager())
                //当链接失效时进行重试
                .retryOnConnectionFailure(true)
                //连接池
                .connectionPool(connectionPool)
                //链接超时时间
                .connectTimeout(connectTimeout, TimeUnit.SECONDS)
                //读超时时间
                .readTimeout(readTimeout, TimeUnit.SECONDS)
                //写超时时间
                .writeTimeout(writeTimeout, TimeUnit.SECONDS)
                .build();
```
