package org.kangspace.springcloud.dtxs.caller.controller;

import org.kangspace.springcloud.dtxs.caller.service.iface.facade.IServiceFacade;
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
