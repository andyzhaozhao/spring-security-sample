package cn.com.taiji.day3.service;

import cn.com.taiji.day3.domain.Authorities;
import cn.com.taiji.day3.domain.Permissions;
import cn.com.taiji.day3.domain.Users;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Component;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

@Component
public class CustomAccessControlService {
    @Autowired
    private JdbcTemplate jdbcTemplate;

    public Boolean canAccess(HttpServletRequest request,
                           Authentication authentication){
        //TODO 具体RBAC 鉴权逻辑 代码的位置
        // 1/未登录的情况下，需要做一个判断或者是拦截。 anonymousUser | userDetails.user
        Object principal = authentication.getPrincipal();
        if ("anonymousUser".equals(principal)) {
            return false;
        }

        // who 是谁:authentication
        // what  哪一个url  ： request
        // how 哪些权限：
        String username = ((Users)authentication.getPrincipal()).getUsername();
        String path = request.getServletPath();

        // how 用户具体有哪些权限
        String sql = "select p.url,p.authority from users u" +
                " left join authorities a on u.username=a.username " +
                " left join permissions p on p.authority=a.authority " +
                " where u.username='" + username + "'";

        List<Permissions> authoritiesList
                = jdbcTemplate.query(sql, new BeanPropertyRowMapper(Permissions.class));

        Boolean hasPermission = false;
        for (Permissions permissions : authoritiesList){
            if(path.equals(permissions.getUrl())){
                // 如果path在权限列表中，说明有权限访问
                hasPermission = true;
                break;
            }
        }

        return hasPermission;

        // 2/匿名的角色 ROLE_ANONYMOUS

        // 3 通过的request对象url）获取到权限信息。

        // 4/ 将获取到的权限信息和当前的登录账号的权限信息进行比对。
    }
}
