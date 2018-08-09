package cn.com.taiji.third.config;

import cn.com.taiji.third.security.CustomFilterSecurityInterceptor;
import cn.com.taiji.third.security.CustomUserService;
import org.apache.commons.codec.digest.DigestUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.access.intercept.FilterSecurityInterceptor;

@Configuration
@EnableWebSecurity
public class SecurityConfig extends WebSecurityConfigurerAdapter {
    @Autowired
    private CustomFilterSecurityInterceptor customFilterSecurityInterceptor;

    @Bean
    UserDetailsService customUserService() {
        //注册UserDetailsService 的bean
        return new CustomUserService();
    }

    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
        auth.userDetailsService(customUserService()).passwordEncoder(new PasswordEncoder() {

            @Override
            public String encode(CharSequence rawPassword) {
                String encodePass = DigestUtils.sha256Hex((String) rawPassword);
                return encodePass;
            }

            @Override
            public boolean matches(CharSequence rawPassword, String encodedPassword) {
                //比较用户输入密码与数据库密码是否相等
                Boolean isEqual = encodedPassword.equals(DigestUtils.sha256Hex((String) rawPassword));
                return isEqual;
            }
        }); //user Details Service验证
    }

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http.authorizeRequests()
                .antMatchers("/css/**").permitAll()  // 都可以访问
                .anyRequest().authenticated() //任何请求,登录后可以访问
                .and().formLogin().loginPage("/login").failureUrl("/login?error").permitAll() //登录页面用户任意访问
                .and().logout().permitAll()//注销行为任意访问
                .and().addFilterBefore(customFilterSecurityInterceptor, FilterSecurityInterceptor.class);
    }
}

