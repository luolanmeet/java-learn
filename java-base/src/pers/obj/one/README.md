#### 计算对象大小

> 通过agent的方式

把 ObjSizeDemo 和 ObjSizeTool拷贝出去，把package信息去了
```java
// 编译
javac ObjSizeTool.java
javac ObjSizeDemo.java

// 打包
jar cvf ObjSizeDemo.jar *.class

// 修改包中 META-INF/MANIFEST.MF, 添加内容
Main-Class: ObjSizeDemo
Premain-Class: ObjSizeTool

// 运行
java -javaagent:ObjSizeDemo.jar -jar ObjSizeDemo.jar
```