package cn.com.taiji.day3.service;

import org.springframework.security.access.annotation.Secured;
import org.springframework.stereotype.Component;

@Component
public class BankService {
    @Secured("ROLE_USER")
    public String aaaaaaaa() {
        return "我是被保护的方法";
    }
}
