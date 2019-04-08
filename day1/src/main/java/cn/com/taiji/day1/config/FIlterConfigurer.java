package cn.com.taiji.day1.config;

import cn.com.taiji.day1.filters.MyFilter;
import org.springframework.boot.web.servlet.FilterRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import javax.servlet.Filter;

@Configuration
public class FIlterConfigurer {

    /**
     * 配置过滤器
     *
     * @return
     */
    @Bean
    public FilterRegistrationBean someFilterRegistration() {
        FilterRegistrationBean registration = new FilterRegistrationBean();
        registration.setFilter(myFilter());
        registration.addUrlPatterns("/**");
//        registration.addUrlPatterns("/a");
        registration.setName("myFilter");
        return registration;
    }

    /**
     * 创建一个bean
     *
     * @return
     */
    @Bean(name = "myFilter")
    public Filter myFilter() {
        return new MyFilter();
    }

}