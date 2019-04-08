package cn.com.taiji.day1.filters;

import cn.com.taiji.day1.mock.Mock;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.servlet.*;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;

public class LoginFilter implements Filter {
    private static Logger logger = LoggerFactory.getLogger(LoginFilter.class);

    @Override
    public void init(FilterConfig filterConfig) throws ServletException {
        logger.info("Init");
    }

    @Override
    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain) throws IOException, ServletException {
        logger.info("访问到了过滤器，doFilter");
        HttpServletRequest request = (HttpServletRequest) servletRequest;
        HttpServletResponse response = (HttpServletResponse) servletResponse;
        HttpSession session = request.getSession(false);
        if (session != null && session.getAttribute(Mock.User_Key) != null) {
            // session已经存在user，说明已经登录成功, 通过
            filterChain.doFilter(servletRequest, servletResponse);
        } else {
            // session不存在user，说明没有登录成功，需要登录
            servletResponse.getWriter().write("please call /login first.");
            return ;
        }

        logger.info("LoginFilter 执行完毕");
    }

    @Override
    public void destroy() {
        logger.info("destroy");
    }
}
