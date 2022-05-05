使用Java agent探针技术，我们可以实现很多的功能：
* 在程序运行时动态的新增日志 
* 热部署替换类方法的实现
* 支持方法执行耗时范围抓取设置，根据耗时范围抓取系统运行时出现在设置耗时范围的代码运行轨迹。
* 支持抓取特定的代码配置，方便对配置的特定方法进行抓取，过滤出关系的代码执行耗时情况。
* 支持APP层入口方法过滤，配置入口运行前的方法进行监控，相当于监控特有的方法耗时，进行方法专题分析。
* 支持入口方法参数输出功能，方便跟踪耗时高的时候对应的入参数。
* 提供WEB页面展示接口耗时展示、代码调用关系图展示、方法耗时百分比展示、可疑方法凸显功能。
---
获取 Instrumentation 对象
* 在JVM启动时指定agent，Instrumentation对象会通过agent的premain方法传递过去。
* 在JVM启动后通过JVM提供的机制加载agent，Instrumentation对象会通过agent的agentmain方法传递过去。
---
Javaagent是java命令的一个参数。参数 javaagent 可以用于指定一个 jar 包，并且对该 java 包有2个要求：
* 这个 jar 包的 MANIFEST.MF 文件必须指定 Premain-Class 项。
> 可以用 maven 插件帮助生成 MANIFEST.MF 文件
* Premain-Class 指定的那个类必须实现 premain() 方法。
> // 优先加载<br>
> public static void premain(String agentArgs, Instrumentation inst)<br>
> public static void premain(String agentArgs)
---
在 Java SE 6 的 Instrumentation 当中，提供了一个新的代理操作方法：agentmain，可以在 main 函数开始运行之后再运行。
