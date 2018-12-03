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
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.access.intercept.FilterSecurityInterceptor;

@Configuration
@EnableWebSecurity
public class SecurityConfig extends WebSecurityConfigurerAdapter {
    @Autowired
    private CustomFilterSecurityInterceptor customFilterSecurityInterceptor;

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http.authorizeRequests()
                .antMatchers("/css/**", "/js/**", "/fonts/**", "/login").permitAll()
                .anyRequest().authenticated()
                .and().formLogin()
                .loginPage("/login")
                .failureUrl("/login?error").permitAll()
                //注销行为任意访问
                .and().logout().permitAll()
                .and().addFilterBefore(customFilterSecurityInterceptor, FilterSecurityInterceptor.class);
    }

    @Autowired
    private CustomUserService customUserService ;

    /**
     * 认证信息管理
     *
     * @param builder
     * @throws Exception
     */
    @Autowired
    public void configureGlobal(AuthenticationManagerBuilder builder) throws Exception {
        builder.userDetailsService(customUserService).passwordEncoder(passwordEncoder());
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

}

