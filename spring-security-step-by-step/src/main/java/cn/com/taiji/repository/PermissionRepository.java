package cn.com.taiji.repository;

import cn.com.taiji.domain.Permission;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 * Created by iandtop on 2018/12/11.
 */
public interface PermissionRepository extends JpaRepository<Permission, Long> {

}
