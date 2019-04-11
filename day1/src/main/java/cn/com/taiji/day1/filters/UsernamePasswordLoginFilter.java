package cn.com.taiji.day1.filters;

import cn.com.taiji.day1.mock.UserServiceMock;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.servlet.*;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.util.HashMap;

public class UsernamePasswordLoginFilter implements Filter {
    private static Logger logger = LoggerFactory.getLogger(UsernamePasswordLoginFilter.class);

    @Override
    public void init(FilterConfig filterConfig) throws ServletException {
        logger.info("Init");
    }

    @Override
    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain) throws IOException, ServletException {
        logger.info("访问到了过滤器，doFilter");
        HttpServletRequest request = (HttpServletRequest) servletRequest;
        HttpServletResponse response = (HttpServletResponse) servletResponse;

        String path = request.getServletPath();
        if ("/login".equals(path)) {
            // 如果是login请求
            // 判断用户名密码是否正确的逻辑
            String username = request.getParameter("username");
            String password = request.getParameter("password");

            if (UserServiceMock.userList.contains(new HashMap() {{
                put(username, password);
            }})) {
                //如果有这个用户那么登录成功
                HttpSession session = request.getSession(true);
                session.setAttribute(UserServiceMock.User_Key, username);
                //登录成功
                servletResponse.getWriter().write("login success!");
                return ;
            }
            //登录失败，报错
            servletResponse.getWriter().write("username or password is incorrect.");
            return ;
        }

        // 如果是其他请求
        HttpSession session = request.getSession(false);
        if (session != null && session.getAttribute(UserServiceMock.User_Key) != null) {
            // session已经存在user，说明已经登录成功, 通过
            filterChain.doFilter(servletRequest, servletResponse);
        } else {
            // session不存在user，说明没有登录成功，需要登录
            servletResponse.getWriter().write("please call /login first.");
            return ;
        }

        logger.info("UsernamePasswordLoginFilter 执行完毕");
    }

    @Override
    public void destroy() {
        logger.info("destroy");
    }
}
