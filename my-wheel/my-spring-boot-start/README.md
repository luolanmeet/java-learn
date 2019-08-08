将此项目打包，在其他项目中依赖此项目，
然后可以注入`MyFormatTemplate`直接使用，达到开箱即用的效果。
可以通过在`application.properties`中填写`my.format.Xxx`进行配置。

注意，在 META-INF/spring.factories 中写入了以下内容
```
org.springframework.boot.autoconfigure.EnableAutoConfiguration=\
  pers.autoconfiguration.MyAutoConfiguration
```
