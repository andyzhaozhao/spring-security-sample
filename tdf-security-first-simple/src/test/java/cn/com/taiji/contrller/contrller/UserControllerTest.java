package cn.com.taiji.contrller.contrller;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

/**
 * Hello World 控制器测试类
 *
 * @author <a href="https://waylau.com">Way Lau</a>
 * @date 2017年1月26日
 */
@RunWith(SpringRunner.class)
@SpringBootTest
@AutoConfigureMockMvc
public class UserControllerTest {

    @Autowired
    private MockMvc mockMvc;

    //@Test
    public void testList() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.get("/users"))
                .andExpect(status().isOk());
    }

    @Test
    @WithMockUser(username = "zhaozhao", password = "123456", roles = {"USER"})
    // mock 了一个用户，mock 用户名为“waylau”角色权限为“USER”的用户
    public void testListWithUser() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.get("/users"))
                .andExpect(status().isOk());
    }

//    @Test
    @WithMockUser(username = "zhaozhao", password = "123456", roles = {"ADMIN"})
    // mock 了一个用户 mock 用户名为“waylau”角色权限为“ADMIN”的用户
    public void testListWithAdmin() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.get("/users"))
                .andExpect(status().isOk());
    }
}
