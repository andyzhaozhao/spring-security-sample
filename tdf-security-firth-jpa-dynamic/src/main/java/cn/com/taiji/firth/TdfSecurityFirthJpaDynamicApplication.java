package cn.com.taiji.firth;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.AutoConfigureAfter;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.jackson.JacksonAutoConfiguration;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;
import org.springframework.scheduling.annotation.EnableScheduling;

@SpringBootApplication
@EnableJpaAuditing
@EnableScheduling
public class TdfSecurityFirthJpaDynamicApplication {

    public static void main(String[] args) {
        SpringApplication.run(TdfSecurityFirthJpaDynamicApplication.class, args);
    }
}
