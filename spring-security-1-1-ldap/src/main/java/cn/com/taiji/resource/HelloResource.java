package cn.com.taiji.resource;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class HelloResource {

    @GetMapping("/check")
    public String check() {

        return "Check Worked";
    }
}
