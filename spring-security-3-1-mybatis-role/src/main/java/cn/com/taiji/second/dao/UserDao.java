package cn.com.taiji.second.dao;

import cn.com.taiji.second.domain.User;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.security.access.annotation.Secured;

import java.util.List;

/**
 * @author andyzhao
 */
@Mapper
public interface UserDao {
    User findByUserName(String user_name);

    @Secured("ROLE_ADMIN")
    List<User> listAll();
}
