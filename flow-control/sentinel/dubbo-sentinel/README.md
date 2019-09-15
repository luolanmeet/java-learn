nacos 增加配置
```
[
    {
        "resource":"pers.IHelloService#sayHello(java.lang.String)",
        "grade" : 1,
        "count" : 10,
        "clusterMode" : true,
        "clusterConfig" : {
            "flowId" : 111,
            "thresholdType" : 1,
            "fallbackToLocalWhenFail" : true
        }
    }
]
```