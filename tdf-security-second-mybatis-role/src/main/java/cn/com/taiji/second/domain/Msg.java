package cn.com.taiji.second.domain;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
public class Msg {
    private String title;
    private String content;
    private String etraInfo;

    public Msg(String title, String content, String etraInfo) {
        super();
        this.title = title;
        this.content = content;
        this.etraInfo = etraInfo;
    }
}
