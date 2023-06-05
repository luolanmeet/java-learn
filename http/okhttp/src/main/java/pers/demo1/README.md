### 客户端发送请求，服务端接收到请求后处理请求，服务端处理请求时间 大于 客户端 readTimeout 
【Read timed out】
```
Exception in thread "main" java.net.SocketTimeoutException: timeout
	at okio.Okio$4.newTimeoutException(Okio.java:232)
	at okio.AsyncTimeout.exit(AsyncTimeout.java:285)
	at okio.AsyncTimeout$2.read(AsyncTimeout.java:241)
	at okio.RealBufferedSource.indexOf(RealBufferedSource.java:355)
	at okio.RealBufferedSource.readUtf8LineStrict(RealBufferedSource.java:227)
	at okhttp3.internal.http1.Http1Codec.readHeaderLine(Http1Codec.java:215)
	at okhttp3.internal.http1.Http1Codec.readResponseHeaders(Http1Codec.java:189)
	at okhttp3.internal.http.CallServerInterceptor.intercept(CallServerInterceptor.java:88)
	at okhttp3.internal.http.RealInterceptorChain.proceed(RealInterceptorChain.java:147)
	at okhttp3.internal.connection.ConnectInterceptor.intercept(ConnectInterceptor.java:45)
	at okhttp3.internal.http.RealInterceptorChain.proceed(RealInterceptorChain.java:147)
	at okhttp3.internal.http.RealInterceptorChain.proceed(RealInterceptorChain.java:121)
	at okhttp3.internal.cache.CacheInterceptor.intercept(CacheInterceptor.java:93)
	at okhttp3.internal.http.RealInterceptorChain.proceed(RealInterceptorChain.java:147)
	at okhttp3.internal.http.RealInterceptorChain.proceed(RealInterceptorChain.java:121)
	at okhttp3.internal.http.BridgeInterceptor.intercept(BridgeInterceptor.java:93)
	at okhttp3.internal.http.RealInterceptorChain.proceed(RealInterceptorChain.java:147)
	at okhttp3.internal.http.RetryAndFollowUpInterceptor.intercept(RetryAndFollowUpInterceptor.java:126)
	at okhttp3.internal.http.RealInterceptorChain.proceed(RealInterceptorChain.java:147)
	at okhttp3.internal.http.RealInterceptorChain.proceed(RealInterceptorChain.java:121)
	at okhttp3.RealCall.getResponseWithInterceptorChain(RealCall.java:200)
	at okhttp3.RealCall.execute(RealCall.java:77)
	at pers.demo1.Test.main(Test.java:27)
Caused by: java.net.SocketTimeoutException: Read timed out
	at java.net.SocketInputStream.socketRead0(Native Method)
	at java.net.SocketInputStream.socketRead(SocketInputStream.java:116)
	at java.net.SocketInputStream.read(SocketInputStream.java:171)
	at java.net.SocketInputStream.read(SocketInputStream.java:141)
	at okio.Okio$2.read(Okio.java:140)
	at okio.AsyncTimeout$2.read(AsyncTimeout.java:237)
	... 20 more
```

### 客户端发送请求，服务端接收到请求后，服务端关闭时报的错
【Connection refused (Connection refused)】
```
Exception in thread "main" java.net.ConnectException: Failed to connect to localhost/127.0.0.1:8080
    at okhttp3.internal.connection.RealConnection.connectSocket(RealConnection.java:242)
    at okhttp3.internal.connection.RealConnection.connect(RealConnection.java:160)
    at okhttp3.internal.connection.StreamAllocation.findConnection(StreamAllocation.java:257)
    at okhttp3.internal.connection.StreamAllocation.findHealthyConnection(StreamAllocation.java:135)
    at okhttp3.internal.connection.StreamAllocation.newStream(StreamAllocation.java:114)
    at okhttp3.internal.connection.ConnectInterceptor.intercept(ConnectInterceptor.java:42)
    at okhttp3.internal.http.RealInterceptorChain.proceed(RealInterceptorChain.java:147)
    at okhttp3.internal.http.RealInterceptorChain.proceed(RealInterceptorChain.java:121)
    at okhttp3.internal.cache.CacheInterceptor.intercept(CacheInterceptor.java:93)
    at okhttp3.internal.http.RealInterceptorChain.proceed(RealInterceptorChain.java:147)
    at okhttp3.internal.http.RealInterceptorChain.proceed(RealInterceptorChain.java:121)
    at okhttp3.internal.http.BridgeInterceptor.intercept(BridgeInterceptor.java:93)
    at okhttp3.internal.http.RealInterceptorChain.proceed(RealInterceptorChain.java:147)
    at okhttp3.internal.http.RetryAndFollowUpInterceptor.intercept(RetryAndFollowUpInterceptor.java:126)
    at okhttp3.internal.http.RealInterceptorChain.proceed(RealInterceptorChain.java:147)
    at okhttp3.internal.http.RealInterceptorChain.proceed(RealInterceptorChain.java:121)
    at okhttp3.RealCall.getResponseWithInterceptorChain(RealCall.java:200)
    at okhttp3.RealCall.execute(RealCall.java:77)
    at pers.demo1.Test.main(Test.java:27)
Caused by: java.net.ConnectException: Connection refused (Connection refused)
    at java.net.PlainSocketImpl.socketConnect(Native Method)
    at java.net.AbstractPlainSocketImpl.doConnect(AbstractPlainSocketImpl.java:350)
    at java.net.AbstractPlainSocketImpl.connectToAddress(AbstractPlainSocketImpl.java:206)
    at java.net.AbstractPlainSocketImpl.connect(AbstractPlainSocketImpl.java:188)
    at java.net.SocksSocketImpl.connect(SocksSocketImpl.java:392)
    at java.net.Socket.connect(Socket.java:606)
    at okhttp3.internal.platform.Platform.connectSocket(Platform.java:129)
    at okhttp3.internal.connection.RealConnection.connectSocket(RealConnection.java:240)
    ... 18 more
```
