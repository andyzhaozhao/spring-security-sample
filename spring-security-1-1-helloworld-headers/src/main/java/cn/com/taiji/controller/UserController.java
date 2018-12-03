package cn.com.taiji.controller;

import cn.com.taiji.domain.User;
import org.springframework.ui.Model;
import org.springframework.validation.support.BindingAwareModelMap;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import java.util.ArrayList;
import java.util.List;


/**
 * 用户控制器.
 */
@RestController
public class UserController {

    private User xsstestUser;

    /**
     * 查询所用用户
     *
     * @return
     */
    @GetMapping("users")
    public ModelAndView users(Model model) {
        return initUsers(model);
    }

    @PostMapping("addUser")
    public ModelAndView addUser(User add) {
        xsstestUser = new User(3L, add.getName(), 32);
        ModelAndView modelAndView = new ModelAndView("redirect:users");
        return modelAndView;
    }

    /**
     * get接口去新增数据，非常不安全
     *
     * @return
     */
    @GetMapping("addUserByGet")
    public ModelAndView addUserByGet(@RequestParam String name) {
        xsstestUser = new User(3L, name, 32);
        ModelAndView modelAndView = new ModelAndView("redirect:users");
        return modelAndView;
    }

    private ModelAndView initUsers(Model model) {
        List<User> list = new ArrayList<>();    // 当前所在页面数据列表
        list.add(new User(1L, "user", 30));
        list.add(new User(2L, "admin", 29));
        if (xsstestUser != null) {
            list.add(xsstestUser);
        }
        model.addAttribute("title", "用户管理");
        model.addAttribute("userList", list);
        return new ModelAndView("users/list", "userModel", model);
    }
}
