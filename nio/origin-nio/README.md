> Linux 中

### BIO
最大的问题是阻塞。等待连接会阻塞（accept）、等待读取数据也是阻塞（recv）。<br />

read方法最终是会调到内核程序（linux）的api，使用多线程处理多个连接，<br />
每个线程都会调recv，也就是会多次用户态和内核态的切换。<br />

```java
socket -> 3 // 创建socket，返回了fb 3
bind(3, 9090) // fb 3 绑定在9090端口
listen(3) // 监听连接

while (true) {

	accept(3) -> 7 // 阻塞，等待连接，连接建立，返回fb 7
	
	new Thread(7) { // 创建一个线程处理连接7
		recv(7) // 阻塞，等待连接7发送数据
	}
}
```

### NIO（non-blocking io） 

虽然不用每个连接都占用一个线程，但依然会有多次用户态和内核态的切换

````java
socket -> 3 // 创建socket，返回了fb 3
bind(3, 9090) // fb 3 绑定在9090端口
listen(3) // 监听连接
3.nonblock // 设置为非阻塞
    
List list; // 存储已建立的连接
    
new Thread() {
    while (true) {
    	accept(3) -> 7 // 这里不再阻塞了，没有连接则返回null之类的，这里假设建立了连接，返回fb 7
	    7.nonblock // 设置为非阻塞
	    list.add(7) // 添加
	}
}

new Thread() {
    for (i : list) { // 循环调用recv
        recv(7) // 这里不会阻塞了
    }
}
````

### 多路复用模型

select（有fb个数限制）、poll（无fb个数限制）<br />
最大的特点就是非阻塞（accept）。读取数据时可以把所有连接fb传给内核程序（select），<br />
然后内核程序返回哪些fb是ready的。<br />
只有一次用户态和内核态的切换。<br />
但这样又有一个缺点，每次都需要把fb传给内核。

```java
socket -> 3 // 创建socket，返回了fb 3
bind(3, 9090) // fb 3 绑定在9090端口
listen(3) // 监听连接
3.nonblock // 设置为非阻塞
    
List list; // 存储fb
    
new Thread() {
    while (true) {
    	accept(3) -> 7 // 这里不再阻塞了，没有连接则返回null之类的，这里假设建立了连接，返回fb 7
	    7.nonblock // 设置为非阻塞
	    list.add(7) // 添加
	}
}

new Thread() {
   select(list) -> 7,8 // 这里一次性将所有fb传给内核程序
}
```

epoll。<br />
内核开辟一块空间（epoll_create，返回一个fb 6），<br />
这块内核空间用于注册fb，注册fb时指定监听的事件（epoll_ctl(6, add, 8, read)）。<br />
注册到这块空间上的fb是ready时会返回对应的fb（epoll_wait（6）-> 8）<br />

```java
socket -> 3 // 创建socket，返回了fb 3
bind(3, 9090) // fb 3 绑定在9090端口
listen(3) // 监听连接

epoll_create -> 6 // 在内核开辟一块空间，返回 fb 6    
epoll_ctl(6, add, 3, accept) // 把fb3注册到6上，事件为accept事件（连接建立事件） 

while (true) {
    epoll_wait -> 3 // 等待事件来临
    3.accept -> 9 // 建立连接，返回 fb 9    
    epoll_ctl(6, add, 9, read) // 把fb9注册到6上，事件为read事件（读事件）,下次epoll_wait可能就会返回fb 9了
}
```

