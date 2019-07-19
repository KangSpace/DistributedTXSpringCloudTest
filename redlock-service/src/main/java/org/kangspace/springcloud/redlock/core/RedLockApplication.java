package org.kangspace.springcloud.redlock.core;

import org.kangspace.springcloud.dtxs.common.utils.SpringApplicationContext;
import org.kangspace.springcloud.redlock.core.redisson.java.RedissonUseage;
import org.redisson.api.RedissonClient;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.jdbc.DataSourceAutoConfiguration;
import org.springframework.context.annotation.Bean;

@SpringBootApplication(exclude = DataSourceAutoConfiguration.class)
public class RedLockApplication {

    public static void main(String[] args) {
        SpringApplication.run(RedLockApplication.class, args);
    }
    @Bean
    public SpringApplicationContext springApplicationContext(){
        return new SpringApplicationContext();
    }

    /**
     * //TODO 使用程序化配置org.kangspace.springcloud.redlock.core.redisson.java.RedissonClient
     *  运行是添加此代码
     *  若为redisson-spring-boot-starter,则删除改代码
     * @return
     */
    @Bean
    public RedissonClient redisson(){
        return new RedissonUseage().getRedissonClient();
    }
}
