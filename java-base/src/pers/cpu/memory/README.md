### 验证缺页中断

程序的编写应该遵循 局部性原理，这样才能辅助虚存技术降低缺页率

#### 局部性原理
* 时间局部性
> 一条指令的一次执行和下次执行，一个数据的一次访问和下次访问
都集中在一个较短时期内。

* 空间局部性
> 当前指令和邻近的几条指令，当前访问的数据和邻近的几个数据
都集中在一个较小区域内。


https://lwn.net/Articles/255364/

https://mechanical-sympathy.blogspot.com/2012/08/memory-access-patterns-are-important.html