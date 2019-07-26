### 运行
运行`JobSchedulingTest`测试类即可

#### 各个类的作用
**`JobActuator`**
任务执行器，本身继承了`Thread`，职责是在`run`方法中不断从延迟任务队列`DelayQueue`中获取任务，
再交由线程池`ExecutorService`执行。延迟效果的都是依靠`DelayQueue`实现。

**`DelayTimeJob`**
实现了`Delayed`接口，执行实际的业务并决定任务是否重新进入延迟队列。

**`BaseJob`**
用户继承此抽象类，在`run`方法中编写业务代码，通过控制`isExit`变量控制任务是否执行。

**`IDelayTimeGenerator`**
延迟时间生成器接口，返回一个延迟时间。可以实现不同的策略，达到不同的延迟效果。
如`DesignatDTGenerator`是定义每一次执行的时间间隔，`FixedRateDTGenerator`是按照某一个固定频率执行。
