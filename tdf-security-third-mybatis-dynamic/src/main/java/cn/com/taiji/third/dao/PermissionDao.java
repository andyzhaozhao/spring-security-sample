package cn.com.taiji.third.dao;

import cn.com.taiji.third.domain.Permission;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

/**
 * Created by yangyibo on 17/1/20.
 */
@Mapper
public interface PermissionDao {
    public List<Permission> findAll();
    public List<Permission> findByAdminUserId(String userId);
}
