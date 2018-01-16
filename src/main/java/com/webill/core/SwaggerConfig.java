package com.webill.core;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;
import springfox.documentation.builders.PathSelectors;
import springfox.documentation.service.ApiInfo;
import springfox.documentation.service.Contact;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

@Configuration
@EnableSwagger2
@EnableWebMvc
@ComponentScan(basePackages = {"com.webill.app.controller"})
public class SwaggerConfig {

    @Bean
    public Docket customDocket() {
        //
        return new Docket(DocumentationType.SWAGGER_2)
                .apiInfo(apiInfo())
                .select()
                .paths(PathSelectors.regex(".*api.*"))
                .build();
    }

    private ApiInfo apiInfo() {
        Contact contact = new Contact("Apache 2.0", "http://www.manmanh.com.cn", "support@manmanh.com");
        return new ApiInfo("慢慢花API接口",//大标题 title
                "慢慢花API接口",//小标题
                "1.0.0",//版本
                "http://swagger.io/terms/",//termsOfServiceUrl
                contact,//作者
                "",//链接显示文字
                ""//网站链接
        );
    }

}

