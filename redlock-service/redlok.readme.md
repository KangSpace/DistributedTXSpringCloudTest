#1. RedLock Test
##  内容:
    RedLock 使用实例
> References:   
    [官网说明:https://redis.io/topics/distlock](https://redis.io/topics/distlock)  
    [Java实现:https://github.com/redisson/redisson](https://github.com/redisson/redisson)  
    [文档:https://github.com/redisson/redisson/wiki](https://github.com/redisson/redisson/wiki)  
    [redisson-spring-boot-starter:https://github.com/redisson/redisson/tree/master/redisson-spring-boot-starter#spring-boot-starter](https://github.com/redisson/redisson/tree/master/redisson-spring-boot-starter#spring-boot-starter)     
    [redisson-spring-data:https://github.com/redisson/redisson/tree/master/redisson-spring-data#spring-data-redis-integration](https://github.com/redisson/redisson/tree/master/redisson-spring-data#spring-data-redis-integration)     
    [11. Redis命令和Redisson对象匹配列表:https://github.com/redisson/redisson/wiki/11.-redis命令和redisson对象匹配列表](https://github.com/redisson/redisson/wiki/11.-redis%E5%91%BD%E4%BB%A4%E5%92%8Credisson%E5%AF%B9%E8%B1%A1%E5%8C%B9%E9%85%8D%E5%88%97%E8%A1%A8)

Redlock算法   
在算法的分布式版本中，我们假设我们有N个Redis主机。这些节点完全独立，因此我们不使用复制或任何其他隐式协调系统。我们已经描述了如何在单个实例中安全地获取和释放锁。我们理所当然地认为算法将使用此方法在单个实例中获取和释放锁。在我们的示例中，我们设置N = 5，这是一个合理的值，因此我们需要在不同的计算机或虚拟机上运行5个Redis主服务器，以确保它们以大多数独立的方式失败。   

为了获取锁，客户端执行以下操作：   

1. 它以毫秒为单位获取当前时间。     
2. 它尝试按顺序获取所有N个实例中的锁，在所有实例中使用相同的键名和随机值。在步骤2期间，当在每个实例中设置锁定时，客户端使用与总锁定自动释放时间相比较小的超时以获取它。例如，如果自动释放时间是10秒，则超时可以在~5-50毫秒范围内。这可以防止客户端长时间保持阻塞，试图与Redis节点进行通信，如果实例不可用，我们应该尝试尽快与下一个实例通话。     
3. 客户端通过从当前时间中减去在步骤1中获得的时间戳来计算获取锁定所需的时间。当且仅当客户端能够在大多数实例中获取锁定时（至少3个）并且获取锁定所经过的总时间小于锁定有效时间，认为锁定被获取。  
4. 如果获得了锁，则其有效时间被认为是初始有效时间减去经过的时间，如步骤3中计算的那样。  
5. 如果客户端由于某种原因（无法锁定N / 2 + 1实例或有效时间为负）未能获取锁定，它将尝试解锁所有实例（即使它认为不是能够锁定）。  
    

####   Dependency:
```
<!-- redisson start -->
<!-- redisson-spring-boot-starter -->
<dependency>
    <groupId>org.redisson</groupId>
    <!-- for Spring Data Redis v.1.6.x -->
    <artifactId>redisson-spring-data-16</artifactId>
    <version>3.11.1</version>
</dependency>
<dependency>
    <groupId>org.redisson</groupId>
    <artifactId>redisson-spring-boot-starter</artifactId>
    <version>3.11.0</version>
</dependency>
<dependency>
    <groupId>org.redisson</groupId>
    <artifactId>redisson</artifactId>
    <version>3.11.1</version>
</dependency>
<!-- redisson end -->
```
####   Configure:
bootstrap.properties
```
#redis settings
spring.redis.pool.max-total=30
spring.redis.pool.max-idle=10
spring.redis.pool.min-idle=0
spring.redis.timeout=3000
spring.redis.cluster.nodes=127.0.0.1:6379,127.0.0.1:6380,127.0.0.1:6381
spring.redis.cluster.host=
spring.redis.cluster.port=
spring.redis.cluster.passwd=
spring.redis.cluster.timeOut=2000
spring.redis.cluster.max-redirects=3

# Redisson settings
#path to redisson.yaml or redisson.json
spring.redis.redisson.config=classpath:redisson-config.json
```
redisson-config.json
```
{
  "threads": 16,
  "nettyThreads": 32,
  "referenceEnabled": true,
  "transportMode": "NIO",
  "lockWatchdogTimeout": 30000,
  "keepPubSubOrder": true,
  "decodeInExecutor": false,
  "useScriptCache": false,
  "minCleanUpDelay": 5,
  "maxCleanUpDelay": 1800,
  "addressResolverGroupFactory": {
    "class": "org.redisson.connection.DnsAddressResolverGroupFactory"
  },
  "clusterServersConfig":{
    "idleConnectionTimeout":10000,
    "pingTimeout":1000,
    "connectTimeout":10000,
    "timeout":3000,
    "retryAttempts":3,
    "retryInterval":1500,
    "reconnectionTimeout":3000,
    "failedAttempts":3,
    "password":null,
    "subscriptionsPerConnection":5,
    "clientName":null,
    "loadBalancer":{
      "class":"org.redisson.connection.balancer.RoundRobinLoadBalancer"
    },
    "slaveSubscriptionConnectionMinimumIdleSize":1,
    "slaveSubscriptionConnectionPoolSize":50,
    "slaveConnectionMinimumIdleSize":32,
    "slaveConnectionPoolSize":64,
    "masterConnectionMinimumIdleSize":32,
    "masterConnectionPoolSize":64,
    "readMode":"SLAVE",
    "nodeAddresses":[
      "redis://127.0.0.1:7004",
      "redis://127.0.0.1:7001",
      "redis://127.0.0.1:7000"
    ],
    "scanInterval":1000
  },
  "masterSlaveServersConfig":{
    "idleConnectionTimeout":10000,
    "pingTimeout":1000,
    "connectTimeout":10000,
    "timeout":3000,
    "retryAttempts":3,
    "retryInterval":1500,
    "reconnectionTimeout":3000,
    "failedAttempts":3,
    "password":null,
    "subscriptionsPerConnection":5,
    "clientName":null,
    "loadBalancer":{
      "class":"org.redisson.connection.balancer.RoundRobinLoadBalancer"
    },
    "slaveSubscriptionConnectionMinimumIdleSize":1,
    "slaveSubscriptionConnectionPoolSize":50,
    "slaveConnectionMinimumIdleSize":32,
    "slaveConnectionPoolSize":64,
    "masterConnectionMinimumIdleSize":32,
    "masterConnectionPoolSize":64,
    "readMode":"SLAVE",
    "slaveAddresses":[
      "redis://127.0.0.1:6381",
      "redis://127.0.0.1:6380"
    ],
    "masterAddress": "redis://127.0.0.1:6379",
    "database":0
  }
}
```
####   使用:
```
local test url : 
    http://127.0.0.1:1005/swagger-ui.html#/RedissonController/
```
#####   SpringBoot:
```
# org.kangspace.springcloud.redlock.core.redisson.springboot.RedissonUseage
@Autowired
RedissonClient redisson;

```
#####   纯Java Code:
```
org.kangspace.springcloud.redlock.core.redisson.java.RedissonUseage
//程序化/配置文件获取配置
private static String CONFIG_FILE = "redisson-cluster-config.json";

    public RedissonClient getRedissonClient() {
        //使用JSON文件读取配置
        Config config = null;
        try {
              URL url = Resources.getResource(CONFIG_FILE);
                String configStr = Resources.toString(url, Charsets.UTF_8);
                config = Config.fromJSON(configStr);
        } catch (IOException e) {
            e.printStackTrace();
        }
        //集群模式配置
//        config.useClusterServers()
//                // 集群状态扫描间隔时间，单位是毫秒
//                .setScanInterval(2000)
//                //可以用"rediss://"来启用SSL连接
//                .addNodeAddress("redis://127.0.0.1:7000", "redis://127.0.0.1:7001")
//                .addNodeAddress("redis://127.0.0.1:7002");
        return Redisson.create(config);
    }

@Bean
public RedissonClient redisson(){
    return new RedissonUseage().getRedissonClient();
}
```


