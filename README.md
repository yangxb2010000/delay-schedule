# delay-schedule
分布式、高可用的延迟调度系统、可以配合消息队列实现延迟任务队列

#### delay-schedule架构图
![分布式延迟任务调度架构图.jpg](https://github.com/yangxb2010000/delay-schedule/blob/master/doc/分布式延迟任务调度架构图.jpg)

#### zookeeper架构图
![分布式延迟任务调度架构图.jpg](https://github.com/yangxb2010000/delay-schedule/blob/master/doc/zookeeper设计图.jpg)


## 项目介绍
#### delay-schedule-client
delay-schedule客户端使用的SDK，即调用方引用该SDK。

#### delay-schedule-server-core
delay-schedule服务端实现的核心逻辑的类都在改项目中

#### delay-schedule-server-spring-boot-starter
基于delay-schedule-server-core适配springboot，对delay-schedule-server-core中得类做装配注入spring容器中

#### delay-schedule-server-web
启动项目，根据需要引入需要的starter，如rabbitmq-spring-boot-starter来实现相应的功能

#### delay-schedule-server-rabbitmq-spring-boot-starter
适配rabbitmq的延时任务队列逻辑

## TODO：
#### 一期目标： 基于mysql、zookeeper实现基本功能
1. client-proxy对外提供http接口支持传入延时消息功能
2. client-proxy通过slot-sharding的方式把延时消息发送到相应的schedule-server实例中
3. schedule-server基于时间轮实现内存中的延时调度，并把延时消息持久化到db中
4. schedule-server通过zookeeper实现集群的高可用，包括leader选举、rebalance
5. schedule-server对外提供http接口
6. 接入RabbitMQ实现延时任务队列

#### 二期目标：
1. schedule-server接入metrics，可以实时展示schedule-server内存运行时状态
2. 添加dashboard 前端项目，可以直观查看运行时状态，实现一些基本功能
3. 接入监控、告警系统
4. 支持定时、周期执行任务
5. 支持设定任务的重试策略、重试多次失败之后告警
6. 添加压测项目
7. kubernetes部署

#### 三期目标
1. 完善dashboard项目，支持更多指标、功能
2. 支持不依赖zookeeper、只依赖mysql
3. 考虑支持异步接口、支持批量接口
4. 考虑schedule-server之间支持主从模式，可以直接传输数据
5. 如果mysql性能不够、考虑使用RocksDB。可能与4配合使用
