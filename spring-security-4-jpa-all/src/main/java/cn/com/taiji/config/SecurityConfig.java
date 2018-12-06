package cn.com.taiji.config;


import cn.com.taiji.security.*;
import cn.com.taiji.service.RoleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.ProviderManager;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.web.access.intercept.FilterSecurityInterceptor;
import org.springframework.security.web.csrf.CookieCsrfTokenRepository;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;

import java.util.ArrayList;
import java.util.List;

@EnableWebSecurity
public class SecurityConfig extends WebSecurityConfigurerAdapter {

    @Bean
    public TaijiUsernamePasswordAuthenticationFilter loginFilter() throws Exception {
        TaijiUsernamePasswordAuthenticationFilter customUsernamePasswordAuthenticationFilter = new TaijiUsernamePasswordAuthenticationFilter();
        customUsernamePasswordAuthenticationFilter.setAuthenticationSuccessHandler(customSuccessHandler());
        customUsernamePasswordAuthenticationFilter.setAuthenticationFailureHandler(customFailureHandler());
        customUsernamePasswordAuthenticationFilter.setAuthenticationManager(authenticationManagerBean());
        customUsernamePasswordAuthenticationFilter.setFilterProcessesUrl("/j_spring_security_check");
        return customUsernamePasswordAuthenticationFilter;
    }

    @Bean
    public TaijiFailureHandler customFailureHandler() {
        TaijiFailureHandler customFailureHandler = new TaijiFailureHandler();
        customFailureHandler.setDefaultFailureUrl("/login?error");
        return customFailureHandler;
    }

    @Bean
    public TaijiSuccessHandler customSuccessHandler() {
        TaijiSuccessHandler customSuccessHandler = new TaijiSuccessHandler();
        customSuccessHandler.setDefaultTargetUrl("/");
        return customSuccessHandler;
    }

    @Bean
    @Override
    public AuthenticationManager authenticationManagerBean() throws Exception {
        List<AuthenticationProvider> authenticationProviderList = new ArrayList<AuthenticationProvider>();
        authenticationProviderList.add(customAuthenticationProvider());
        AuthenticationManager authenticationManager = new ProviderManager(authenticationProviderList);
        return authenticationManager;
    }

    @Autowired
    public TaijiUserDetailServiceImpl userDetailsService;

    @Bean
    public TaijiAuthenticationProvider customAuthenticationProvider() {
        TaijiAuthenticationProvider customAuthenticationProvider = new TaijiAuthenticationProvider();
        customAuthenticationProvider.setUserDetailsService(userDetailsService);
        return customAuthenticationProvider;
    }

    @Autowired
    private RoleService roleService;

    @Bean
    public TaijiSecurityMetadataSource taijiSecurityMetadataSource() {
        TaijiSecurityMetadataSource fisMetadataSource = new TaijiSecurityMetadataSource();
        fisMetadataSource.setRoleService(roleService);
        return fisMetadataSource;
    }

    @Autowired
    private TaijiAccessDecisionManager accessDecisionManager;

    @Bean
    public TaijiFilterSecurityInterceptor taijifiltersecurityinterceptor() throws Exception {
        TaijiFilterSecurityInterceptor taijifiltersecurityinterceptor = new TaijiFilterSecurityInterceptor();
        taijifiltersecurityinterceptor.setFisMetadataSource(taijiSecurityMetadataSource());
        taijifiltersecurityinterceptor.setAccessDecisionManager(accessDecisionManager);
        taijifiltersecurityinterceptor.setAuthenticationManager(authenticationManagerBean());
        return taijifiltersecurityinterceptor;
    }

    @Bean
    public MyAccessDeniedHandler accessDeniedHandler() {
        MyAccessDeniedHandler access = new MyAccessDeniedHandler();
        access.setErrorPage("403");
        return access;
    }

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        // @formatter:off
        http.headers().frameOptions().disable();
        http.authorizeRequests().filterSecurityInterceptorOncePerRequest(true)// 过滤器的安全拦截器的每一次的要求
                .antMatchers("/403").permitAll() // for test
                .antMatchers("/login").permitAll() // for login
                .antMatchers("/image").permitAll() // for login
                .antMatchers("/j_spring_security_check").permitAll()
                // access-denied-page
                .anyRequest().fullyAuthenticated() // .accessDecisionManager(accessDecisionManager)
                // // all others need login
                .and().formLogin().loginPage("/login").failureUrl("/login?error").successForwardUrl("/index") // login
                // config
                .and().logout().logoutRequestMatcher(new AntPathRequestMatcher("/logout")) // logout
                // config
                .and().exceptionHandling().accessDeniedHandler(accessDeniedHandler());
        http.addFilterAfter(taijifiltersecurityinterceptor(), FilterSecurityInterceptor.class)
                .addFilter(loginFilter()).rememberMe().key("logintoken")
                .and().csrf().csrfTokenRepository(CookieCsrfTokenRepository.withHttpOnlyFalse());


    }

    @Override
    public void configure(WebSecurity web) throws Exception {
        // 解决静态资源被拦截的问题
        web.ignoring().antMatchers("/theme/**","/css/**");
    }

    @Bean
    @Override
    protected AuthenticationManager authenticationManager() throws Exception {
        return super.authenticationManager();
    }

    @Autowired
    public void configureGlobalSecurity(AuthenticationManagerBuilder auth) throws Exception {
        auth.userDetailsService(userDetailsService);
    }

}
