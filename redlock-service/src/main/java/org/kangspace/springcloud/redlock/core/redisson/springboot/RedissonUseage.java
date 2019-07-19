package org.kangspace.springcloud.redlock.core.redisson.springboot;

import org.redisson.RedissonRedLock;
import org.redisson.api.RLock;
import org.redisson.api.RMap;
import org.redisson.api.RedissonClient;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.Map;
import java.util.concurrent.TimeUnit;

/**
 * SpringBoot start 实现Redisson的使用
 * 2019/7/16 17:03
 *
 * @author kangxuefeng@etiantian.com
 */
@Component
public class RedissonUseage {
    @Autowired
    RedissonClient redisson;

    public String get(String key){
        return (String) redisson.getBucket(key).get();
    }
    public void set(String key,String val){
        redisson.getBucket(key).set(val);
    }
    public Object hGet(String key){
        RMap rMap = redisson.getMap(key);
        return rMap.get(key);
    }
    public boolean hSet(String key, Map val){
        //hset
        return redisson.getMap(key).fastPut(key,val);
        //hsetnx
//        redisson.getMap(key).fastPutIfAbsent();
    }
    public boolean redlock(String key,Integer time){
        RLock lock = redisson.getLock(key);
        RedissonRedLock redlock = (RedissonRedLock) redisson.getRedLock(lock);
        if(time!=null) {
            try {
               return redlock.tryLock(-1,time.longValue(), TimeUnit.MINUTES);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
//            redlock.tryLock()
        }
        if(redlock.tryLock()){
            redlock.unlock();
            return true;
        }
        return false;
    }

    public void unredlock(String key){
        RLock lock = redisson.getLock(key);
        lock.unlock();
        RedissonRedLock redlock = (RedissonRedLock) redisson.getRedLock(lock);
        redlock.unlock();
    }
}
