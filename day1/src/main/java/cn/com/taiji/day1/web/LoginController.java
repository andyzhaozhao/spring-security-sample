//package cn.com.taiji.day1.web;
//
//import cn.com.taiji.day1.mock.UserServiceMock;
//import org.slf4j.Logger;
//import org.slf4j.LoggerFactory;
//import org.springframework.web.bind.annotation.PostMapping;
//import org.springframework.web.bind.annotation.RestController;
//
//import javax.servlet.http.HttpServletRequest;
//import javax.servlet.http.HttpSession;
//import java.util.HashMap;
//
//@RestController
//public class LoginController {
//    private static Logger logger = LoggerFactory.getLogger(LoginController.class);
//
//    // 登录
//    @PostMapping("/login")
//    public String login(HttpServletRequest request, String username, String password) {
//        HttpSession session = request.getSession();
//
//        // 判断用户名密码是否正确的逻辑
//        if (UserServiceMock.userList.contains(new HashMap() {{
//            put(username, password);
//        }})) {
//            //如果有这个用户那么登录成功
//            session.setAttribute(UserServiceMock.User_Key, username);
//            return "登录成功,您可以访问接口了";
//        }
//
//        return "登录失败，请求检查您的用户名和密码";
//    }
//
////    // 退出
////    @PostMapping("/logout")
////    public String logout(HttpServletRequest request, String username) {
////        HttpSession session = request.getSession();
////        session.removeAttribute(UsernamePasswordLoginFilter.User_Key);
////        return "登出成功";
////    }
//
//}
