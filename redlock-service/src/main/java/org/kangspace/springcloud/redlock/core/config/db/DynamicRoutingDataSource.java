package org.kangspace.springcloud.redlock.core.config.db;

import org.springframework.jdbc.datasource.lookup.AbstractRoutingDataSource;

/**
 * 实现spring的抽象类AbstractRoutingDataSource，就是实现determineCurrentLookupKey
 *
 * @vsersion 1.0.0
 * @date Created on 17:14 2017/11/1
 */
public class DynamicRoutingDataSource extends AbstractRoutingDataSource {
    @Override
    protected Object determineCurrentLookupKey() {
        return DbContextHolder.getDbType();
    }
}
