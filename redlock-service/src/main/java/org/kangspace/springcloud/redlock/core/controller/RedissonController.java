package org.kangspace.springcloud.redlock.core.controller;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.kangspace.springcloud.dtxs.common.utils.req.ResponseCode;
import org.kangspace.springcloud.dtxs.common.utils.req.ResponseEntity;
import org.kangspace.springcloud.redlock.core.RedLockApplication;
import org.kangspace.springcloud.redlock.core.redisson.springboot.RedissonUseage;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;

/**
 * RedissonController
 * 在{@link RedLockApplication#main(String[])} 中启动}
 *
 *
 * @author kango2ger@gmail.com
 */
@RestController
@EnableAutoConfiguration
@Api(value = "RedissonController", description = "RedissonController", tags = "RedissonController")
@RequestMapping(value = "rest/api/redisson")
public class RedissonController extends BaseController{
    @Autowired
    private RedissonUseage redissonUseage;

    @ApiOperation(value = "RedissonGet"
            , notes = "RedissonGet", httpMethod = "GET"
            , produces = MediaType.ALL_VALUE)
    @RequestMapping(value = "", method = RequestMethod.GET)
    @ResponseBody
    public ResponseEntity get(@RequestParam("key") String key) {
        return new ResponseEntity(ResponseCode.SUCCESS,redissonUseage.get(key));
    }

    @ApiOperation(value = "RedissonSet"
            , notes = "RedissonSet", httpMethod = "POST"
            , produces = MediaType.ALL_VALUE)
    @RequestMapping(value = "", method = RequestMethod.POST)
    @ResponseBody
    public ResponseEntity set(@RequestParam("key") String key,
                              @RequestParam("val") String val) {
        redissonUseage.set(key, val);
        return new ResponseEntity(ResponseCode.SUCCESS,null);
    }

    @ApiOperation(value = "RedissonLock"
            , notes = "RedissonLock", httpMethod = "PUT"
            , produces = MediaType.ALL_VALUE)
    @RequestMapping(value = "redLock", method = RequestMethod.PUT)
    @ResponseBody
    public ResponseEntity lock(@RequestParam("key") String key,
                               @RequestParam(value = "time",required = false) Integer time) {
        return new ResponseEntity(ResponseCode.SUCCESS,redissonUseage.redlock(key,time));
    }

    @ApiOperation(value = "unRedLock"
            , notes = "unRedLock", httpMethod = "POST"
            , produces = MediaType.ALL_VALUE)
    @RequestMapping(value = "unredLock", method = RequestMethod.POST)
    @ResponseBody
    public ResponseEntity unlock(@RequestParam("key") String key) {
        redissonUseage.unredlock(key);
        return new ResponseEntity(ResponseCode.SUCCESS,null);
    }


}
