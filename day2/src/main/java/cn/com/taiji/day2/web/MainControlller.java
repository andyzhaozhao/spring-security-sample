package cn.com.taiji.day2.web;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class MainControlller {
    @GetMapping({"/", "/home"})
    public String home() {
        return "home";
    }
}
