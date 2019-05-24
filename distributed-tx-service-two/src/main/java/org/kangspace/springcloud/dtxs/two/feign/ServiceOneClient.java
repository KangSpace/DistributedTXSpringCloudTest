package org.kangspace.springcloud.dtxs.two.feign;

import org.springframework.cloud.netflix.feign.FeignClient;
import org.springframework.stereotype.Component;

/**
 * @author kango2ger@gmail.com
 * @desc 调用 ServiceOne SpringCloud服务
 * @date 2019/5/9 9:41
 */

@Component
@FeignClient(value = "distributed-tx-service-one", fallback = ServiceOneClient.class)
public interface ServiceOneClient {
}
