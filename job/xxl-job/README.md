> [官网]很详细了 

下载`xxl-job`项目，为`xxl-job`创建表，运行`xxl-job-admin`项目

注意，xxl-job的日志是存储在对应的IJobHandler实现类运行的机器上。
如果将日志文件删除，在控制台上将只能看到 [Load Log Finish]。
此外xxl-job打印异常应该这么写
> XxlJobLogger.log(e);
>
> 而不是 XxlJobLogger.log("", e); 
>
> 并且没法直接把参数和异常堆栈一起打印，即以下写法均无法打印堆栈信息:
>
> XxlJobLogger.log("param {},  error {}", 123, e);
>
> XxlJobLogger.log("param {},  error ", 123, e);
>
> 可以直接把参数和堆栈信息拼在一块打印或则分开打印


[官网]:https://github.com/xuxueli/xxl-job