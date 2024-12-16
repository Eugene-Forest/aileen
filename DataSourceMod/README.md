# DataSource

- [ ] 通过用户账号来获取数据源
- [ ] 通过用户账号来获取多/动态数据源

多数据源的实现其实 mybatis 有对应的实现，但是在此项目中，不通过它来实现。

```xml
    <dependency>
        <groupId>com.baomidou</groupId>
        <artifactId>dynamic-datasource-spring-boot-starter</artifactId>
        <version>${dynamic-datasource.version}</version>
    </dependency>
```

