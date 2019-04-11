package cn.com.taiji.day1.mock;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class UserServiceMock {
    public static final String User_Key = "user";

    public static List<Map> userList = new ArrayList() {{
        add(new HashMap() {{
            put("user", "user");
        }});
        add(new HashMap() {{
            put("admin", "admin");
        }});
    }};

}
