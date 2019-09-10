### 启动sentinel控制台
1. 下载控制台jar包
2. 启动，server.port指定了端口，后边两个参数是将控制台接入到控制台上
```
java -Dserver.port=8888 -Dcsp.sentinel.dashboard.server=localhost:8888 -Dproject.name=sentinel-dashboard -jar sentinel-dashboard.jar
```

### 客户端接入
启动时指定应用名`-Dproject.name=sentinel-demo`

指定sentinel控制台`-Dcsp.sentinel.dashboard.server=localhost:8888`