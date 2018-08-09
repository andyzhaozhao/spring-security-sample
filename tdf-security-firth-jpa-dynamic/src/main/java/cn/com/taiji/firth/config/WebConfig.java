//package cn.com.taiji.firth.config;
//
//import com.fasterxml.jackson.databind.ObjectMapper;
//import com.fasterxml.jackson.databind.SerializationFeature;
//import com.fasterxml.jackson.databind.util.ISO8601DateFormat;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.context.annotation.Bean;
//import org.springframework.context.annotation.Configuration;
//import org.springframework.context.annotation.Primary;
//import org.springframework.http.MediaType;
//import org.springframework.web.accept.ContentNegotiationManager;
//import org.springframework.web.servlet.View;
//import org.springframework.web.servlet.ViewResolver;
//import org.springframework.web.servlet.config.annotation.ContentNegotiationConfigurer;
//import org.springframework.web.servlet.config.annotation.EnableWebMvc;
//import org.springframework.web.servlet.config.annotation.ViewControllerRegistry;
//import org.springframework.web.servlet.config.annotation.WebMvcConfigurerAdapter;
//import org.springframework.web.servlet.view.ContentNegotiatingViewResolver;
//import org.springframework.web.servlet.view.json.MappingJackson2JsonView;
//import org.thymeleaf.dialect.IDialect;
//import org.thymeleaf.spring4.view.ThymeleafViewResolver;
//
//import javax.servlet.http.HttpServletRequest;
//import javax.servlet.http.HttpServletResponse;
//import java.util.*;
//
///**
// * Created by andyzhao on 2018/8/6.
// */
//@Configuration
////@EnableWebMvc
//public class WebConfig extends WebMvcConfigurerAdapter {
//    @Autowired
//    ThymeleafViewResolver thymeleafViewResolver;
//
//    @Bean
//    @Primary
//    public ObjectMapper jacksonObjectMapper() {
//        // @formatter:off
//        return new ObjectMapper()
//                .configure(SerializationFeature.WRITE_DATES_AS_TIMESTAMPS, false)
//                .configure(SerializationFeature.INDENT_OUTPUT, true)
//                .setDateFormat(new ISO8601DateFormat())
////				.registerModule(new JodaModule()) // add more Module
//				/*  .registerModule(new CmsModule())
//			/*	.registerModule(new PortalModule())	*/
////				.registerModule(new SysModule())
//                ;
//
//    }
//
//    @Bean
//    public MappingJackson2JsonView mappingJackson2JsonView() {
//        MappingJackson2JsonView v = new org.springframework.web.servlet.view.json.MappingJackson2JsonView();
//        v.setObjectMapper(jacksonObjectMapper());
//        v.setPrettyPrint(true);
//        return v;
//    }
//
//    protected class MappingJackson2JsonpView extends MappingJackson2JsonView {
//        public static final String DEFAULT_CONTENT_TYPE = "application/javascript";
//
//        @Override
//        public String getContentType() {
//            return DEFAULT_CONTENT_TYPE;
//        }
//
//        @Override
//        public void render(Map<String, ?> model, HttpServletRequest request,
//                           HttpServletResponse response) throws Exception {
//            Map<String, String[]> params = request.getParameterMap();
//            if (params.containsKey("callback")) {
//                response.getOutputStream().write(
//                        new String(params.get("callback")[0] + "(").getBytes());
//                super.render(model, request, response);
//                response.getOutputStream().write(new String(");").getBytes());
//                response.setContentType(DEFAULT_CONTENT_TYPE);
//            } else {
//                super.render(model, request, response);
//            }
//        }
//    }
//
//    @Bean
//    public MappingJackson2JsonpView mappingJackson2JsonpView() {
//        MappingJackson2JsonpView v = new MappingJackson2JsonpView();
//        v.setObjectMapper(jacksonObjectMapper());
//        v.setPrettyPrint(false);
//        return v;
//    }
//
//    @Override
//    public void configureContentNegotiation(
//            ContentNegotiationConfigurer configurer) {
//        configurer.favorParameter(true).ignoreAcceptHeader(false)
//                .defaultContentType(MediaType.TEXT_HTML)
//                .mediaType("json", MediaType.APPLICATION_JSON)
//                .mediaType("jsonp", MediaType.valueOf("application/javascript"));
//    }
//
//    @Bean
//    public ViewResolver contentNegotiatingViewResolver(
//            ContentNegotiationManager manager) {
//        List<ViewResolver> resolvers = new ArrayList<ViewResolver>();
//
//        resolvers.add(thymeleafViewResolver);
//        // resolvers.add(deviceDelegatingViewResolver());
//        ContentNegotiatingViewResolver resolver = new ContentNegotiatingViewResolver();
//        resolver.setViewResolvers(resolvers);
//        resolver.setContentNegotiationManager(manager);
//
//        List<View> views = new ArrayList<View>();
//        views.add(mappingJackson2JsonView());
//        views.add(mappingJackson2JsonpView());
//        resolver.setDefaultViews(views);
//        return resolver;
//
//    }
//
//    // see
//    // org.springframework.boot.autoconfigure.thymeleaf.ThymeleafAutoConfiguration.ThymeleafDefaultConfiguration
//    @Bean
//    public Collection<IDialect> dialects() {
//        Collection<IDialect> dialects = new HashSet<IDialect>();
//        dialects
//                .add(new org.thymeleaf.extras.springsecurity4.dialect.SpringSecurityDialect());
//        return dialects;
//    }
//
//    @Override
//    public void addViewControllers(ViewControllerRegistry registry) {
//        registry.addViewController("/login").setViewName("login");
//        registry.addViewController("/index").setViewName("index");
//    }
//
//
//}
//
