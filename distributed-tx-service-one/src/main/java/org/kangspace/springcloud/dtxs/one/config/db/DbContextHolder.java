package org.kangspace.springcloud.dtxs.one.config.db;

/**
 * 标识存放ThreadLocal的实现
 */
public class DbContextHolder {

    protected static final ThreadLocal<DataSourceType> contextHolder = new ThreadLocal<>();

    public static void setDbType(DataSourceType dbType) {
        if (dbType == null){
            throw new NullPointerException();
        }
        contextHolder.set(dbType);
    }

    public static DataSourceType getDbType() {
        return contextHolder.get();
    }

    public static void clearDbType() {
        contextHolder.remove();
    }
}
