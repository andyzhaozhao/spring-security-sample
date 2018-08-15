package cn.com.taiji.fourth.repository;


import cn.com.taiji.fourth.domain.RoleMenu;
import cn.com.taiji.fourth.domain.RoleMenuPK;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * 类名称：RoleMenuRepository   菜单角色表Repository
 * 类描述：
 * 创建人：wanghw
 * 创建时间：2018年2月5日 下午8:45:12
 */
@Repository
public interface RoleMenuRepository extends JpaRepository<RoleMenu, RoleMenuPK>, JpaSpecificationExecutor<RoleMenu> {

    @Query("select k.id.menuId from RoleMenu k where k.id.roleId =:roleId")
    List<String> findMenuIdsByRoleId(@Param("roleId") String roleId);

    @Query("select k.id.roleId from RoleMenu k where k.id.menuId =:menuId")
    List<String> findRoleIdsByMenuId(@Param("menuId") String menuId);

    @Query("select k from RoleMenu k where k.id.menuId =:menuId")
    List<RoleMenu> findByMenuId(@Param("menuId") String menuId);

    @Query("select k from RoleMenu k where k.id.roleId =:roleId")
    List<RoleMenu> findByRoleId(@Param("roleId") String roleId);

    @Query("select k from RoleMenu k where k.id=:id")
    RoleMenu findByPk(@Param("id") RoleMenuPK id);

}
