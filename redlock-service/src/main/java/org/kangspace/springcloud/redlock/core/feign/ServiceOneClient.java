package org.kangspace.springcloud.redlock.core.feign;

import org.kangspace.springcloud.redlock.core.feign.hystrix.ServiceOneClientHystrix;
import org.kangspace.springcloud.dtxs.common.utils.req.ResponseEntity;
import org.springframework.cloud.netflix.feign.FeignClient;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

/**
 * @author kango2ger@gmail.com
 * @desc 调用 ServiceOne SpringCloud服务
 * @date 2019/5/9 9:41
 */

@Component
@FeignClient(value = "distributed-tx-service-one", fallback = ServiceOneClientHystrix.class)
public interface ServiceOneClient {
    @PostMapping("v1/api/serviceones")
    ResponseEntity serviceOneSave(@RequestParam("calledFrom") String calledFrom);
}
