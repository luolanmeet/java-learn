> 缓冲区对象本质上是一个数组，只不过内置了一些机制。<br/>
能够跟踪和记录缓冲区的状态变化情况，使用get/put方法从缓冲区获取/写入数据，<br/>
都会引起缓冲区状态的变化。

### 缓冲区的三个属性
**position**
> 指定下一个将要被写入或读取的元素索引，初始化为0

**limit**
> 指定还有多少数据需要取出或还有多少空间可以写入

**capacity**
> 指定了可以存储在缓冲区的最大数据容量

0 <= position <= limit <= capacity

### 缓冲区的创建
```
// 分配指定大小的缓冲区
ByteBuffer buffer1 = ByteBuffer.allocate(10);

// 包装一个现有的数组
byte array[] = new byte[10];
ByteBuffer buffer2 = ByteBuffer.wrap(array);

// 创建只读缓冲区，只能读取数据，与原缓冲区共享数据
ByteBuffer readOnlyBuffer = buffer.asReadOnlyBuffer();

// 缓冲区分片，与原缓冲区共享数据
ByteBuffer sliceBuffer = buffer.slice();