package cn.com.taiji.support;

import cn.com.taiji.service.PermissionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.ConfigAttribute;
import org.springframework.security.web.FilterInvocation;
import org.springframework.security.web.access.intercept.FilterInvocationSecurityMetadataSource;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpServletRequest;
import javax.websocket.server.ServerEndpoint;
import java.util.Collection;
import java.util.Map;

/**
 * Created by iandtop on 2018/12/11.
 */
@Service
public class CustomInvocationSecurityMetadataSourceService implements FilterInvocationSecurityMetadataSource {
    @Autowired
    private PermissionService permissionService;

    @Override
    public Collection<ConfigAttribute> getAttributes(Object object) throws IllegalArgumentException {
        HttpServletRequest request = ((FilterInvocation) object).getHttpRequest();

        Map<String, Collection<ConfigAttribute>> permissionMap = permissionService.getPermissionMap();
        for (String resUrl : permissionMap.keySet()) {
            AntPathRequestMatcher matcher = new AntPathRequestMatcher(resUrl);
            if (matcher.matches(request)) {
                return permissionService.getPermissionMap().get(resUrl);
            }
        }

//        String resUrl = request.getRequestURL();
//        return permissionService.getPermissionMap().get(resUrl);

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
