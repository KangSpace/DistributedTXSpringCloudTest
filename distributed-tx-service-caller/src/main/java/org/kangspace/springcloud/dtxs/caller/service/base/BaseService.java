package org.kangspace.springcloud.dtxs.caller.service.base;

import org.kangspace.springcloud.dtxs.caller.dao.mapper.facade.IMapperFacade;
import org.kangspace.springcloud.dtxs.caller.feign.ServiceOneClient;
import org.kangspace.springcloud.dtxs.common.utils.SpringApplicationContext;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * 2019/5/9 9:37
 *
 * @author kango2ger@gmail.com
 */
@Service
public class BaseService implements IBaseService {
    @Autowired
    public IMapperFacade mapperFacade;

    @Autowired
    public SpringApplicationContext springApplicationContext;

    @Autowired
    public ServiceOneClient serviceOneClient;
}
