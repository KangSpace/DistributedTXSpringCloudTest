package org.kangspace.springcloud.dtxs.one.feign.hystrix;

import org.kangspace.springcloud.dtxs.common.utils.req.ResponseEntity;
import org.kangspace.springcloud.dtxs.one.feign.ServiceTwoClient;
import org.springframework.stereotype.Component;

/**
 * @author kango2ger@gmail.com
 * @desc 调用 ServiceOne SpringCloud服务熔断处理
 * @date 2019/5/9 9:41
 */

@Component
public class ServiceTwoClientHystrix implements ServiceTwoClient {
    @Override
    public ResponseEntity twoSave(String calledFrom) {
        return null;
    }
}
