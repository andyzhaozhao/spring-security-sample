package cn.com.taiji.firth.repository;

import cn.com.taiji.firth.domain.RoleUser;
import cn.com.taiji.firth.domain.RoleUserPK;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * 类名称：RoleUserRepository   角色用户表Repository
 * 类描述：
 * 创建人：wanghw
 * 创建时间：2018年2月5日 下午8:46:31
 */
@Repository
public interface RoleUserRepository extends JpaRepository<RoleUser, RoleUserPK> {

    @Query("select k.id.userId from RoleUser k where k.id.roleId =:roleId")
    List<String> findUserIdsByRoleId(@Param("roleId") String roleId);

    @Query("select k.id.roleId from RoleUser k where k.id.userId =:userId")
    List<String> findRoleIdsByUserId(@Param("userId") String userId);

    @Query("select k from RoleUser k where k.id.userId =:userId")
    List<RoleUser> findByUserId(@Param("userId") String userId);

    @Query("select k from RoleUser k where k.id.roleId =:roleId")
    List<RoleUser> findByRoleId(@Param("roleId") String roleId);

    @Query("select k from RoleUser k where k.id=:id")
    RoleUser findByPk(@Param("id") RoleUserPK id);


}
