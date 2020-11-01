### 备忘录模式

> 在不破坏封装的前提下，捕获一个对象的内部状态，并在对象之外保存这个状态。
>
> 这样之后就可将该对象恢复到原来保存的状态。

#### 组成

* 发起人角色（Originator）：负责创建一个备忘录，记录自身需要保存的状态；具备状态回滚功能。
* 备忘录角色（Memento）：用于存储`Originator`的内部状态，且可以防止`Originator`以外的对象进行访问。
* 备忘录管理员角色（Caretaker）：负责存储，提供管理备忘录`Memento`，无法对备忘录内容进行操作和访问。