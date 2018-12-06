package cn.com.taiji.repository;

import cn.com.taiji.domain.Menu;
import cn.com.taiji.domain.Role;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Set;

/**
 * 菜单表dao
 *
 * @author SunJingyan
 * @date 2014-04-21
 */
@Repository
public interface MenuRepository extends JpaRepository<Menu, String>, JpaSpecificationExecutor<Menu> {

    /**
     * 查询菜单树
     *
     * @return
     */
    @Query("select o from Menu o left  join fetch  o.children c  where c.flag=1")
    public List<Menu> findMenuTree();

    /**
     * @param mark
     * @return Menu
     * @throws
     * @Description: 标示，暂时用于区别初始化菜单来源
     * @author chixue
     * @date 2016年5月9日
     */
    Menu findByMark(@Param("mark") String mark);

    /**
     * 查询出树根
     */
    List<Menu> findByFlagAndParentIsNullOrderByMenuIndexAsc(@Param("flag") int flag);

    /**
     * 标记为删除
     *
     * @param id
     */
    @Modifying
    @Query("update Menu m set m.flag=0 where m.id=:id")
    void updateFlag(@Param("id") String id);

    /**
     * 根据描述查询出树根
     *
     * @param menuDesc
     * @return
     */
    @Query("select o from Menu o where o.parent.id=0 and o.menuDesc=:menuDesc and o.flag=1")
    List<Menu> findRoots(@Param("menuDesc") String menuDesc);

    /**
     * 查询所有菜单（未标记未删除的）
     *
     * @return
     */
    @Query("select m from Menu m where m.flag=1")
    List<Menu> findAllMenus();

    /**
     * 按角色查询认证授权的菜单
     *
     * @param roles
     * @return
     */
    @Query("select o from Menu o  left JOIN o.roles r where  o.parent is null and o.flag=1 and (r in (:roles))  order by o.menuIndex")
    List<Menu> findRootByAuthorization(@Param("roles") Set<Role> roles);


    /**
     * 按节点查询认证授权的菜单
     *
     * @param roles
     * @return
     */
    @Query("select distinct o from Menu o  left JOIN o.roles r where  o.parent.id=:id and  o.flag=1 and (r in (:roles))  order by o.menuIndex")
    List<Menu> findMenuByRootAndAuthorization(@Param("roles") Set<Role> roles, @Param("id") String id);


    /**
     * 按节点查询认证授权的菜单
     *
     * @param roleId
     * @return
     */
    @Query("select o from Menu o  left JOIN o.roles r where  o.flag=1 and r.id= :roleId  order by o.menuIndex")
    List<Menu> getMenuListByRole(@Param("roleId") String roleId);

    @Query("select o from Menu o  where  o.parent.id=:id and  o.flag=1  order by o.menuIndex")
    List<Menu> findMenuByRootId(@Param("id") String id);

    /**
     * @param mark
     * @return List<Menu>
     * @throws
     * @Description: 描述信息查询，根据描述信息查询出菜单的相关信息
     * @author chixue
     * @date 2017年2月20日
     */
    @Query("select o from Menu o  where  o.parent.mark=:mark and  o.flag=1  order by o.menuIndex")
    List<Menu> findMenuByMark(@Param("mark") String mark);
}
