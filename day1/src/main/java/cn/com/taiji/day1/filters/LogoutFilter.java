package cn.com.taiji.day1.filters;

import cn.com.taiji.day1.mock.UserServiceMock;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.servlet.*;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;

public class LogoutFilter implements Filter {
    private static Logger logger = LoggerFactory.getLogger(LogoutFilter.class);

    public static final String User_Key = "user";

    @Override
    public void init(FilterConfig filterConfig) throws ServletException {
        logger.info("Init");
    }

    @Override
    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain) throws IOException, ServletException {
        logger.info("访问到了过滤器LgoutFilter.doFilter");
        HttpServletRequest request = (HttpServletRequest) servletRequest;
        HttpServletResponse response = (HttpServletResponse) servletResponse;
        HttpSession session = request.getSession(false);

        String path = request.getServletPath();
        if ("/logout".equals(path)) {
            // 如果当前调用的是登出接口
            if (session == null || session.getAttribute(User_Key) == null) {
                //用户session不存在，处于已经登出状态，直接返回
                servletResponse.getWriter().write("User has logouted .");
                return;
            }
            // 如果是登出端口，则直接删除session进行登出操作，不用进入到controller 层
            session.removeAttribute(UserServiceMock.User_Key);
            servletResponse.getWriter().write("Logout success!");
            return;
        }

        filterChain.doFilter(servletRequest, servletResponse);
        logger.info("LogoutFilter 执行完毕");
    }

    @Override
    public void destroy() {
        logger.info("destroy");
    }
}
