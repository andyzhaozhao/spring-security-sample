package cn.com.taiji.firth.repository;

import cn.com.taiji.firth.domain.Menu;
import cn.com.taiji.firth.domain.Role;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * 类名称：RoleRepository  角色Repository
 * 类描述：
 * 创建人：wanghw
 * 创建时间：2018年2月5日 下午8:45:30
 */
@Repository
public interface RoleRepository extends JpaRepository<Role, String>,
        JpaSpecificationExecutor<Role>, PagingAndSortingRepository<Role, String> {

    /**
     * 根据角色id查询菜单集合
     *
     * @param roleId
     * @return
     */
    @Query("SELECT u.menus FROM Role u WHERE u.id = :roleId")
    List<Menu> findMenuByRoleId(@Param("roleId") String roleId);

    /**
     * 标记为删除
     *
     * @param id
     */
    @Modifying
    @Query("update Role m set m.flag=0  where m.id=:id")
    void updateFlag(@Param("id") String id);

    /**
     * 查询所有未标记为删除的角色集合
     *
     * @return
     */
    @Query("select r from Role r where r.flag=1")
    List<Role> findAllRoles();

    /**
     * 根据roleName查询出记录
     *
     * @param roleName
     * @return
     */
    @Query("select r from Role r where r.roleName = :roleName and r.id!= :id and r.flag=1")
    List<Role> findByRoleName(@Param("roleName") String roleName, @Param("id") String id);

    Role findByRoleNameAndFlag(@Param("roleName") String roleName, @Param("flag") int flag);
}
