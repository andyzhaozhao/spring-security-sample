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
//public class SimpleAddUserSecurityConfig extends WebSecurityConfigurerAdapter {
//
//    /**
//     * 自定义配置
//     */
//    @Override
//    protected void configure(HttpSecurity http) throws Exception {
//        super.configure(http);
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
