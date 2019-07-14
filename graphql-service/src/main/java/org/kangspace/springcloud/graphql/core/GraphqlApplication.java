package org.kangspace.springcloud.graphql.core;

import org.kangspace.springcloud.dtxs.common.utils.SpringApplicationContext;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.jdbc.DataSourceAutoConfiguration;
import org.springframework.boot.web.servlet.ServletComponentScan;
import org.springframework.context.annotation.Bean;

@ServletComponentScan(basePackages = "org.kangspace.springcloud.graphql.core.graphql")
//@ComponentScan(basePackages = {"org.kangspace.springcloud.graphql.core"})
@SpringBootApplication(exclude = DataSourceAutoConfiguration.class)
public class GraphqlApplication {

    public static void main(String[] args) {
        SpringApplication.run(GraphqlApplication.class, args);
    }
    @Bean
    public SpringApplicationContext springApplicationContext(){
        return new SpringApplicationContext();
    }

}
