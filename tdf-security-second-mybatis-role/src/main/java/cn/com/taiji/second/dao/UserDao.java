package cn.com.taiji.second.dao;

import cn.com.taiji.second.domain.User;
import org.apache.ibatis.annotations.Mapper;

/**
 * @author andyzhao
 */
@Mapper
public interface UserDao {
    public User findByUserName(String user_name);
}
