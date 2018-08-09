package cn.com.taiji.firth.security;

import cn.com.taiji.firth.dto.RoleDTO;
import cn.com.taiji.firth.dto.UserDTO;
import cn.com.taiji.firth.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Component;

import java.util.Collection;
import java.util.HashSet;
import java.util.Set;

/**
 * 该类的主要作用是为Spring Security提供一个经过用户认证后的UserDetails。
 * 该UserDetails包括用户名、密码、是否可用、是否过期等信息。
 */
@Component
public class TaijiUserDetailServiceImpl implements UserDetailsService {
    @Autowired
    private UserService userService;

    // 登录验证
    public UserDetails loadUserByUsername(String loginName)
            throws UsernameNotFoundException {
        /** 连接数据库根据登陆？？用户名称获得用户信息 */
        UserDTO user = userService.findByLoginName(loginName);
        if (user == null) {
            throw new UsernameNotFoundException(loginName);
        } else {
            if (!"1".equals(user.getState())) {
                throw new UsernameNotFoundException("该用户处于锁定状态");
            }
        }

        Collection<GrantedAuthority> grantedAuths = obtionGrantedAuthorities(user);

        boolean enables = true; // 是否可用
        boolean accountNonExpired = true; // 是否过期
        boolean credentialsNonExpired = true;
        boolean accountNonLocked = true;
        // 封装成spring security的user
        User userdetail = new User(user.getUserName(), user.getPassword(),
                enables, accountNonExpired, credentialsNonExpired,
                accountNonLocked, grantedAuths);
        return userdetail;
    }

    // 取得用户的权限
    private Set<GrantedAuthority> obtionGrantedAuthorities(UserDTO users) {
        Set<GrantedAuthority> authSet = new HashSet<GrantedAuthority>();
        // 获取用户角色
        Set<RoleDTO> roles = users.getRoles();
        if (null != roles && !roles.isEmpty())
            for (RoleDTO role : roles) {
                authSet.add(new SimpleGrantedAuthority(role.getId()));
            }
        return authSet;
    }
}
