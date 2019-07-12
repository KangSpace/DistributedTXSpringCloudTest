package org.kangspace.springcloud.graphql.core;

import org.kangspace.springcloud.dtxs.common.utils.SpringApplicationContext;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

@SpringBootApplication
//@ComponentScan(basePackages = {"org.kangspace.springcloud.graphql.core"})
//@ServletComponentScan
public class GraphqlApplication {

    public static void main(String[] args) {
        SpringApplication.run(GraphqlApplication.class, args);
    }
    @Bean
    public SpringApplicationContext springApplicationContext(){
        return new SpringApplicationContext();
    }

}
