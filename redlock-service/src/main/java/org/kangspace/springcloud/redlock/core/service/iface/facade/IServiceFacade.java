package org.kangspace.springcloud.redlock.core.service.iface.facade;

import org.kangspace.springcloud.redlock.core.service.base.IBaseService;
import org.kangspace.springcloud.redlock.core.service.iface.ICallerService;

/**
 * @author kango2ger@gmail.com
 * @desc
 * @date 2019/5/6 18:24
 */
public interface IServiceFacade {
    IBaseService getBaseService();
    ICallerService getCallerService();
}
