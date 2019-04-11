package cn.com.taiji.day1.filters;

import cn.com.taiji.day1.mock.UserServiceMock;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.servlet.*;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.util.Base64;
import java.util.HashMap;

public class HttpBasicAuthenticationFilter implements Filter {
    private static Logger logger = LoggerFactory.getLogger(HttpBasicAuthenticationFilter.class);

    @Override
    public void init(FilterConfig filterConfig) throws ServletException {
        logger.info("Init");
    }

    @Override
    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain) throws IOException, ServletException {
        logger.info("访问到了过滤器HttpBasicAuthenticationFilter，doFilter");
        HttpServletRequest request = (HttpServletRequest) servletRequest;
        HttpServletResponse response = (HttpServletResponse) servletResponse;

        String header = request.getHeader("Authorization");
        if (header == null || !header.toLowerCase().startsWith("basic ")) {
            // 如果不是basic认证，则继续后续逻辑
            filterChain.doFilter(request, response);
            return;
        }

        byte[] base64Token = header.substring(6).getBytes("UTF-8");
        byte[] decoded = Base64.getDecoder().decode(base64Token);
        String token = new String(decoded, "UTF-8");

        int delim = token.indexOf(":");

        if (delim == -1) {
            servletResponse.getWriter().write("Invalid basic authentication token\"");
            return;
        }
        String username = token.substring(0, delim);
        String password = token.substring(delim + 1);

        logger.info("Basic认证，Http头信息：user '" + username + "'");

        if (!UserServiceMock.userList.contains(new HashMap() {{
            put(username, password);
        }})) {
            // Basic用户名和密码不对,报错
            servletResponse.getWriter().write("Invalid basic authentication password or username\"");
            return;
        }

        //如果有这个用户那么登录成功
        HttpSession session = request.getSession(true);
        if (session.getAttribute(UserServiceMock.User_Key) == null) {
            //用户session不存在，则新建session
            session.setAttribute(UserServiceMock.User_Key, username);
        }

        filterChain.doFilter(servletRequest, servletResponse);
        logger.info("HttpBasicAuthenticationFilter执行完毕");
    }

    @Override
    public void destroy() {
        logger.info("destroy");
    }

}
