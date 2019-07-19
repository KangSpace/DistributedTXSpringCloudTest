package org.kangspace.springcloud.redlock.core.redisson.java;

import com.google.common.base.Charsets;
import com.google.common.io.Resources;
import org.redisson.Redisson;
import org.redisson.api.RedissonClient;
import org.redisson.config.Config;

import java.io.IOException;
import java.net.URL;

/**
 * Java Code 实现Redisson的使用
 * 2019/7/16 16:41
 *
 * @author kangxuefeng@etiantian.com
 */
public class RedissonUseage{
    private static String CONFIG_FILE = "redisson-masterslave-config.json";

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

    public static void main(String[] args) throws IOException {
        Config config = new Config();
        System.out.println(config.toJSON());
        System.out.println(config.toYAML());

    }
}
