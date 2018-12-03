package cn.com.taiji.second.controller;

import cn.com.taiji.second.dao.UserDao;
import cn.com.taiji.second.domain.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.ModelAndView;

import java.util.ArrayList;
import java.util.List;


/**
 * 用户控制器.
 */
@RestController
@RequestMapping("/users")
public class UserController {

    @Autowired
    private UserDao userDao;

    /**
     * 查询所用用户
     *
     * @return
     */
    @GetMapping
    public ModelAndView list(Model model) {
        List<User> list = userDao.listAll();
        model.addAttribute("title", "用户管理");
        model.addAttribute("userList", list);
        return new ModelAndView("users/list", "userModel", model);
    }
}
