package cn.com.taiji.day1.config;

import cn.com.taiji.day1.filters.AccessControlFilter;
import cn.com.taiji.day1.filters.HttpBasicAuthenticationFilter;
import cn.com.taiji.day1.filters.LoginFilter;
import cn.com.taiji.day1.filters.LogoutFilter;
import org.springframework.boot.web.servlet.FilterRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.annotation.Order;

import javax.servlet.Filter;

@Configuration
public class FIlterConfigurer {

    @Bean
    @Order(1)
    public FilterRegistrationBean logoutFilterRegistration() {
        FilterRegistrationBean registration = new FilterRegistrationBean();
        registration.setFilter(logoutFilter());
        registration.addUrlPatterns("/*");
        registration.setName("logoutFilter");
        return registration;
    }

    @Bean
    @Order(2)
    public FilterRegistrationBean httpBasicAuthenticationFilterRegistration() {
        FilterRegistrationBean registration = new FilterRegistrationBean();
        registration.setFilter(httpBasicAuthenticationFilter());
        registration.addUrlPatterns("/*");
        registration.setName("httpBasicAuthenticationFilter");
        return registration;
    }

    /**
     * 配置过滤器
     *
     * @return
     */
    @Bean
    @Order(3)
    public FilterRegistrationBean someFilterRegistration() {
        FilterRegistrationBean registration = new FilterRegistrationBean();
        registration.setFilter(loginFilter());
//        registration.addUrlPatterns("/*");
        registration.addUrlPatterns("/a", "/b");
        registration.setName("loginFilter");
//        registration.setOrder(1);
        return registration;
    }

    @Bean
    @Order(4)
    public FilterRegistrationBean accessControlFilterRegistration() {
        FilterRegistrationBean registration = new FilterRegistrationBean();
        registration.setFilter(accessControlFilter());
        registration.addUrlPatterns("/a", "/b");
        registration.setName("accessControlFilter");
        return registration;
    }

    /**
     * 创建一个bean
     *
     * @return
     */
    @Bean(name = "loginFilter")
    public Filter loginFilter() {
        return new LoginFilter();
    }

    @Bean(name = "accessControlFilter")
    public Filter accessControlFilter() {
        return new AccessControlFilter();
    }

    @Bean
    public Filter logoutFilter() {
        return new LogoutFilter();
    }

    @Bean
    public HttpBasicAuthenticationFilter httpBasicAuthenticationFilter() {
        return new HttpBasicAuthenticationFilter();
    }

}