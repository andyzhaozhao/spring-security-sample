package cn.com.taiji.support;

import cn.com.taiji.domain.Role;
import cn.com.taiji.domain.UserInfo;
import cn.com.taiji.service.UserInfoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by iandtop on 2018/12/11.
 */
@Component
public class CustomUserDetailService implements UserDetailsService {

    @Autowired
    private UserInfoService userInfoService;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        //username ---> UserDetails
        //1. 根据usernaem从数据库查处UserInfo
        UserInfo userInfo = userInfoService.findByUsername(username);
        if (userInfo == null) {
            throw new UsernameNotFoundException("Not found UserInfo By username=" + username);
        }
        String password = userInfo.getPassword();

        //2.
        List<GrantedAuthority> authorities = new ArrayList();
//        GrantedAuthority authority = new SimpleGrantedAuthority(userInfo.getRole().name());
//        authorities.add(authority);

        //定义权限列表.
        //用户可以访问的资源名称（或者说用户所拥有的权限)
        for(Role role:userInfo.getRoles()) {
            authorities.add(new SimpleGrantedAuthority(role.getName()));
        }

        UserDetails user = new User(username, password, authorities);
        return user;
    }
}
