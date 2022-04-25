## 生成客户端文件

### 方式一、请求服务端数据生成
wsimport -extension -keep -p com.pers.client http://localhost:10001/helloWorld?wsdl
> cmd 里直接执行（没成功）

### 方式二、利用服务端数据文件生成
1. 访问 http://localhost:10001/helloWorld?wsdl
2. 把数据保存到文件中 webservice.wsdl
3. 执行 wsimport -extension -keep -p com.pers.client helloWorld.wsdl