# postman 请求
* 请求地址： http://localhost:8080/services/ws/api
* content-type: text/xml
* 请求数据
```body
<soapenv:Envelope xmlns:soapenv="http://schemas.xmlsoap.org/soap/envelope/" xmlns:ser="http://service.pers/">
   <soapenv:Header/>
   <soapenv:Body>
      <ser:queryUser>
         <arg0>hhhhh</arg0>
      </ser:queryUser>
   </soapenv:Body>
</soapenv:Envelope>
```