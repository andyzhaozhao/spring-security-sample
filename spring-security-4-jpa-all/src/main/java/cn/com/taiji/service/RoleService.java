package cn.com.taiji.service;

import cn.com.taiji.domain.Role;
import cn.com.taiji.dto.RoleDTO;

import java.util.List;
import java.util.Map;
import java.util.Set;

/**
 * 类名称：角色Service
 * 类描述：
 * 创建人：wanghw
 * 创建时间：2018年2月5日 下午8:30:43
 */
public interface RoleService {

    /**
     * @param searchParameters
     * @param salt
     * @return Map
     * @throws
     * @Description: 查询角色信息
     * @author wanghw
     * @date 2018年2月5日
     */
    Map getPage(final Map searchParameters, String salt);

    /**
     * @throws
     * @Description: 查询所有角色
     * @author wanghw
     * @date 2018年2月5日
     */
    List<Role> findAllRoles();

    /**
     * @param list void
     * @throws
     * @Description: 添加角色信息
     * @author wanghw
     * @date 2018年2月5日
     */
    void add(List<RoleDTO> list, String salt);

    /**
     * @param roleIds void
     * @throws
     * @Description: 多条删除
     * @author wanghw
     * @date 2018年2月5日
     */
    void deleteRole(String[] roleIds);

    /**
     * 根据ID查询角色
     *
     * @param id
     * @return
     */
    RoleDTO findById(String id, String salt);

    /**
     * @param name
     * @return Role
     * @throws
     * @Description: 根据角色名称查询角色详情
     * @author dourl
     * @date 2018年3月26日
     */
    RoleDTO findByRoleName(String name);

    /**
     * @param userIds
     * @param preUserIds
     * @param roleId     void
     * @throws
     * @Description:保存角色用户的分配
     * @author wanghw
     * @date 2018年2月5日
     */
    void saveRoleUsers(String[] userIds, String preUserIds, String roleId);

    /**
     * @param roleId 户选择的角色id
     * @return 映射的菜单id的字符串，以逗号分隔
     * @throws
     * @Description: 根据roleId查询出关系表中所有的菜单Id
     * @author wanghw
     * @date 2018年2月5日
     */
    List<String> findMenuIdsByRoleId(String roleId);

    /**
     * @param menuIds    选择的菜单Id的字符串，以逗号分隔的
     * @param preMenuIds 旧的被选中的ids
     * @param roleId     void  选择的角色Id
     * @throws
     * @Description: 保存角色菜单的分配
     * @author wanghw
     * @date 2018年2月5日
     */
    void saveRoleMenus(String[] menuIds, String[] preMenuIds, String roleId);

    /**
     * @param roleName
     * @param id
     * @return boolean
     * @throws
     * @Description: 根据角色名查询
     * @author wanghw
     * @date 2018年2月5日
     */
    boolean findByName(String roleName, String id);

    List<Role> findRoleByName(String roleName, String id);

    /**
     * @param roleList
     * @param salt
     * @return List<RoleDTO>
     * @throws
     * @Description: 集合对象转换
     * @author chixue
     * @date 2017年5月12日
     */
    List<RoleDTO> objList2DtoList(List<Role> roleList, String salt);

    /**
     * @param role
     * @param salt
     * @return RoleDTO
     * @throws
     * @Description: 对象转换
     * @author chixue
     * @date 2017年5月12日
     */
    RoleDTO obj2Dto(Role role, String salt);

    RoleDTO obj2Dto(Role role);

    Set<Role> dtoList2ObjList(Set<RoleDTO> roleList);
}
