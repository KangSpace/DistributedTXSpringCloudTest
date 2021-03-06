package org.kangspace.springcloud.redlock.core.service.base;

import org.kangspace.springcloud.redlock.core.dao.mapper.facade.IMapperFacade;
import org.kangspace.springcloud.redlock.core.feign.ServiceOneClient;
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
    public ServiceOneClient serviceOneClient;
}
