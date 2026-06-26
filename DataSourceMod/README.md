# DataSourceMod - 动态多数据源模块

## 模块概述

DataSourceMod 是一个基于 Spring Boot + MyBatis 的**自定义动态多数据源管理模块**，不依赖第三方 `dynamic-datasource` 组件，完全手动实现多数据源的注册、路由与管理。

核心能力：
- 支持**多账套**（AccountSet）隔离，每个账套拥有独立的数据源集合
- 基于 `AbstractRoutingDataSource` 实现**运行时数据源动态切换**
- 支持从 **本地 JSON 文件** 或 **Nacos 配置中心** 加载数据源配置
- 自动为每个逻辑数据源注册完整的 MyBatis 基础设施（SqlSessionFactory、事务管理器、Mapper 扫描器）
- 数据源敏感信息（密码、服务器地址）支持 **RSA 加密存储**，运行时自动解密

---

## 核心架构

```
┌─────────────────────────────────────────────────────────┐
│                    DataSourceApp                         │
│            (Spring Boot 启动入口)                         │
└──────────────────────┬──────────────────────────────────┘
                       │
         ┌─────────────▼──────────────┐
         │    DataSourceStartRunner    │
         │ (BeanDefinitionRegistry     │
         │  PostProcessor 启动注册)     │
         └─────────────┬──────────────┘
                       │
        ┌──────────────┼──────────────┐
        │              │              │
  ┌─────▼──────┐ ┌────▼─────┐ ┌─────▼──────────┐
  │ JSON 文件   │ │  Nacos   │ │ DataSourceSet  │
  │ 数据源配置  │ │ 配置中心  │ │  (账套模型)     │
  └─────┬──────┘ └────┬─────┘ └─────┬──────────┘
        │              │              │
        └──────────────┼──────────────┘
                       │
         ┌─────────────▼──────────────┐
         │   AccountSetDataLoader     │
         │   (账套数据加载器)          │
         └─────────────┬──────────────┘
                       │
         ┌─────────────▼──────────────────────┐
         │  DataSourceBeanDefinitionRegister   │
         │  (为每个逻辑数据源注册:)              │
         │  - DynamicDataSource                │
         │  - SqlSessionFactory                │
         │  - SqlSessionTemplate               │
         │  - DataSourceTransactionManager     │
         │  - MapperScannerConfigurer          │
         └────────────────────────────────────┘
```

---

## 包结构说明

| 包/类 | 说明 |
|-------|------|
| `config/AileenDFConfig` | 配置类（当前已注释，Bean 注册由 StartRunner 接管） |
| `dynamic/DynamicDataSource` | 继承 `AbstractRoutingDataSource`，通过 ThreadLocal 实现数据源动态路由 |
| `enums/DBType` | 数据库类型枚举（MYSQL=0, MSSQL=1） |
| `exceptions/` | 模块自定义异常及异常工厂 |
| `loader/DataSourceLoader` | 基于 ThreadLocal 的数据源上下文持有器 |
| `loader/AccountSetDataLoader` | 账套数据加载器，按 dbId 聚合各账套的数据源 |
| `model/AccountSet` | 账套模型（ID、名称、是否默认、数据源列表） |
| `model/DataSourceSet` | 数据源集合（包含多个账套） |
| `model/DataSourceData` | 单个数据源配置（dbId/dbName/dbType/dbServer/dbUser/dbPassword） |
| `model/DataSourceConfigDto` | JDBC URL 模板 DTO，按数据库类型生成实际 JDBC URL |
| `model/DriverComConfigDto` | 驱动通用配置（driverClassName + jdbcUrl 模板） |
| `starter/DataSourceStartRunner` | **核心启动组件**，实现 `BeanDefinitionRegistryPostProcessor`，在 Spring 容器启动时加载配置并注册所有 Bean |
| `utils/AileenBeanUtils` | Bean 工具类，封装单例注册、Bean 定义注册、Bean 获取 |
| `utils/DataSourceBeanDefinitionRegister` | 数据源 Bean 定义注册器，创建 DynamicDataSource、SqlSessionFactory 等 |

