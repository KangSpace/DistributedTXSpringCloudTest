package org.kangspace.springcloud.dtxs.caller.service.iface.facade;

import org.kangspace.springcloud.dtxs.caller.service.base.IBaseService;
import org.kangspace.springcloud.dtxs.caller.service.iface.ICallerService;

/**
 * @author kango2ger@gmail.com
 * @desc
 * @date 2019/5/6 18:24
 */
public interface IServiceFacade {
    IBaseService getBaseService();
    ICallerService getCallerService();
}
