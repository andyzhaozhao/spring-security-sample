//package cn.com.taiji.support;
//
//import org.slf4j.Logger;
//import org.slf4j.LoggerFactory;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.jdbc.core.JdbcTemplate;
//import org.springframework.security.core.GrantedAuthority;
//import org.springframework.security.core.authority.SimpleGrantedAuthority;
//import org.springframework.security.core.userdetails.User;
//import org.springframework.security.core.userdetails.UserDetails;
//import org.springframework.security.core.userdetails.UserDetailsService;
//import org.springframework.security.core.userdetails.UsernameNotFoundException;
//import org.springframework.security.crypto.password.PasswordEncoder;
//import org.springframework.stereotype.Service;
//
//import java.util.ArrayList;
//import java.util.LinkedHashMap;
//import java.util.List;
//import java.util.Map;
//
//@Service
//public class CustomUserDetailsService implements UserDetailsService {
//
//    private static final Logger logger = LoggerFactory.getLogger(CustomUserDetailsService.class);
//
//    @Autowired
//    private JdbcTemplate jdbcTemplate;
//
//    @Autowired
//    private PasswordEncoder passwordEncoder;
//
//    /**
//     * 授权的时候是对角色授权，而认证的时候应该基于资源，而不是角色，因为资源是不变的，而用户的角色是会变的
//     */
//    @Override
//    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
//        Map<String, String> sysUser = getUser(username);
//        if (null == sysUser) {
//            throw new UsernameNotFoundException(username);
//        }
//
////        logger.info(passwordEncoder.encode(sysUser.get("password")));
//
//        List<GrantedAuthority> grantedAuthorities = new ArrayList<>();
//        grantedAuthorities.add(new SimpleGrantedAuthority("ROLE_ADMIN"));
//        grantedAuthorities.add(new SimpleGrantedAuthority("ROLE_USER"));
//        return new User(sysUser.get("name"), sysUser.get("password"), grantedAuthorities);
//    }
//
//    public Map<String, String> getUser(String username) {
//        String sql = "select * " + sqlFrom(username);
//        Map<String, Object> user = jdbcTemplate.queryForMap(sql);
//        Map<String, String> newMap = new LinkedHashMap<>();
//
//        newMap.put("name", username);
//        newMap.put("password", (String) user.get("password"));
//        return newMap;
//    }
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
