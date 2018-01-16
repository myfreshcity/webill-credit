package com.webill.app;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.ImportResource;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.config.annotation.*;
import springfox.documentation.builders.ParameterBuilder;
import springfox.documentation.builders.PathSelectors;
import springfox.documentation.schema.ModelRef;
import springfox.documentation.service.ApiInfo;
import springfox.documentation.service.Contact;
import springfox.documentation.service.Parameter;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

import java.util.ArrayList;
import java.util.List;

@Configuration
@EnableSwagger2
@EnableWebMvc
@ComponentScan(basePackages = {"com.webill.app.controller"})
@ImportResource("classpath:spring/spring-mvc.xml")
public class WebConfig extends WebMvcConfigurerAdapter {

    private final static Logger logger = LoggerFactory.getLogger(WebConfig.class);

    @Bean
    public Docket customDocket() {
        ParameterBuilder tokenPar = new ParameterBuilder();
        List<Parameter> pars = new ArrayList<Parameter>();
        tokenPar.name("token").description("令牌").modelRef(new ModelRef("string")).parameterType("header").required(false).build();
        pars.add(tokenPar.build());
        return new Docket(DocumentationType.SWAGGER_2)
                .apiInfo(apiInfo())
                .select()
                .paths(PathSelectors.regex(".*api.*"))
                .build()
                .globalOperationParameters(pars);
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

    //配置静态资源
    @Override
    public void addResourceHandlers(ResourceHandlerRegistry registry) {
        logger.info("addResourceHandlers");
        registry.addResourceHandler("/assets/**").addResourceLocations("/WEB-INF/assets/");
        registry.addResourceHandler("/static/**").addResourceLocations("/WEB-INF/static/");

        registry.addResourceHandler("swagger-ui.html")
                .addResourceLocations("classpath:/META-INF/resources/");
        registry.addResourceHandler("webjars/**")
                .addResourceLocations("classpath:/META-INF/resources/webjars/");
    }

    //允许跨域的接口
    @Override
    public void addCorsMappings(CorsRegistry registry) {
        registry.addMapping("/api/*").allowedOrigins("*")
                .allowCredentials(false)
                .allowedMethods("GET", "POST", "DELETE", "PUT")
                .allowedHeaders("Access-Control-Allow-Origin", "Access-Control-Allow-Headers", "Access-Control-Allow-Methods"
                        , "Access-Control-Max-Age")
                .exposedHeaders("Access-Control-Allow-Origin")
                .maxAge(3600);
    }

    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        logger.info("addInterceptors");
        registry.addInterceptor(getTokenHeader())
                .addPathPatterns("/api/**/*")
                .excludePathPatterns(
                        "/robots.txt");
    }

    @Bean
    public HandlerInterceptor getTokenHeader(){
        return new HeaderTokenInterceptor();
    }

}

