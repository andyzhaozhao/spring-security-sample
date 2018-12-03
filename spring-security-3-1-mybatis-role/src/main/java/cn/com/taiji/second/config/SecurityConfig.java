package cn.com.taiji.second.config;

import cn.com.taiji.second.security.CustomUserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

@Configuration
@EnableWebSecurity
@EnableGlobalMethodSecurity(securedEnabled = true)
public class SecurityConfig extends WebSecurityConfigurerAdapter {

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http.authorizeRequests()
                .antMatchers("/css/**", "/js/**", "/fonts/**", "/login").permitAll()
                .antMatchers("/users/**").hasRole("USER")
                .antMatchers("/admins/**").hasRole("ADMIN")
//                .antMatchers(HttpMethod.GET).permitAll()
                .anyRequest().authenticated() //任何请求,登录后可以访问
                .and()
                .formLogin()
                .loginPage("/login")
                .failureUrl("/login?error")
                .permitAll() //登录页面用户任意访问
                .and()
                .logout().permitAll(); //注销行为任意访问
    }

    @Autowired
    private CustomUserService customUserService;

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

