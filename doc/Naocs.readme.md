# Nacos 融合 Spring Cloud，成为注册配置中心

关于 Nacos Spring Cloud 的详细文档请参看：Nacos Config 和 Nacos Discovery。Nacos 的主要目的为以下：

* 通过 Nacos Server 和 spring-cloud-starter-alibaba-nacos-config 实现配置的动态变更。
* 通过 Nacos Server 和 spring-cloud-starter-alibaba-nacos-discovery 实现服务的注册与发现。

> 后续操作基于 nacos server 已经开启。


## 启动配置管理

```xml

<dependency>
    <groupId>com.alibaba.cloud</groupId>
    <artifactId>spring-cloud-starter-alibaba-nacos-config</artifactId>
    <version>${latest.version}</version>
</dependency>
```



## 启动服务发现


```xml

<dependency>
    <groupId>com.alibaba.cloud</groupId>
    <artifactId>spring-cloud-starter-alibaba-nacos-discovery</artifactId>
    <version>${latest.version}</version>
</dependency>
```

