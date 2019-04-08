package cn.com.taiji.day1.web;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class MainController {
    private static Logger logger = LoggerFactory.getLogger(MainController.class);

    @GetMapping("/a")
    public String a() {
        return "aaaaaaaaaaaaaaa";
    }

    @GetMapping("/b")
    public String b() {
        return "bbbbbbbbbbbbbb";
    }
}
