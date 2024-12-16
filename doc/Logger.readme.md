#  日志

Spring Boot包含许多Logback扩展，可以帮助进行高级配置。可以在logback-spring.xml配置文件中使用这些扩展。如果需要比较复杂的配置，建议使用扩展配置的方式

如果需要比较复杂的配置，建议使用扩展配置的方式，SpringBoot推荐我们使用带-spring后缀的 logback-spring.xml 扩展配置，因为默认的的logback.xml标准配置，Spring无法完全控制日志初始化。（spring扩展对springProfile节点的支持）


以下是项目常见的完整logback-spring.xml，SpringBoot默认扫描classpath下面的logback.xml、logback-spring.xml，所以不需要再指定spring.logging.config，当然，你指定也没有问题

```yaml
logging:
  config: classpath:logback-spring.xml
```

如果你即想完全掌控日志配置，但又不想用logback.xml作为Logback配置的名字，application.yml可以通过logging.config属性指定自定义的名字。格式如上所示。

## 日志 Level

日志级别从低到高分为

```text
TRACE < DEBUG < INFO < WARN < ERROR < FATAL
```


## 根据运行环境进行变化的日志

如果使用springProfile, 就需要在application配置文件中通过 spring.profiles.active=dev 来指定环境,
也可以直接去掉 <springProfile> 这个标签或者把它整个注释掉

//TODO: 

## 根据用户环境进行的日志

//TODO:


## 基本配置

1.日志输出到文件并根据LEVEL级别将日志分类保存到不同文件

2.通过异步输出日志减少磁盘IO提高性能

详情参考本项目的配置。