---

## 配置说明

### application.yml 核心配置项

```yaml
datasource-mod:
  # 是否从 Nacos 加载账套配置
  nacos-enable: true
  nacos-data-id: datasource-set
  nacos-group: DEFAULT_GROUP
  nacos-namespace: <namespace-id>
  nacos-server-addr: 127.0.0.1:8848
  # 本地 JSON 文件路径（nacos-enable=false 时使用）
  file-path: "classpath:datasource/datasourceset.json"

  # 逻辑数据源对应的 Mapper XML 位置
  mapper-locations:
    d1: classpath*:mapper/test1/*.xml
    d2: classpath*:mapper/test2/*.xml
  # 逻辑数据源对应的 Mapper 接口包路径
  mapper-base-package:
    d1: com.example.demo.mapper
    d2: com.example.demo.mapper

  logic:
    # 所有逻辑数据源名称（逗号分隔）
    names: d1, d2
    # 默认数据源
    default: d1

  # MyBatis 全局配置
  mybatis-config:
    cache-enabled: false
    map-underscore-to-camel-case: false
    call-setters-on-nulls: true
    return-instance-for-empty-row: false

  # JDBC URL 模板（[dbServer] 和 [dbName] 为占位符，运行时替换）
  config:
    dbServer: "[dbServer]"
    dbName: "[dbName]"
    mssql:
      driverClassName: "com.microsoft.sqlserver.jdbc.SQLServerDriver"
      jdbcUrl: "jdbc:sqlserver://[dbServer]:1433;databaseName=[dbName];encrypt=false;trustServerCertificate=true"
    mysql:
      driverClassName: "com.mysql.cj.jdbc.Driver"
      jdbcUrl: "jdbc:mysql://[dbServer]:3306/[dbName]?useUnicode=true&characterEncoding=utf8&serverTimezone=UTC&allowPublicKeyRetrieval=true"
```

### 账套 JSON 配置格式

```json
{
  "accountSets": [
    {
      "accountSetID": 1,
      "accountSetName": "TuTor_Ali",
      "serviceName": "TuTor_Ali",
      "isDefault": true,
      "data": [
        {
          "id": "1",
          "dbname": "Tutor_Ali",
          "dbid": "d1",
          "dbtype": "0",
          "dbserver": "<RSA加密的服务器地址>",
          "dbpassword": "<RSA加密的密码>",
          "dbuser": "tutor"
        }
      ]
    }
  ]
}
```

- `dbtype`: `0` = MySQL, `1` = SQL Server
- `dbserver` / `dbpassword`: 支持 RSA 加密存储，运行时通过 `CryptoUnits.defaultDecrypt()` 自动解密
- `dbid`: 对应 `logic.names` 中的逻辑数据源名称

---

## 使用方式

### 1. 切换数据源

在业务代码中通过 `DataSourceLoader` 切换当前线程的数据源：

```java
// 切换到指定账套
DataSourceLoader.setDataSource("TuTor_tencent");

try {
    // 执行数据库操作...
} finally {
    // 清理线程上下文
    DataSourceLoader.clearDataSource();
}
```

### 2. 注入特定数据源的 SqlSessionTemplate

```java
@Autowired
@Qualifier("sqlSessionTemplate_d1")
private SqlSessionTemplate sqlSessionTemplateD1;
```

---

## 依赖模块

| 模块 | 用途 |
|------|------|
| `CryptoMod` | RSA 加解密，用于解密数据源配置中的敏感信息 |

## 技术依赖

- Spring Boot (JDBC + Tomcat)
- MyBatis Spring Boot Starter
- Jackson (JSON 解析)
- HikariCP (连接池，Spring Boot 默认)
- Nacos Config Client (可选，用于远程配置加载)
- MySQL Connector / SQL Server JDBC Driver
