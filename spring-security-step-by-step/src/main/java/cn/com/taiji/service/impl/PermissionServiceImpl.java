package cn.com.taiji.service.impl;

import cn.com.taiji.domain.Permission;
import cn.com.taiji.domain.Role;
import cn.com.taiji.repository.PermissionRepository;
import cn.com.taiji.service.PermissionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.ConfigAttribute;
import org.springframework.security.access.SecurityConfig;
import org.springframework.stereotype.Service;

import javax.annotation.PostConstruct;
import java.util.*;

/**
 * Created by iandtop on 2018/12/11.
 */
@Service
public class PermissionServiceImpl implements PermissionService {
    private Map<String, Collection<ConfigAttribute>> permissionMap = null;

    @Autowired
    private PermissionRepository permissionRepository;

    @Override
    public Map<String, Collection<ConfigAttribute>> getPermissionMap() {
        if (permissionMap == null || permissionMap.size() == 0) {
            initPermissions();
        }

        return permissionMap;
    }

    @PostConstruct
    public void initPermissions() {
        //程序启动后，立即自动 执行
        //初始化permissionMap
        //key =  ulr
        //value = [role1,role2] ;
        permissionMap = new HashMap();
        List<Permission> permissions = permissionRepository.findAll();
        for (Permission p : permissions) {
            Collection<ConfigAttribute> collection = new ArrayList<ConfigAttribute>();
            for (Role role : p.getRoles()) {
                ConfigAttribute cfg = new SecurityConfig(role.getName());
                collection.add(cfg);
            }
            permissionMap.put(p.getUrl(), collection);
        }

    }
}
