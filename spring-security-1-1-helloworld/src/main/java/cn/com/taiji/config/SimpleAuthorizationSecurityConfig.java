//package cn.com.taiji.config;
//
//import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
//import org.springframework.security.config.annotation.web.builders.HttpSecurity;
//import org.springframework.security.config.annotation.web.builders.WebSecurity;
//import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
//import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
//
///**
// * Spring Security 配置类.
// */
//@EnableWebSecurity
//public class SimpleAuthorizationSecurityConfig extends WebSecurityConfigurerAdapter {
//
//    @Override
//    protected void configure(HttpSecurity http) throws Exception {
//        http.authorizeRequests()
//                // 都可以访问
//                .antMatchers("/css/**", "/js/**", "/fonts/**", "/index").permitAll()
//                .antMatchers("/login").permitAll()
//                .antMatchers("/users/**").hasRole("USER")   // 需要相应的角色才能访问
//                .antMatchers("/admins/**").hasAnyRole("ADMIN")  // 需要相应的角色才能访问
//                //错误案例1：同样的"/admins/**"后一个生效
////                .antMatchers("/admins/**").hasAuthority("fooAuthority")   //用户fooAuthority权限的角色也能访问
//                //正确案例：
////                .antMatchers("/admins/**").access("hasRole('ADMIN') or hasAuthority('fooAuthority')")
//                .antMatchers("/admins/**").hasIpAddress("127.0.0.1")
//                .anyRequest().authenticated()
//                .and()
//                .formLogin().loginPage("/login");
//
//    }
//
//    @Override
//    public void configure(WebSecurity web) throws Exception {
//        super.configure(web);
//    }
//
//    @Override
//    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
////        super.configure(auth);
//        auth.inMemoryAuthentication()
//                .withUser("user").password("123456").roles("USER")
//                .and()
//                .withUser("admin").password("123456").roles("USER", "ADMIN")
//                .and()
//                .withUser("foo").password("123456").authorities("fooAuthority");
//    }
//}
