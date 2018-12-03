package cn.com.taiji.fourth.service;

import cn.com.taiji.fourth.domain.Menu;
import cn.com.taiji.fourth.domain.Role;
import cn.com.taiji.fourth.dto.MenuDTO;
import cn.com.taiji.fourth.dto.RoleDTO;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.List;
import java.util.Map;
import java.util.Set;


public interface MenuService {

    /**
     * 查询菜单信息
     *
     * @param searchParameters 查询参数的map集合
     * @return 查询的结果, map类型 total:总条数 menus:查询结果list集合
     */
    Map getPage(final Map searchParameters, String salt);


    /**
     * @param menu
     * @param salt
     * @return
     */

    MenuDTO menu2Dto(Menu menu, String salt);


    /**
     * @throws
     * @Description: 添加和编辑菜单信息
     * @author dourl
     * @date 2017年6月6日
     */
    MenuDTO saveMenu(MenuDTO dto, String salt);

    /**
     * 多条删除
     *
     * @param list
     */
    void deleteMenu(List<MenuDTO> list);

    /**
     * 根据ID查询菜单
     *
     * @param id
     * @return
     */
    Menu findById(String id);

    /**
     * @param id
     * @param salt
     * @return MenuDTO
     * @throws
     * @Description: 根据 id 返回 菜单DTO对象
     * @author dourl
     * @date 2017年6月6日
     */
    MenuDTO findMenuById(String id, String salt);

    /**
     * 查询出所有的菜单集合
     *
     * @return
     */
    List<MenuDTO> findAllMenus();

    /**
     * 上传菜单图标文件
     * 暂时无用
     *
     * @param menuId
     * @param iconPath
     */
    void uploadPic(String menuId, byte[] iconPath,
                   UserDetails userDetails);

    /**
     * 获得认证获得被授权树
     * 暂时没有
     *
     * @return
     */
    List<MenuDTO> findRootByAuthorization(Set<RoleDTO> roleDTOs);

    /**
     * 获得认证获得被授权树
     *
     * @return
     */
    List<Menu> findRootByAuthorization(Set<RoleDTO> roleDTOs, String rootId);

    /**
     * 根据节点ID获得树
     *
     * @param rootId
     * @return
     */
    List<MenuDTO> findMenuByRootId(String rootId);

    /**
     * 根据标示获得树
     *
     * @param mark
     * @return
     */
    List<MenuDTO> findMenuByMark(String mark);

    /**
     * 获得根节点菜单
     *
     * @return
     */

    List<MenuDTO> findRootTree();

    /**
     * @param list
     * @return List<MenuDTO>
     * @throws
     * @Description: 集合对象转换为实体
     * @author chixue
     * @date 2016年10月20日
     */
    List<MenuDTO> object2DtoList(List<Menu> list);

    /**
     * @param list
     * @return List<Menu>
     * @throws
     * @Description: DTO转OBJ 编辑保存使用修改条件判断
     * @author dourl
     * @date 2017年6月15日
     */
    List<Menu> dto2Object(List<MenuDTO> list);

    /**
     * 查询出更新后的数据
     *
     * @param list
     * @return
     */
    List<MenuDTO> findMenus(List<MenuDTO> list, String salt);

    /**
     * 根据角色Id获得被授权的菜单信息
     *
     * @param roleId
     * @return
     */
    List<MenuDTO> getMenuListByRoleId(String roleId);

}