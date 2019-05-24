package org.kangspace.springcloud.dtxs.one.service.impl.facade;

import org.kangspace.springcloud.dtxs.one.service.base.IBaseService;
import org.kangspace.springcloud.dtxs.one.service.iface.IServiceOneService;
import org.kangspace.springcloud.dtxs.one.service.iface.facade.IServiceFacade;
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
    private IServiceOneService serviceOneService;

    @Override
    public IBaseService getBaseService() {
        return baseService;
    }

    @Override
    public IServiceOneService getServiceOneService() {
        return serviceOneService;
    }


}
