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
//public class SimpleCustomLoginPageSecurityConfig extends WebSecurityConfigurerAdapter {
//
//    /**
//     * 自定义配置
//     */
//    @Override
//    protected void configure(HttpSecurity http) throws Exception {
//        //错误案例1: 导致父类配置覆盖了子类配置
////        super.configure(http);
//
//        //错误案例2，只配置登录相关，spring security默认没有保护所有url，所以不会跳转到登录页面
////        http.formLogin().loginPage("/login");
//
//        //错误案例3，把/login也给保护起来了，导致死循环：/index无权限-》重定向到/login-》/login也无权限-》重定向到/login
////      //http.authorizeRequests()方法有很多子方法，每个子匹配器将会按照声明的顺序起作用。 
////    	http.authorizeRequests()
////                .anyRequest().authenticated()
////                .and()
////                .formLogin().loginPage("/login");
//
//        //错误案例4，导致静态资源也被保护起来了，怎么解决呢
////        http.authorizeRequests()
////                .antMatchers("/login").permitAll()
////                .anyRequest().authenticated()
////                .and()
////                .formLogin().loginPage("/login");
//
//        //正确配置
//        http.authorizeRequests()
//                // 都可以访问
//                .antMatchers("/css/**", "/js/**", "/fonts/**").permitAll()
//                .antMatchers("/login").permitAll()
//                .anyRequest().authenticated()
//                .and()
//                .formLogin().loginPage("/login");
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
//                .withUser("admin").password("123456").roles("USER", "ADMIN");
//    }
//}
