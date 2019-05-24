package org.kangspace.springcloud.dtxs.one.feign;

import org.kangspace.springcloud.dtxs.common.utils.req.ResponseEntity;
import org.kangspace.springcloud.dtxs.one.feign.hystrix.ServiceTwoClientHystrix;
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
@FeignClient(value = "distributed-tx-service-two", fallback = ServiceTwoClientHystrix.class)
public interface ServiceTwoClient {

    @PostMapping(value = "v1/api/servicetwos")
    ResponseEntity twoSave(@RequestParam("calledFrom") String calledFrom);
}
