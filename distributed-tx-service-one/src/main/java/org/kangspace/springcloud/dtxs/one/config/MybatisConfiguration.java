package org.kangspace.springcloud.dtxs.one.config;


import com.github.pagehelper.PageHelper;
import org.apache.ibatis.plugin.Interceptor;
import org.apache.ibatis.session.SqlSessionFactory;
import org.kangspace.springcloud.dtxs.one.config.db.DataSourceType;
import org.kangspace.springcloud.dtxs.one.config.db.DynamicRoutingDataSource;
import org.mybatis.spring.SqlSessionFactoryBean;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.autoconfigure.AutoConfigureAfter;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.io.support.PathMatchingResourcePatternResolver;
import org.springframework.jdbc.datasource.DataSourceTransactionManager;
import org.springframework.jdbc.datasource.lookup.AbstractRoutingDataSource;
import org.springframework.transaction.PlatformTransactionManager;
import org.springframework.transaction.annotation.EnableTransactionManagement;
import org.springframework.transaction.annotation.TransactionManagementConfigurer;

import javax.annotation.Resource;
import javax.sql.DataSource;
import java.util.HashMap;
import java.util.Map;
import java.util.Properties;

@Configuration
@EnableTransactionManagement
@AutoConfigureAfter(DataSourceConfiguration.class)
public class MybatisConfiguration   implements TransactionManagementConfigurer {
    /**
     * DataSourceConfiguration 配置的数据库名称
     */
    @Resource(name = "masterDataSource")
    private DataSource masterDataSource;
    @Resource(name = "slaveDataSource")
    private DataSource slaveDataSource;
    @Value("${mybatis.mapper-locations}")
    private String mapperLocations;

    @Bean
    public AbstractRoutingDataSource roundRobinDataSourceProxy() {
        DynamicRoutingDataSource proxy = new DynamicRoutingDataSource();
        Map<Object, Object> targetDataResources = new HashMap<>(1);
        targetDataResources.put(DataSourceType.MASTER, masterDataSource);
        targetDataResources.put(DataSourceType.SLAVE, slaveDataSource);
        //默认源
        proxy.setDefaultTargetDataSource(masterDataSource);
        proxy.setTargetDataSources(targetDataResources);
        proxy.afterPropertiesSet();
        return proxy;
    }

    @Bean(name = "sqlSessionFactory")
    @ConditionalOnMissingBean
    public SqlSessionFactory sqlSessionFactory() throws Exception {
        SqlSessionFactoryBean sqlSessionFactoryBean = new SqlSessionFactoryBean();
        //TODO 此处暂时取消多数据源配置,LCN 5.0.2
        //sqlSessionFactoryBean.setDataSource(roundRobinDataSourceProxy());
        sqlSessionFactoryBean.setDataSource(masterDataSource);

        //分页设置
        sqlSessionFactoryBean.setPlugins(new Interceptor[]{ pageHelper()});
        PathMatchingResourcePatternResolver resolver = new PathMatchingResourcePatternResolver();
        sqlSessionFactoryBean.setMapperLocations(resolver.getResources(mapperLocations));
        sqlSessionFactoryBean.getObject().getConfiguration().setMapUnderscoreToCamelCase(true);
        return sqlSessionFactoryBean.getObject();
    }

    /**
     * 事务配置,考虑多数据源情况下
     * @return
     */
    @Bean
    public PlatformTransactionManager txManager() {
        //TODO 此处暂时取消多数据源配置,LCN 5.0.2
        //new DataSourceTransactionManager(roundRobinDataSourceProxy());
        return new DataSourceTransactionManager(masterDataSource);
    }

    @Override
    public PlatformTransactionManager annotationDrivenTransactionManager() {
        return txManager();
    }
    @Bean
    public PageHelper pageHelper() {
        //分页插件设置
        PageHelper pageHelper = new PageHelper();
        Properties properties = new Properties();
        //启用合理化时，如果pageNum<1会查询第一页，如果pageNum>pages会查询最后一页
        properties.setProperty("reasonable", "true");
        properties.setProperty("supportMethodsArguments", "true");
        properties.setProperty("returnPageInfo", "check");
        properties.setProperty("params", "count=countSql");
        pageHelper.setProperties(properties);
        return pageHelper;
    }
}
