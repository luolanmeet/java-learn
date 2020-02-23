### 服务端启动
* clone 源码，（用clone --depth=1 可能不行，会少一些commit，之后执行build.bat/sh时报错）

* 创建数据库。执行 scripts\sql 下的两个sql文件，注意自己的版本，需要在scripts\sql\delta目录下找额外的sql文件执行
* 修改\scripts\build.sh或build.bat文件，改数据库帐号密码这些配置。
* 运行\scripts\build文件
* 运行一下三个目录中的jar包，按照顺序分别跑 
    * apollo-configservice\target\
    * apollo-adminservice\target\
    * apollo-portal\target\
* 启动后访问 http://localhost:8070/ 看到配置的界面即可。

> 更多见[github](https://github.com/ctripcorp/apollo/wiki)，很详细 
