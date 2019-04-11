package cn.com.taiji.day3.web;

import cn.com.taiji.day3.domain.Users;
import cn.com.taiji.day3.service.BankService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class MainController {
    @Autowired
    private BankService bankService;

    @GetMapping("/")
    public String main() {
        String result = bankService.aaaaaaaa();
        return "main" + result;
    }

    @GetMapping("/user")
    public Users user() {
        Object principal = SecurityContextHolder.getContext().getAuthentication().getPrincipal();

        if ("anonymousUser".equals(principal)) {
            Users users = new Users();
            users.setUsername("anonymousUser");
            return users;
        } else {
            Users user = (Users) principal;
            return user;
        }
    }

    @GetMapping("/admin")
    public String admin() {
        return "admin";
    }
}
