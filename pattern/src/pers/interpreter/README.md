### 解释器模式

> 指定一门语言，定义它的文法的一种表示，并定义一个解释器，该解释器使用该表示来解释语言中的句子。

#### 组成

* 抽象表达式（Expression）：负责定义一个解释方法interpret，交由具体子类进行具体解释
* 终结符表达式（TerminalExpression）：实现文法中与终结符有关的解释操作。R = R1 + R2，R1 与 R2 为终结符
* 非终结符表达式（NonterminalExpression）：实现文法中与非终结符有关的解释操作。R = R1 + R2，+ 为非终结符
* 上下文环境类（Context）：包含解释器之外的全局信息。它的任务一般是由存放文法中各个终结符所对应的具体值。

#### 例子

* `Java`的`Pattern`
* `Spring`的`ExpressionParser`