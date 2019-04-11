package cn.com.taiji.day3.config;

import cn.com.taiji.day3.domain.Authorities;
import cn.com.taiji.day3.domain.Users;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class CustomAuthenticationProvider implements AuthenticationProvider {

    @Autowired
    private JdbcTemplate jdbcTemplate;

    @Override
    public Authentication authenticate(Authentication authentication) throws AuthenticationException {
        // 1. 查询用户User
        String username = (String) authentication.getPrincipal();
        String password = (String) authentication.getCredentials();

        String sql = "select username,PASSWORD,enabled,phone,email " +
                " from users " +
                " where username='" + username + "' and password='" + password + "' ";
        Users users = jdbcTemplate.queryForObject(sql,
                new BeanPropertyRowMapper<>(Users.class));

        String authoritiesSql = "select a.username,a.authority from users u " +
                " left join authorities a on u.username=a.username " +
                " where u.username='" + username + "' and u.password='" + password + "' ";

        List<Authorities> authoritiesList
                = jdbcTemplate.query(authoritiesSql, new BeanPropertyRowMapper(Authorities.class));

        // 2. 把用户User转换Authentication对象返回
        UsernamePasswordAuthenticationToken result
                = new UsernamePasswordAuthenticationToken(
                users, users.getPassword(), authoritiesList);

        result.setDetails(authentication.getDetails());
        return result;
    }

    @Override
    public boolean supports(Class<?> aClass) {
        return true;
    }
}
