package cn.com.taiji.fourth.config;//package cn.com.taiji.fourth.config;
//
//import org.slf4j.Logger;
//import org.slf4j.LoggerFactory;
//import org.springframework.context.annotation.Bean;
//import org.springframework.context.annotation.Configuration;
//import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
//import org.springframework.web.servlet.config.annotation.WebMvcConfigurationSupport;
//import springfox.documentation.builders.ApiInfoBuilder;
//import springfox.documentation.builders.PathSelectors;
//import springfox.documentation.builders.RequestHandlerSelectors;
//import springfox.documentation.service.ApiInfo;
//import springfox.documentation.service.Contact;
//import springfox.documentation.spi.DocumentationType;
//import springfox.documentation.spring.web.plugins.Docket;
//import springfox.documentation.swagger2.annotations.EnableSwagger2;
//
///**
// * swagger配置
// */
//@Configuration
//@EnableSwagger2
//public class SwaggerConfig extends WebMvcConfigurationSupport {
//
//    private final Logger log = LoggerFactory.getLogger(SwaggerConfig.class);
//
//    @Bean
//    public Docket createRestApi() {
//        return new Docket(DocumentationType.SWAGGER_2)
//                .apiInfo(apiInfo())
//                .select()
//                .apis(RequestHandlerSelectors.basePackage("cn.com.taiji.fourth.webapi"))
//                .paths(PathSelectors.any())
//                .build();
//    }
//
//    private ApiInfo apiInfo() {
//        return new ApiInfoBuilder()
//                .title("Tdf security演示RESTful APIs")
//                .description("如果有疑问可以及时沟通，主要负责人andyzhao")
//                .termsOfServiceUrl("XXXXXXXXXXX")
//                .contact(new Contact("zhaozhao", "http://tech.taiji.com.cn", "zhaozhao@mail.taiji.com..cn"))
//                .version("1.0")
//                .build();
//    }
//
////    @Override
////    protected void addResourceHandlers(ResourceHandlerRegistry registry) {
////        registry.addResourceHandler("/swagger-ui.html").addResourceLocations(
////                "classpath:/META-INF/resources/");
////        registry.addResourceHandler("/webjars/**").addResourceLocations(
////                "classpath:/META-INF/resources/webjars/");
////    }
//}
