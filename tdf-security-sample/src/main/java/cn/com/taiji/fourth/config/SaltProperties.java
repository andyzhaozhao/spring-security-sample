package cn.com.taiji.fourth.config;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

@Component
@ConfigurationProperties(prefix = "taiji")
@Data
public class SaltProperties {
    private String salt;
}
