package org.kangspace.springcloud.dtxs.two.feign.hystrix;

import org.kangspace.springcloud.dtxs.two.feign.ServiceOneClient;
import org.springframework.stereotype.Component;

/**
 * @author kango2ger@gmail.com
 * @desc 调用 ServiceOne SpringCloud服务熔断处理
 * @date 2019/5/9 9:41
 */

@Component
public class ServiceOneClientHystrix implements ServiceOneClient {
}
