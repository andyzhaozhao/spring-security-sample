package cn.com.taiji;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;

@SpringBootApplication
@EnableJpaAuditing
public class SpringSecurity4JpaAllApplication {

	public static void main(String[] args) {
		SpringApplication.run(SpringSecurity4JpaAllApplication.class, args);
	}
}
