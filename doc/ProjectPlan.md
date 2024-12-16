# Project Plan

## BotSign

- [ ] 签名
- [ ] token 验证
- [ ] 登录验证

## BotCommon

- [x] Http 拦截器
- [ ] 定时器
- [ ] MQ

## BotWeb

- [x] 启动模块。
- [x] Slf4j 日志 配置
- [ ] 多用户情况下的日志
- [ ] 事务
- [ ] 多模块的 sql 服务

## SeleniumBot

Web自动化测试驱动模块

## TutorConfigService

- [ ] 数据源配置从此获取
- [ ] 需要引入 BotSign

## TutorBotDataSource

- [ ] 多数据源
- [ ] seata 


## 关于重复依赖引用的问题解决

2024年11月13日 

当前项目的 spring-boot-start-web 依赖重复引入。

如果解决？