package org.kangspace.springcloud.dtxs.caller.service.impl.facade;

import org.kangspace.springcloud.dtxs.caller.service.base.IBaseService;
import org.kangspace.springcloud.dtxs.caller.service.iface.ICallerService;
import org.kangspace.springcloud.dtxs.caller.service.iface.facade.IServiceFacade;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * @author kango2ger@gmail.com
 * @desc
 * @date 2019/5/6 18:24
 */
@Service
public class ServiceFacade implements IServiceFacade {
    @Autowired
    private IBaseService baseService;
    @Autowired
    private ICallerService callerService;
    @Override
    public IBaseService getBaseService() {
        return baseService;
    }

    @Override
    public ICallerService getCallerService() {
        return callerService;
    }
}
