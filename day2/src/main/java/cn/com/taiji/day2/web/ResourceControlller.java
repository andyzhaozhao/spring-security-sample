package cn.com.taiji.day2.web;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class ResourceControlller {
    @GetMapping({"/about"})
    public String about() {
        return "about";
    }

    @GetMapping({"/a"})
    public String a() {
        return "resources a";
    }

    @GetMapping({"/b"})
    public String b() {
        return "resources b";
    }


    @GetMapping({"/resources/1"})
    public String home() {
        return "resources 1";
    }

    @GetMapping({"/resources/2"})
    public String home2() {
        return "resources 2";
    }


    @GetMapping({"/admin/1"})
    public String admin() {
        return "admin 1";
    }

    @GetMapping({"/admin/2"})
    public String admin1() {
        return "admin 2";
    }

    @GetMapping({"/admin/a1/1/1"})
    public String admin111() {
        return "admin 11111";
    }

    @GetMapping({"/db/1"})
    public String db() {
        return "db 1";
    }

    @GetMapping({"/db/2"})
    public String db2() {
        return "db 2";
    }
}
