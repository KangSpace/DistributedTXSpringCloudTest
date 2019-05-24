package org.kangspace.springcloud.dtxs.two.dao.mapper.facade;

import org.kangspace.springcloud.dtxs.two.dao.mapper.OpTableTwoMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

@Repository
public class MapperFacade implements IMapperFacade {
    @Autowired
    private OpTableTwoMapper opTabletwoMapper;
    @Override
    public OpTableTwoMapper getOpTableTwoMapper() {
        return opTabletwoMapper;
    }
}
