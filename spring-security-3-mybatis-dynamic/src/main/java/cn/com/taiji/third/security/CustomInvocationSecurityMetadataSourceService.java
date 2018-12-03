package cn.com.taiji.third.security;

import cn.com.taiji.third.dao.PermissionDao;
import cn.com.taiji.third.domain.Permission;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.ConfigAttribute;
import org.springframework.security.access.SecurityConfig;
import org.springframework.security.web.FilterInvocation;
import org.springframework.security.web.access.intercept.FilterInvocationSecurityMetadataSource;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpServletRequest;
import java.util.*;

/**
 * Created by yangyibo on 17/1/19.
 */
@Service
public class CustomInvocationSecurityMetadataSourceService implements
        FilterInvocationSecurityMetadataSource {

    @Autowired
    private PermissionDao permissionDao;

    private HashMap<String, Collection<ConfigAttribute>> map = null;

    /**
     * 加载资源，初始化资源变量
     */
    public void loadResourceDefine() {
        map = new HashMap<>();

        List<Permission> permissions = permissionDao.findAll();
        for (Permission permission : permissions) {
            Collection<ConfigAttribute> array = new ArrayList<>();
            ConfigAttribute cfg = new SecurityConfig(permission.getPermission_name());
            array.add(cfg);
            map.put(permission.getUrl(), array);
        }
    }

    /**
     * 方法返回本次访问需要的权限，可以有多个权限。在上面的实现中如果没有匹配的url直接返回null
     * www也就是没有配置权限的url默认都为白名单，想要换成默认是黑名单只要修改这里即可。
     */
    @Override
    public Collection<ConfigAttribute> getAttributes(Object object) throws IllegalArgumentException {
        if (map == null) {
            loadResourceDefine();
        }

        HttpServletRequest request = ((FilterInvocation) object).getHttpRequest();
        AntPathRequestMatcher matcher;
        String resUrl;
        for (Iterator<String> iter = map.keySet().iterator(); iter.hasNext(); ) {
            resUrl = iter.next();
            matcher = new AntPathRequestMatcher(resUrl);
            if (matcher.matches(request)) {
                return map.get(resUrl);
            }
        }
        return null;
    }

    @Override
    public Collection<ConfigAttribute> getAllConfigAttributes() {
        return null;
    }

    @Override
    public boolean supports(Class<?> clazz) {
        return true;
    }
}
