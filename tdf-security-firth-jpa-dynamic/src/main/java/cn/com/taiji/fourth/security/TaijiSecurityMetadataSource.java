package cn.com.taiji.fourth.security;

import cn.com.taiji.fourth.domain.Menu;
import cn.com.taiji.fourth.domain.Role;
import cn.com.taiji.fourth.repository.MenuRepository;
import cn.com.taiji.fourth.service.RoleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.ConfigAttribute;
import org.springframework.security.access.SecurityConfig;
import org.springframework.security.web.FilterInvocation;
import org.springframework.security.web.access.intercept.FilterInvocationSecurityMetadataSource;
import org.springframework.stereotype.Component;
import org.thymeleaf.util.ListUtils;
import org.thymeleaf.util.MapUtils;

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
        if (!MapUtils.isEmpty(resourceMap) && !refresh) {
            return;
        }

        resourceMap = new HashMap();
        List<Menu> menus = this.menuRepository.findAllMenus();
        if (ListUtils.isEmpty(menus)) {
            return;
        }

        // 以权限名封装为Spring的security Object
        for (Menu menu : menus) {
            List<Role> roles = menu.getRoles();
            if (ListUtils.isEmpty(roles)) {
                //role没有与menu对应，设置为"null"，在AccessDecisionManager做验证时，保证
                //所有未配置角色的menu不可访问
                loadResouce(menu, "null");
            } else {
                for (Role role : roles) {
                    loadResouce(menu, role.getId());
                }
            }
        }
    }

    private void loadResouce(Menu menu, String roleId) {
        String key = menu.getMenuUrl() != null ? menu.getMenuUrl() : "";
        if (!key.startsWith("/")) {
            key = "/" + key;
        }

        List<ConfigAttribute> configAttributes = new ArrayList();
        ConfigAttribute configAttribute = new SecurityConfig(roleId);
        configAttributes.add(configAttribute);
        // 获得已有资源，添加
        if (resourceMap.containsKey(key) && !resourceMap.get(key).contains(configAttribute)) {
            resourceMap.get(key).add(configAttribute);
        } else {
            resourceMap.put(key, configAttributes);
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
