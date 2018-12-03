package cn.com.taiji.third.dao;

import cn.com.taiji.third.domain.User;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

/**
 * @author andyzhao
 */
@Mapper
public interface UserDao {
    User findByUserName(String user_name);
    List<User> listAll();
}
