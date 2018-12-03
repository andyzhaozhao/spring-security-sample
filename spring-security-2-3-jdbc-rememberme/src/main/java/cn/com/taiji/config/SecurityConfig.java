//package cn.com.taiji.config;//package cn.com.taiji.config;
//
//import cn.com.taiji.support.CustomUserDetailsService;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.context.annotation.Bean;
//import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
//import org.springframework.security.config.annotation.web.builders.HttpSecurity;
//import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
//import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
//import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
//import org.springframework.security.crypto.password.PasswordEncoder;
//
////import org.springframework.jdbc.datasource.embedded.EmbeddedDatabaseBuilder;
////import org.springframework.jdbc.datasource.embedded.EmbeddedDatabaseType;
//
///**
// * Spring Security 配置类.
// */
//@EnableWebSecurity
//public class SecurityConfig extends WebSecurityConfigurerAdapter {
//
//    /**
//     * 自定义配置
//     */
//    @Override
//    protected void configure(HttpSecurity http) throws Exception {
//        http.authorizeRequests()
//                .antMatchers("/css/**", "/js/**", "/fonts/**", "/login").permitAll()  // 都可以访问
//                .antMatchers("/users/**").hasRole("USER")   // 需要相应的角色才能访问
//                .antMatchers("/admins/**").hasRole("ADMIN")   // 需要相应的角色才能访问
//                .anyRequest().authenticated()
//                .and()
//                .formLogin()   //基于 Form 表单登录验证
//                .loginPage("/login").failureUrl("/login-error")
//                .and().rememberMe()
//                .rememberMeCookieName("custom-app-remember-me")
//                .tokenValiditySeconds(24 * 60 * 60);
//    }
//
//    @Autowired
//    private CustomUserDetailsService userDetailsService;
//
//    /**
//     * 认证信息管理
//     *
//     * @param builder
//     * @throws Exception
//     */
//    @Autowired
//    public void configureGlobal(AuthenticationManagerBuilder builder) throws Exception {
//        builder.userDetailsService(userDetailsService).passwordEncoder(passwordEncoder());
//    }
//
//    @Bean
//    public PasswordEncoder passwordEncoder() {
//        return new BCryptPasswordEncoder();
//    }
//}
