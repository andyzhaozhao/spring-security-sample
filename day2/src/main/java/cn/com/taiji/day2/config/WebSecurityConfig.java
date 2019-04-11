package cn.com.taiji.day2.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.provisioning.InMemoryUserDetailsManager;
import org.springframework.security.web.authentication.logout.LogoutSuccessHandler;

@EnableWebSecurity(debug = true)
public class WebSecurityConfig extends WebSecurityConfigurerAdapter {

    @Autowired
    private CustomLogoutSuccessHandler customLogoutSuccessHandler;

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http.authorizeRequests()
                // 主要URl匹配的优先级问题，不要配置冲突
                .antMatchers("/login", "/loginP", "/resources/**", "/signup", "/about").permitAll()
                .antMatchers("/admin/**").hasAnyRole("ADMIN", "USER")
                .regexMatchers("^/a$").hasRole("ADMIN")
                .mvcMatchers("/b").hasRole("USER")
                .antMatchers("/admin/*/1/**").hasRole("ADMIN")
                .antMatchers("/db/**").access("hasRole('ADMIN') and hasRole('DBA')")
                .anyRequest().authenticated();

        http.formLogin()
                .loginPage("/loginP")
                .loginProcessingUrl("/login")
                .usernameParameter("un")
                .passwordParameter("pass")
                .successHandler(customAuthenticationSuccessHandler());

        http.logout().logoutUrl("/my/logout")
                .logoutSuccessHandler(customLogoutSuccessHandler)
//                .logoutSuccessUrl("/home")
                .deleteCookies("DAY2SESSIONID");

//        http.csrf().disable();
    }

    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
//        super.configure(auth);
        auth.inMemoryAuthentication().withUser("1").password("1").roles("USER")
                .and().withUser("2").password("2").roles( "ADMIN")
                .and().withUser("3").password("3").roles( "DBA")
                .and().withUser("4").password("4").roles( "ADMIN", "DBA");
    }

    @Override
    public void configure(WebSecurity web) throws Exception {
        super.configure(web);
    }

    @Bean
    public CustomAuthenticationSuccessHandler customAuthenticationSuccessHandler() {
        return new CustomAuthenticationSuccessHandler();
    }


//    @Bean
//    public UserDetailsService userDetailsService() {
//        InMemoryUserDetailsManager manager = new InMemoryUserDetailsManager();
//        manager.createUser(User.withUsername("1").password("1").roles("USER").build());
//        manager.createUser(User.withUsername("2").password("2").roles("ADMIN").build());
//        manager.createUser(User.withUsername("3").password("3").roles("DBA").build());
//        manager.createUser(User.withUsername("4").password("4").roles("ADMIN", "DBA").build());
//        return manager;
//    }
}