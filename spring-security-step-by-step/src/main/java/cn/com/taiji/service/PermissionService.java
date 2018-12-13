package cn.com.taiji.service;

import org.springframework.security.access.ConfigAttribute;

import java.util.Collection;
import java.util.Map;

/**
 * Created by iandtop on 2018/12/11.
 */
public interface PermissionService {
    //helloAdmin,  [ROLE_ADMIN,ROLE_USER]
    Map<String, Collection<ConfigAttribute>> getPermissionMap();
}
