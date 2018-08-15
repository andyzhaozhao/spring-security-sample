package cn.com.taiji.third.dao;

import cn.com.taiji.third.domain.User;
import org.apache.ibatis.annotations.Mapper;

/**
 * @author andyzhao
 */
@Mapper
public interface UserDao {
    public User findByUserName(String user_name);
}
