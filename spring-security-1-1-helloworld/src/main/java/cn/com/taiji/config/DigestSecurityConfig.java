//package cn.com.taiji.config;
//
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.context.annotation.Bean;
//import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
//import org.springframework.security.config.annotation.web.builders.HttpSecurity;
//import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
//import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
//import org.springframework.security.config.http.SessionCreationPolicy;
//import org.springframework.security.core.userdetails.User;
//import org.springframework.security.core.userdetails.UserDetailsService;
//import org.springframework.security.provisioning.InMemoryUserDetailsManager;
//import org.springframework.security.web.authentication.www.DigestAuthenticationEntryPoint;
//import org.springframework.security.web.authentication.www.DigestAuthenticationFilter;
//
//@EnableWebSecurity
//public class DigestSecurityConfig extends WebSecurityConfigurerAdapter {
//
//    public final static String REALM = "MY_REALM";
//
//    @Override
//    protected void configure(HttpSecurity http) throws Exception {
//        http.csrf().disable()
//                .authorizeRequests()
//                .anyRequest().authenticated()
//                .and().sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS)
//                .and().exceptionHandling().authenticationEntryPoint(getDigestEntryPoint())
//                .and().addFilter(getDigestAuthenticationFilter());
//    }
//
//    /**
//     * 用户信息服务
//     */
//    @Bean
//    public UserDetailsService userDetailsService() {
//        InMemoryUserDetailsManager manager = new InMemoryUserDetailsManager(); // 在内存中存放用户信息
//        manager.createUser(User.withUsername("user").password("123456").roles("USER").build());
//        manager.createUser(User.withUsername("admin").password("123456").roles("USER", "ADMIN").build());
//        return manager;
//    }
//
//    /**
//     * 认证信息管理
//     *
//     * @param auth
//     * @throws Exception
//     */
//    @Autowired
//    public void configureGlobal(AuthenticationManagerBuilder auth) throws Exception {
//        auth.userDetailsService(userDetailsService());
//    }
//
//    @Bean
//    public DigestAuthenticationEntryPoint getDigestEntryPoint() {
//        DigestAuthenticationEntryPoint digestAuthenticationEntryPoint = new DigestAuthenticationEntryPoint();
//        digestAuthenticationEntryPoint.setKey("mykey");
//        digestAuthenticationEntryPoint.setNonceValiditySeconds(120);
//        digestAuthenticationEntryPoint.setRealmName(REALM);
//        return digestAuthenticationEntryPoint;
//    }
//
//    @Bean
//    public DigestAuthenticationFilter getDigestAuthenticationFilter() throws Exception {
//        DigestAuthenticationFilter digestAuthenticationFilter = new DigestAuthenticationFilter();
//        digestAuthenticationFilter.setAuthenticationEntryPoint(getDigestEntryPoint());
//        digestAuthenticationFilter.setUserDetailsService(userDetailsServiceBean());
//        return digestAuthenticationFilter;
//    }
//}
