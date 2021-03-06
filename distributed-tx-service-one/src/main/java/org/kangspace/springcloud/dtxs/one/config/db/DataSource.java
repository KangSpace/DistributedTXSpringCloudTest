package org.kangspace.springcloud.dtxs.one.config.db;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/*annotation的定义*/
@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.METHOD)
public @interface DataSource {
    DataSourceType value() default DataSourceType.MASTER;
}
