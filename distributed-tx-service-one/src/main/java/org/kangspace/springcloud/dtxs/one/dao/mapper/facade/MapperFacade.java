package org.kangspace.springcloud.dtxs.one.dao.mapper.facade;

import org.kangspace.springcloud.dtxs.one.dao.mapper.OpTableOneMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

@Repository
public class MapperFacade implements IMapperFacade {
    @Autowired
    private OpTableOneMapper opTableOneMapper;

    @Override
    public OpTableOneMapper getOpTableOneMapper() {
        return opTableOneMapper;
    }
}
