#1. spring-boot-test 为spring boot 测试项目
##   内容:
###   1.1 启动Web服务,Controller 注册 OK
###   1.2 Mybatis Mysql 操作数据库
含tk-mybatis,mybatis,alibaba.druid,   
~~读写分离~~(此处取消读写分离,LCN5.0.2.RELEASE版不支持多数据源)
#2. 集成功能:
###   2.1 SpringCloud 分布式事务 TX-LCN(5.0.2.RELEASE)
###   2.1.1 SpringCloud 分布式事务 TX-LCN(5.0.2.RELEASE)
####  [Tx-Manager(TM)](https://www.txlcn.org/zh-cn/docs/start.html)
下载[5.0.2.RELEASE.zip](https://github.com/codingapi/tx-lcn/releases/tag/5.0.2.RELEASE)源码  
修改mysql,redis配置
```
spring.datasource.url=jdbc:mysql://127.0.0.1:3306/tx-manager?characterEncoding=UTF-8
spring.datasource.username=root
spring.datasource.password=root
spring.redis.host=127.0.0.1
#redis端口
spring.redis.port=6379

IDEA中启动tx-lcn-5.0.2.RELEASE\txlcn-tm\src\main\java\com\codingapi\txlcn\tm\TMApplication.java
启动后访问管理平台:
http://127.0.0.1:7970/admin/index.html
```
    
####  [Tx-Client(TC)](https://www.txlcn.org/zh-cn/docs/start.html)
LINKS:      [SpringCloud示例](https://www.txlcn.org/zh-cn/docs/demo/springcloud.html)  
注意Issues: [集群节点下，分布式事务回调用，会路由到非发起方机器上去](https://github.com/codingapi/tx-lcn/issues/296)  
            [#关于多数据源导致的LCN错误代理Connection的问题#](https://github.com/codingapi/tx-lcn/issues/254)  
            [同一个微服务模块多实例,TM会发生事物通知TC错乱的问题](https://github.com/codingapi/tx-lcn/issues/368)  
问题:
```
  1. 不支持多数据源
  
```  
 
1. 创建SpringCloudTest项目,  
    添加模块distributed-tx-service-caller,distributed-tx-service-one,distributed-tx-service-two  
    由service-caller 调用 service-one 服务,service-one 调用 service-two服务测试分布式事务  
    ```
    @Trans                                   @Trans                             @Trans
    distributed-tx-service-caller(1001) ->  distributed-tx-service-one(1002) -> distributed-tx-service-two(1003)  
    @Trans                                  @Trans                        
    distributed-tx-service-caller(1001) ->  distributed-tx-service-one(1002)
                                            @Trans
                                         -> distributed-tx-service-two(1003)  
    
    ```
    ```
    创建SpringBoot项目并完成项目配置    
    配置  mysql ,mybatis,tk-mybatis,druid,mybatis-generator-maven-plugin,lombok,swagger2,动态数据源切换 
    ```
    pom依赖:
    ```
    <properties>
        <spring-cloud.version>Edgware.SR5</spring-cloud.version>
        <tk.mybatis.version>3.4.0</tk.mybatis.version>
        <mybatis.typehandlers>1.0.1</mybatis.typehandlers>
        <mybatis-generator-maven-plugin.version>1.3.2</mybatis-generator-maven-plugin.version>
        <druid.version>1.1.2</druid.version>
    </properties>
    <dependencyManagement>
        <dependencies>
        
            <dependency>
                <groupId>org.projectlombok</groupId>
                <artifactId>lombok</artifactId>
                <optional>true</optional>
            </dependency>
        
           <!-- tx-lcn -->
            <dependency>
                <groupId>com.codingapi.txlcn</groupId>
                <artifactId>txlcn-tc</artifactId>
                <version>5.0.2.RELEASE</version>
            </dependency>
    
            <dependency>
                <groupId>com.codingapi.txlcn</groupId>
                <artifactId>txlcn-txmsg-netty</artifactId>
                <version>5.0.2.RELEASE</version>
            </dependency>
            
            <!-- DB -->
            <dependency>
                <groupId>mysql</groupId>
                <artifactId>mysql-connector-java</artifactId>
                <scope>runtime</scope>
            </dependency>
            <dependency>
                <groupId>com.alibaba</groupId>
                <artifactId>druid</artifactId>
                <version>${druid.version}</version>
            </dependency>
        
            <dependency>
                <groupId>tk.mybatis</groupId>
                <artifactId>mapper</artifactId>
                <version>${tk.mybatis.version}</version>
            </dependency>
        
            <dependency>
                <groupId>org.mybatis</groupId>
                <artifactId>mybatis-typehandlers-jsr310</artifactId>
                <version>${mybatis.typehandlers}</version>
            </dependency>
        </dependencies>
    </dependencyManagement>
    ```
    
    创建测试数据库表:
    ```
    DB: tx-manager
    table: 
    table_one , table_two
    ```
    TABLES DDL:
    ```
    DROP TABLE IF EXISTS `tx-manager`.`op_table_caller` ;
    CREATE TABLE `tx-manager`.`op_table_caller` (
      `id` BIGINT NOT NULL AUTO_INCREMENT,
      `op_id` VARCHAR(64) NOT NULL COMMENT '操作id-雪花算法id',
      `op_name` VARCHAR(200) NOT NULL COMMENT '操作名称',
      `op_desc` VARCHAR(200) NULL COMMENT '操作描述',
      `c_time` DATETIME NULL COMMENT '创建时间',
      `m_time` DATETIME NULL COMMENT '更新时间',
      PRIMARY KEY (`id`),
      INDEX `IDX_TABLE_CALLER_OP_ID` (`op_id` ASC))
    COMMENT = 'caller测试表';
    
    DROP TABLE IF EXISTS `tx-manager`.`op_table_one` ;
    CREATE TABLE `tx-manager`.`op_table_one` (
      `id` BIGINT NOT NULL AUTO_INCREMENT,
      `op_id` VARCHAR(64) NOT NULL COMMENT '操作id-雪花算法id',
      `op_name` VARCHAR(200) NOT NULL COMMENT '操作名称',
      `op_desc` VARCHAR(200) NULL COMMENT '操作描述',
      `c_time` DATETIME NULL COMMENT '创建时间',
      `m_time` DATETIME NULL COMMENT '更新时间',
      PRIMARY KEY (`id`),
      INDEX `IDX_TABLE_ONE_OP_ID` (`op_id` ASC))
    COMMENT = '测试表1';
    
    DROP TABLE IF EXISTS `tx-manager`.`op_table_two` ;
    CREATE TABLE `tx-manager`.`op_table_two` (
      `id` BIGINT NOT NULL AUTO_INCREMENT,
      `op_id` VARCHAR(64) NOT NULL COMMENT '操作id-雪花算法id',
      `op_name` VARCHAR(200) NOT NULL COMMENT '操作名称',
      `op_desc` VARCHAR(200) NULL COMMENT '操作描述',
      `c_time` DATETIME NULL COMMENT '创建时间',
      `m_time` DATETIME NULL COMMENT '更新时间',
      PRIMARY KEY (`id`),
      INDEX `IDX_TABLE_TWO_OP_ID` (`op_id` ASC))
    COMMENT = '测试表2';
    
    ```
    配置项目后,启动服务访问各swagger-ui.html  
    distributed-tx-service-caller: http://localhost:1001/swagger-ui.html#/  
    distributed-tx-service-one: http://localhost:1002/swagger-ui.html#/  
    distributed-tx-service-two: http://localhost:1003/swagger-ui.html#/
    ```
        日志输出以下即为启动成功:
         Searching for more TM...
        2019-05-23 11:06:29.766  INFO 30696 --- [      Thread-69] com.codingapi.txlcn.tc.txmsg.TMSearcher  : TC[distributed-tx-service-one:1002] established TM cluster successfully!
        2019-05-23 11:06:29.766  INFO 30696 --- [      Thread-69] com.codingapi.txlcn.tc.txmsg.TMSearcher  : TM cluster's size: 1

    ```  
2. 编写雪花算法实现业务id 
   雪花算法: 64位字符串 , 1位为0,41位为时间戳,10位为机器码,12位为序号
   ```
    //实现
    SpringCloudTest\distributed-tx-service-common\src\main\java\org\kangspace\springcloud\dtxs\common\utils\SnowflakeIdGenerator.java
    
    //获取id
    long id2 = SnowflakeIdGenerator.getInstance().nextId();
    System.out.println(id2);
    //
    System.out.println(SnowflakeIdGenerator.getID(id2));
    ```
   
3. 编写基于tx-lcn分布式事务接口,并测试//TODO  
    a.     service-caller 调用 service-one , service-one 调用 service-two
           其中含,service-caller,service-one ,service-two 分别异常回滚,事务一致
           @Trans                                   @Trans                             @Trans
           distributed-tx-service-caller(1001) ->  distributed-tx-service-one(1002) -> distributed-tx-service-two(1003)     
    b.     service-caller调用 service-one, service-two
           其中含,service-caller,service-one ,service-two 分别异常回滚,事务一致
           @Trans                                  @Trans                        
           distributed-tx-service-caller(1001) ->  distributed-tx-service-one(1002)
                                                   @Trans
                                                   -> distributed-tx-service-two(1003)
####  2.1.2 考虑LCN tx-manager加入k8s, LCN tx-client 接入k8s中的tx-manager  
###   2.2 ShardingJDBC
###   2.3 分布式锁 RedLock-Redission

   