package org.kangspace.springcloud.graphql.core.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.context.request.async.DeferredResult;
import springfox.documentation.builders.ApiInfoBuilder;
import springfox.documentation.builders.PathSelectors;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.service.ApiInfo;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

/**
 * 2019/5/9 13:59
 *
 * @author kango2ger@gmail.com
 */
@Configuration
@EnableSwagger2
public class SwaggerConfig {
    @Value("${swagger.base-package}")
    private String basePackage;

    @Bean
    public Docket api() {
        return new Docket(DocumentationType.SWAGGER_2)
                //多版本问题
                .groupName("v1")
                .genericModelSubstitutes(DeferredResult.class)
                .useDefaultResponseMessages(false)
//                .forCodeGeneration(true)
//                .pathMapping("/Test")//api测试请求地址
                .select()
                .apis(RequestHandlerSelectors.basePackage(basePackage))
//                .paths(PathSelectors.regex("/api/.*"))//过滤的接口 全部api接口
                //过滤的接口 全部api接口
                .paths(PathSelectors.any())
                .build()
                .apiInfo(apiInfo());
    }

    private ApiInfo apiInfo() {
        return new ApiInfoBuilder()
                .title("SpringCloud测试分布式事务Caller API文档")
                .description("HTTP对外开放接口")
                .version("1.0.0")
                .termsOfServiceUrl("https://KangSpace.org")
//                .license("LICENSE")
//                .licenseUrl("http://xxx.xxx.com")
                .build();

    }
}
