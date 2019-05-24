package org.kangspace.springcloud.dtxs.one.service.iface.facade;

import org.kangspace.springcloud.dtxs.one.service.base.IBaseService;
import org.kangspace.springcloud.dtxs.one.service.iface.IServiceOneService;

/**
 * @author kango2ger@gmail.com
 * @desc
 * @date 2019/5/6 18:24
 */
public interface IServiceFacade {
    IBaseService getBaseService();

    IServiceOneService getServiceOneService();
}
