# 生成客户端文件

## 方式一、请求服务端数据生成
wsimport -extension -keep -p com.pers.client http://localhost:10001/helloWorld?wsdl
> cmd 里直接执行（没成功）

## 方式二、利用服务端数据文件生成
1. 访问 http://localhost:10001/helloWorld?wsdl
2. 把数据保存到文件中 webservice.wsdl
3. 执行 wsimport -extension -keep -p com.pers.client helloWorld.wsdl

# SOAP 的不同版本区别
## content-type
* 1.1 版本为 text/xml; charset=utf-8
* 1.2 版本为 application/soap+xml; charset=utf-8
## xml 命名空间
* 1.1 版本为 http://schemas.xmlsoap.org/soap/envelope/
* 1.2 版本为 http://www.w3.org/2003/05/soap-envelope
> 注意 1.2 最后不带 /


# postman调用
> 1.1 版本，1.2 照着上述区别修改即可

* 请求url:  http://localhost:10001/helloWorld
* content-type: text/xml
``` body
<soapenv:Envelope xmlns:soapenv="http://schemas.xmlsoap.org/soap/envelope/"  <!--命名空间-->
                  xmlns:ser="http://service.cainiao.com/">  <!--服务名称-->
   <soapenv:Header/>
   <soapenv:Body>
      <ser:changeUser>  <!--请求方法-->
         <arg0>  <!--第一个参数-->
            <id>551</id>
            <name>Luffer</name>
         </arg0>
         <arg1>hi </arg1>  <!--第二个参数-->
      </ser:changeUser>
   </soapenv:Body>
</soapenv:Envelope>
```