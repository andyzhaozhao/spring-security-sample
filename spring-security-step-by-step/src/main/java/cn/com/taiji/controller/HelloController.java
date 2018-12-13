package cn.com.taiji.controller;

import org.springframework.security.access.annotation.Secured;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * Created by iandtop on 2018/12/11.
 */
@RestController
public class HelloController {
    @GetMapping("/hello")
    public String getWelcomeMsg() {
        return "Hello,World";
    }

    @GetMapping("/helloUser")
//    @PreAuthorize("hasAnyRole('USER')")
    public String getHelloUser() {
        return "Hello,User";
    }

    @GetMapping("/helloAdmin")
//    @PreAuthorize("hasAnyRole('ADMIN')")
    public String getHelloAdmin() {
        return "Hello,Admin";
    }
}
