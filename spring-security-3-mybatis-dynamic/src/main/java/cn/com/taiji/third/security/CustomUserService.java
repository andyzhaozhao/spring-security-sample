package cn.com.taiji.third.security;

import cn.com.taiji.third.dao.PermissionDao;
import cn.com.taiji.third.dao.UserDao;
import cn.com.taiji.third.domain.Permission;
import cn.com.taiji.third.domain.Role;
import cn.com.taiji.third.domain.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

/**
 * 自定义UserDetailsService 接口
 */
@Service
public class CustomUserService implements UserDetailsService {

    @Autowired
    private UserDao userDao;

    @Autowired
    private PermissionDao permissionDao;

    @Override
    public UserDetails loadUserByUsername(String username) {
        //重写loadUserByUsername 方法获得 userdetails 类型用户
        User user = userDao.findByUserName(username);
        if (user == null) {
            throw new UsernameNotFoundException("用户名不存在");
        }

        List<Permission> permissions = permissionDao.findByAdminUserId(user.getId());
        List<GrantedAuthority> grantedAuthorities = new ArrayList<>();
        for (Permission permission : permissions) {
            if (permission != null && permission.getPermission_name() != null) {

                GrantedAuthority grantedAuthority = new SimpleGrantedAuthority(permission.getPermission_name());
                grantedAuthorities.add(grantedAuthority);
            }
        }
        return new org.springframework.security.core.userdetails.User
                (user.getLogin_name(), user.getPassword(), grantedAuthorities);

    }

}
