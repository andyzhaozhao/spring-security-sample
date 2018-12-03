//package cn.com.taiji.support;
//
//import org.slf4j.Logger;
//import org.slf4j.LoggerFactory;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.jdbc.core.JdbcTemplate;
//import org.springframework.security.authentication.AuthenticationProvider;
//import org.springframework.security.authentication.AuthenticationServiceException;
//import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
//import org.springframework.security.core.Authentication;
//import org.springframework.security.core.AuthenticationException;
//import org.springframework.security.core.GrantedAuthority;
//import org.springframework.security.core.authority.SimpleGrantedAuthority;
//import org.springframework.stereotype.Component;
//import org.thymeleaf.util.StringUtils;
//
//import java.util.ArrayList;
//import java.util.LinkedHashMap;
//import java.util.List;
//import java.util.Map;
//
///**
// * 外接数据库用户源
// */
//@Component
//public class CustomAuthenticationProvider implements AuthenticationProvider {
//    private static final Logger log = LoggerFactory.getLogger(CustomAuthenticationProvider.class);
//
//    @Autowired
//    private JdbcTemplate jdbcTemplate;
//
//    @Override
//    public Authentication authenticate(Authentication authentication) throws AuthenticationException {
//        String username = authentication.getName();
//        String password = (String) authentication.getCredentials();
//
//        if (StringUtils.isEmpty(username)) {
//            throw new AuthenticationServiceException("User name can't be null");
//        }
//
//        Long num = getUserCount(username, password);
//
//        if (num < 1) {
//            throw new AuthenticationServiceException("登录名称或密码不正确，请重新输入");
//        }
//
//        List<GrantedAuthority> grantedAuthorities = new ArrayList<>();
//        grantedAuthorities.add(new SimpleGrantedAuthority("ROLE_ADMIN"));
//        grantedAuthorities.add(new SimpleGrantedAuthority("ROLE_USER"));
//        Authentication auth = new UsernamePasswordAuthenticationToken(username, password, grantedAuthorities);
//        return auth;
//    }
//
//    @Override
//    public boolean supports(Class<?> aClass) {
//        return true;
//    }
//
//    public Long getUserCount(String username, String password) {
//        String sql = "select count(*) " + sqlFrom(username, password);
//
//        Long num = (long) jdbcTemplate.queryForObject(sql, Long.class);
//        return num;
//    }
//
//    public Map<String, String> getUser(String username) {
//        String sql = "select * " + sqlFrom(username);
//        Map<String, Object> user = jdbcTemplate.queryForMap(sql);
//        Map<String, String> newMap = new LinkedHashMap<>();
//
//        newMap.put("name", username);
//        newMap.put("email", username);
//        return newMap;
//    }
//
//
//    private String sqlFrom(String username) {
//        return sqlFrom() + " where " + sqlUserNameWhere(username);
//    }
//
//    private String sqlFrom(String username, String password) {
//        return sqlFrom() + " where " + sqlUserNameWhere(username) + " and " + sqlPasswordWhere(password);
//    }
//
//    private String sqlFrom() {
//        return " from user_info";
//    }
//
//    private String sqlUserNameWhere(String username) {
//        return "login_name='" + username + "' ";
//    }
//
//    private String sqlPasswordWhere(String password) {
//        return "password='" + password + "' ";
//    }
//}
