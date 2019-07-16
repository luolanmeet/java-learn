### 前言
接微信支付的时候，看到微信支付的回调是按照某种频率去回调的，
像`15s/15s/30s/3m/10m/20m/30m/30m/30m/60m/3h/3h/3h/6h/6h`这样，其中有一次成功就不会再回调。
于是在想怎么用`Java`做这个事情。

### 运行
运行`Main`类中的测试方法即可

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
