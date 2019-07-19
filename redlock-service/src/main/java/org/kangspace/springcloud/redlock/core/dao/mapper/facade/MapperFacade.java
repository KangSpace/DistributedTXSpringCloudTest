package org.kangspace.springcloud.redlock.core.dao.mapper.facade;

import org.kangspace.springcloud.redlock.core.dao.mapper.OpTableCallerMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

@Repository
public class MapperFacade implements IMapperFacade {
    @Autowired
    private OpTableCallerMapper opTableCallerMapper;

    @Override
    public OpTableCallerMapper getOpTableCallerMapper() {
        return opTableCallerMapper;
    }
}
