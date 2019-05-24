package org.kangspace.springcloud.dtxs.two.service.iface.facade;

import org.kangspace.springcloud.dtxs.two.service.base.IBaseService;
import org.kangspace.springcloud.dtxs.two.service.iface.IServiceTwoService;

/**
 * @author kango2ger@gmail.com
 * @desc
 * @date 2019/5/6 18:24
 */
public interface IServiceFacade {
    IBaseService getBaseService();

    IServiceTwoService getServiceTwoService();
}
