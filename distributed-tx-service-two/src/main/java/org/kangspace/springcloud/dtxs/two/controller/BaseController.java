package org.kangspace.springcloud.dtxs.two.controller;

import org.kangspace.springcloud.dtxs.two.service.iface.facade.IServiceFacade;
import org.springframework.beans.factory.annotation.Autowired;

import javax.annotation.Resource;

/**
 * 2019/5/9 14:43
 *
 * @author kango2ger@gmail.com
 */
@Resource
public class BaseController {
    @Autowired
    IServiceFacade serviceFacade;
}
