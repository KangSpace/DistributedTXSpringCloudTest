package org.kangspace.springcloud.dtxs.two.service.base;

import org.kangspace.springcloud.dtxs.common.utils.SpringApplicationContext;
import org.kangspace.springcloud.dtxs.two.dao.mapper.facade.IMapperFacade;
import org.springframework.beans.factory.annotation.Autowired;

/**
 * 2019/5/9 9:37
 *
 * @author kango2ger@gmail.com
 */
public class BaseService implements IBaseService {
    @Autowired
    public IMapperFacade mapperFacade;

    @Autowired
    public SpringApplicationContext springApplicationContext;

}
