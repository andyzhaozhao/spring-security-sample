package cn.com.taiji.day1;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Day1ApplicationTest {

    private static Logger logger = LoggerFactory.getLogger(Day1ApplicationTest.class);

    private static List<Map> userList = new ArrayList() {{
        add(new HashMap() {{
            put("user", "user");
        }});
        add(new HashMap() {{
            put("admin", "admin");
        }});
    }};

    public static void main(String[] args) {
        // 判断用户名密码是否正确的逻辑
        if (userList.contains(new HashMap() {{
            put("user", "user");
        }})) {
            logger.info("true");
        } else {
            logger.info("false");
        }
    }
}
