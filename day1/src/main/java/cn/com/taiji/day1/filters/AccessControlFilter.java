package cn.com.taiji.day1.filters;

import cn.com.taiji.day1.mock.UserServiceMock;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.servlet.*;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;

public class AccessControlFilter implements Filter {
    private static Logger logger = LoggerFactory.getLogger(AccessControlFilter.class);

    @Override
    public void init(FilterConfig filterConfig) throws ServletException {
        logger.info("Init");
    }

    @Override
    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain) throws IOException, ServletException {
        logger.info("访问到了过滤器AccessControlFilter，doFilter");
        HttpServletRequest request = (HttpServletRequest) servletRequest;
        HttpServletResponse response = (HttpServletResponse) servletResponse;
        HttpSession session = request.getSession(false);
        if (session != null && session.getAttribute(UserServiceMock.User_Key)!=null){
            String username = (String) session.getAttribute(UserServiceMock.User_Key);

            String path = request.getServletPath();
            if(username.equals("user") && !path.equals("/a")){
                // 用户user只能访问端口a
                // session不存在user，说明没有登录成功，需要登录
                servletResponse.getWriter().write("user could only visit '/a'");
                return ;
            }
        }

        filterChain.doFilter(servletRequest, servletResponse);
        logger.info("AccessControlFilter用来进行访问控制 执行完毕");
    }

    @Override
    public void destroy() {
        logger.info("destroy");
    }
}
