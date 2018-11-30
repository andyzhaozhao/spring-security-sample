package cn.com.taiji.config;

import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;

/**
 * Spring Security 配置类.
 */
@EnableWebSecurity
public class SimpleExceptionSecurityConfig extends WebSecurityConfigurerAdapter {

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http.authorizeRequests()
                // 都可以访问
                .antMatchers("/css/**", "/js/**", "/fonts/**", "/index").permitAll()
                .antMatchers("/login").permitAll()
                // 需要相应的角色才能访问
                .antMatchers("/users/**").hasRole("USER")
                // 需要相应的角色才能访问
                .antMatchers("/admins/**").hasAnyRole("ADMIN")
                .anyRequest().authenticated()
                .and()
                // 自定义登录界面;
                .formLogin().loginPage("/login").failureUrl("/login-error")
                .and()
                // 处理异常，拒绝访问就重定向到 403 页面
                .exceptionHandling().accessDeniedPage("/403");
    }

    @Override
    public void configure(WebSecurity web) throws Exception {
        super.configure(web);

    }

    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
//        super.configure(auth);
        auth.inMemoryAuthentication()
                .withUser("user").password("123456").roles("USER")
                .and()
                .withUser("admin").password("123456").roles("USER", "ADMIN");
    }
}
