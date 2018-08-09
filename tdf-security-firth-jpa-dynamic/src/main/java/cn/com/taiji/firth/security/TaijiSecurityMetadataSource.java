package cn.com.taiji.firth.security;

import cn.com.taiji.firth.domain.Menu;
import cn.com.taiji.firth.domain.Role;
import cn.com.taiji.firth.repository.MenuRepository;
import cn.com.taiji.firth.service.RoleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.ConfigAttribute;
import org.springframework.security.access.SecurityConfig;
import org.springframework.security.web.FilterInvocation;
import org.springframework.security.web.access.intercept.FilterInvocationSecurityMetadataSource;
import org.springframework.stereotype.Component;

import javax.servlet.http.HttpSession;
import java.util.*;

/**
 * @description 资源源数据定义，将所有的资源和权限对应关系建立起来，即定义某一资源可以被哪些角色访问
 * 此类在初始化时，应该取到所有资源及其对应角色的定义
 */
@Component
public class TaijiSecurityMetadataSource implements FilterInvocationSecurityMetadataSource {
    @Autowired
    private RoleService roleService;
    @Autowired
    private MenuRepository menuRepository;
    @Autowired
    HttpSession session;


    /* 保存资源和权限的对应关系 key-资源url value-权限 */
    private Map<String, Collection<ConfigAttribute>> resourceMap = null;

    public RoleService getRoleService() {
        return roleService;
    }

    public void setRoleService(RoleService roleService) {
        this.roleService = roleService;
    }

    public TaijiSecurityMetadataSource() {
        // loadResourceDefine();
    }

    public Collection<ConfigAttribute> getAllConfigAttributes() {
        return null;
    }

    // 加载所有资源与权限的关系
    public void loadResourceDefine(boolean refresh) {
        if (resourceMap == null || resourceMap.isEmpty() || refresh) {
            resourceMap = new HashMap<String, Collection<ConfigAttribute>>();
            List<Menu> menus = this.menuRepository.findAll();
            if (!menus.isEmpty()) {
                // 以权限名封装为Spring的security Object
                for (Menu menu : menus) {
                    List<Role> list = menu.getRoles();
                    if (list != null && list.size() > 0) {
                        for (Role role : list) {
                            Collection<ConfigAttribute> configAttributes = new ArrayList<ConfigAttribute>();
                            ConfigAttribute configAttribute = new SecurityConfig(role.getId());
                            configAttributes.add(configAttribute);
                            // 获得已有资源，添加
                            if (!resourceMap.isEmpty()) {
                                if (menu.getMenuUrl() != null && !menu.getMenuUrl().isEmpty()) {
                                    Collection<ConfigAttribute> configAttribute_old = resourceMap.get("/" + menu.getMenuUrl());
                                    if ("/".equals(menu.getMenuUrl())) {
                                        configAttribute_old = resourceMap.get(menu.getMenuUrl());
                                    }
                                    if (configAttribute_old != null) {
                                        configAttributes.addAll(configAttribute_old);
                                    }
                                }
                            }
                            if (menu.getMenuUrl() != null && !menu.getMenuUrl().isEmpty()) {
                                if (menu.getMenuUrl().startsWith("/")) {
                                    resourceMap.put(menu.getMenuUrl(), configAttributes);
                                } else {
                                    resourceMap.put("/" + menu.getMenuUrl(), configAttributes);
                                }
                            }
                        }
                    }
                }
            }
        }
    }

    // 返回所请求资源所需要的权限
    public Collection<ConfigAttribute> getAttributes(Object object) throws IllegalArgumentException {
        String requestUrl = ((FilterInvocation) object).getRequestUrl();
        if (resourceMap == null || resourceMap.isEmpty()) {
            loadResourceDefine(false);
        }
        return resourceMap.get(requestUrl);
    }

    public boolean supports(Class<?> arg0) {
        loadResourceDefine(false);
        return true;
    }
}
