### Java 队列

#### 常用方法

|      | 可能报异常 | 方法返回布尔值 | 可能阻塞 | 设定等待时间            |
| ---- | ---------- | -------------- | -------- | ----------------------- |
| 入队 | add(e)     | offer(e)       | put(e)   | offer(e, timeout, unit) |
| 出队 | remove()   | poll()         | take()   | poll(timeout, unit)     |
| 查看 | element()  | peek()         | 无       | 无                      |

