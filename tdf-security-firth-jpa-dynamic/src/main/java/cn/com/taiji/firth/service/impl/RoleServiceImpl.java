package cn.com.taiji.firth.service.impl;

import cn.com.taiji.firth.domain.*;
import cn.com.taiji.firth.dto.RoleDTO;
import cn.com.taiji.firth.exception.BusinessException;
import cn.com.taiji.firth.repository.RoleMenuRepository;
import cn.com.taiji.firth.repository.RoleRepository;
import cn.com.taiji.firth.repository.RoleUserRepository;
import cn.com.taiji.firth.service.RoleService;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.data.domain.Sort.Direction;
import org.springframework.data.domain.Sort.Order;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;
import java.util.*;
import java.util.stream.Collectors;

/**
 * 类名称：角色Service
 * 类描述：
 * 创建人：wanghw
 * 创建时间：2018年2月5日 下午8:30:43
 */
@Service
public class RoleServiceImpl implements RoleService {

    private static final Logger log = LoggerFactory
            .getLogger(RoleServiceImpl.class);

    @Autowired
    private RoleRepository roleRepository;

    @Autowired
    private RoleUserRepository roleUserRepository;

    @Autowired
    private RoleMenuRepository roleMenuRepository;


    public final String roleIdKey = "5b66ecf45d634159a08468898b1b3217";

    /**
     * @param searchParameters
     * @param salt
     * @return Map
     * @throws
     * @Description: 查询角色信息
     * @author wanghw
     * @date 2018年2月5日
     */
    @Transactional(propagation = Propagation.SUPPORTS)
    public Map getPage(final Map searchParameters, String salt) {
        Map map = new HashMap();
        int page = 0;
        int pageSize = 10;
        Page<Role> pageList;
        // 页码
        if (searchParameters != null && searchParameters.size() > 0
                && searchParameters.get("page") != null) {
            page = Integer.parseInt(searchParameters.get("page").toString()) - 1;
        }
        // 每页显示的条数
        if (searchParameters != null && searchParameters.size() > 0
                && searchParameters.get("pageSize") != null) {
            pageSize = Integer.parseInt(searchParameters.get("pageSize")
                    .toString());
        }
        if (pageSize < 1)
            pageSize = 1;
        if (pageSize > 100)
            pageSize = 100;
        List<Map> orderMaps = (List<Map>) searchParameters.get("sort");
        List<Order> orders = new ArrayList<Order>();
        // 排序字段不为空
        if (orderMaps != null) {
            for (Map m : orderMaps) {
                if (m.get("field") == null)
                    continue;
                String field = m.get("field").toString();
                if (!StringUtils.isEmpty(field)) {
                    String dir = m.get("dir").toString();
                    if ("DESC".equalsIgnoreCase(dir)) {
                        orders.add(new Order(Direction.DESC, field));
                    } else {
                        orders.add(new Order(Direction.ASC, field));
                    }
                }
            }
        }
        PageRequest pageable;
        if (orders.size() > 0) {
            pageable = new PageRequest(page, pageSize, new Sort(orders));
        } else {
            Sort s = new Sort(Direction.ASC, "roleIndex");
            pageable = new PageRequest(page, pageSize, s);
        }
        Map filter = (Map) searchParameters.get("filter");
        // 查询条件不为空
        if (filter != null) {
            // String logic = filter.get("logic").toString();
            final List<Map> filters = (List<Map>) filter.get("filters");
            Specification<Role> spec = new Specification<Role>() {
                @Override
                public Predicate toPredicate(Root<Role> root,
                                             CriteriaQuery<?> query, CriteriaBuilder cb) {
                    List<Predicate> pl = new ArrayList<Predicate>();
                    for (Map f : filters) {
                        // String operator = ((String)
                        // f.get("operator")).trim();
                        String field = f.get("field").toString().trim();
                        String value = f.get("value").toString().trim();
                        if (value != null && value.length() > 0 && !"".equals(value)) {
                            if ("roleIndex".equalsIgnoreCase(field)) {
                                pl.add(cb.equal(root.<String>get(field), value));
                            } else if ("roleName".equalsIgnoreCase(field)) {
                                pl.add(cb.like(root.<String>get(field), value + "%"));
                            } else if ("roleDesc".equalsIgnoreCase(field)) {
                                pl.add(cb.like(root.<String>get(field), value + "%"));
                            }
                        }

                    }
                    // 查询出未删除的
                    pl.add(cb.equal(root.<Integer>get("flag"), 1));
                    return cb.and(pl.toArray(new Predicate[0]));
                }
            };
            pageList = roleRepository.findAll(spec, pageable);

        }
        // 查询条件为空
        else {
            Specification<Role> spec = new Specification<Role>() {
                public Predicate toPredicate(Root<Role> root,
                                             CriteriaQuery<?> query, CriteriaBuilder cb) {
                    List<Predicate> list = new ArrayList<Predicate>();
                    // 查询出未删除的
                    list.add(cb.equal(root.<Integer>get("flag"), 1));
                    return cb.and(list.toArray(new Predicate[0]));
                }
            };
            pageList = roleRepository.findAll(spec, pageable);

        }
        map.put("total", pageList.getTotalElements());
        List<RoleDTO> rows = objList2DtoList(pageList.getContent(), salt);
        map.put("roles", rows);
        return map;
    }

    /**
     * @throws
     * @Description: 查询所有角色
     * @author wanghw
     * @date 2018年2月5日
     */
    @Transactional(propagation = Propagation.SUPPORTS)
    public List<Role> findAllRoles() {
        return roleRepository.findAllRoles();
    }

    /**
     * @param list void
     * @throws
     * @Description: 添加角色信息
     * @author wanghw
     * @date 2018年2月5日
     */
    @Transactional(propagation = Propagation.REQUIRED)
    public void add(List<RoleDTO> list, String salt) {
        if (list != null && list.size() > 0) {
            for (RoleDTO dto : list) {
                if (dto.tokenKeeped(salt)) {
                    if (!findByName(dto.getRoleName(), dto.getId())) {
                        throw new BusinessException("角色名[" + dto.getRoleName()
                                + "]已存在！");
                    }
                    // 新增
                    Role role = new Role();
                    BeanUtils.copyProperties(dto, role);
                    this.roleRepository.saveAndFlush(role);
                } else {
                    throw new BusinessException("角色[" + dto.getRoleName() + "]修改异常！请确认后重新修改");
                }
            }
        }
    }


    /**
     * @param roleIds void
     * @throws
     * @Description: 多条删除
     * @author wanghw
     * @date 2018年2月5日
     */
//	@PreAuthorize("hasRole('ROLE_ADMIN')")
    @PreAuthorize("hasAuthority('" + roleIdKey + "')")
    @Transactional(propagation = Propagation.REQUIRED)
    public void deleteRole(String[] roleIds) {
        if (roleIds != null && roleIds.length > 0) {
            for (String id : roleIds) {
                if (id != null && !"".equals(id)) {
                    Role role = this.roleRepository.findOne(id);
                    if (role != null) {
                        if (role.getUsers() != null && role.getUsers().size() > 0) {
                            throw new BusinessException("该角色与用户关联，不能删除！");
                        } else if (role.getMenus() != null && role.getMenus().size() > 0) {
                            throw new BusinessException("该角色与菜单关联，不能删除！");
                        } else {
                            // 标记为已删除-0,未删除-1
                            this.roleRepository.updateFlag(id);
                        }
                    }

                }
            }

        }
    }

    /**
     * 根据ID查询角色
     *
     * @param id
     * @return
     */
    @Transactional(propagation = Propagation.SUPPORTS)
    public RoleDTO findById(String id, String salt) {
        Role role = this.roleRepository.findOne(id);
        return obj2Dto(role, salt);
    }


    /**
     * @param name
     * @return Role
     * @throws
     * @Description: 根据角色名称查询角色详情
     * @author dourl
     * @date 2018年3月26日
     */
    @Transactional(propagation = Propagation.SUPPORTS)
    public RoleDTO findByRoleName(String name) {
        if (!StringUtils.isEmpty(name)) {
            Role role = this.roleRepository.findByRoleNameAndFlag(name, 1);
            return obj2Dto(role);
        }
        return null;
    }


    /**
     * @param userIds
     * @param preUserIds
     * @param roleId     void
     * @throws
     * @Description:保存角色用户的分配
     * @author wanghw
     * @date 2018年2月5日
     */
    @Transactional(propagation = Propagation.REQUIRED)
    public void saveRoleUsers(String[] userIds, String preUserIds, String roleId) {
        String UIDS = StringUtils.join(userIds, ",");
        if (UIDS == null) {
            UIDS = "";
        }
        if (preUserIds == null) {
            preUserIds = "";
        }
        if (userIds == null) {
            userIds = new String[0];
        }
        //所有被选中的ID数组转list
        List<String> userIdList = Arrays.asList(userIds);
        //旧的被选中的数组转list
        List<String> preUserIdList = Arrays.asList(preUserIds.split(","));
        if (roleId != null && roleId.length() > 0) {
            // 求差集
            List<String> differenceSet = userIdList.stream().filter(t -> !preUserIdList.contains(t)).collect(Collectors.toList());
            List<String> differenceSet2 = preUserIdList.stream().filter(t -> !userIdList.contains(t)).collect(Collectors.toList());
            differenceSet.removeAll(differenceSet2);//此处指的是将与l2重复的删除
            differenceSet.addAll(differenceSet2);//此处指加上l2
            for (int i = 0; i < differenceSet.size(); i++) {
                String ssd = differenceSet.get(i);
                //取差集中在旧ID中进行删除
                if (preUserIds.contains(ssd) && preUserIds != null && !"".equals(preUserIds) && !"".equals(ssd) && ssd != null) {
                    RoleUserPK pk = new RoleUserPK();
                    pk.setRoleId(roleId);
                    pk.setUserId(differenceSet.get(i));
                    RoleUser roleUser = new RoleUser();
                    roleUser.setId(pk);
                    this.roleUserRepository.delete(roleUser);
                    //取差集中在新ID中进行存储
                } else if (UIDS.contains(differenceSet.get(i)) && differenceSet.get(i) != null && !"".equals(differenceSet.get(i)) && UIDS != null
                        && !"".equals(userIds)) {
                    RoleUserPK pk = new RoleUserPK();
                    pk.setRoleId(roleId);
                    pk.setUserId(differenceSet.get(i));
                    RoleUser roleUser = new RoleUser();
                    roleUser.setId(pk);
                    this.roleUserRepository.saveAndFlush(roleUser);
                }
            }
        }
    }

    /**
     * @param roleId 户选择的角色id
     * @return 映射的菜单id的字符串，以逗号分隔
     * @throws
     * @Description: 根据roleId查询出关系表中所有的菜单Id
     * @author wanghw
     * @date 2018年2月5日
     */
    @Transactional(propagation = Propagation.SUPPORTS)
    public List<String> findMenuIdsByRoleId(String roleId) {
        List<String> list = this.roleMenuRepository.findMenuIdsByRoleId(roleId);
        return list;
    }


    /**
     * @param menuIds    选择的菜单Id的字符串，以逗号分隔的
     * @param preMenuIds 旧的被选中的ids
     * @param roleId     void  选择的角色Id
     * @throws
     * @Description: 保存角色菜单的分配
     * @author wanghw
     * @date 2018年2月5日
     */
    @Transactional(propagation = Propagation.REQUIRED)
    public void saveRoleMenus(String[] menuIds, String[] preMenuIds, String roleId) {
        String UIDS = StringUtils.join(menuIds, ",");
        if (UIDS == null) {
            UIDS = "";
        }
        if (menuIds == null) {
            menuIds = new String[0];
        }
        if (preMenuIds == null) {
            preMenuIds = new String[]{};
        }
        //所有被选中的ID数组转list
        List<String> userIdList = Arrays.asList(menuIds);
        //旧的被选中的数组转list
        List<String> preUserIdList = Arrays.asList(preMenuIds);

        if (roleId != null && roleId.length() > 0) {
            // 求差集
            List<String> differenceSet = userIdList.stream().filter(t -> !preUserIdList.contains(t)).collect(Collectors.toList());
            List<String> differenceSet2 = preUserIdList.stream().filter(t -> !userIdList.contains(t)).collect(Collectors.toList());
            differenceSet.removeAll(differenceSet2);//此处指的是将与l2重复的删除
            differenceSet.addAll(differenceSet2);//此处指加上l2
            for (int i = 0; i < differenceSet.size(); i++) {
                String ssd = differenceSet.get(i);
                //取差集中在旧ID中进行删除
                if (preUserIdList.contains(ssd) && preMenuIds != null && !"".equals(preMenuIds) && !"".equals(ssd) && ssd != null) {
                    RoleMenuPK pk = new RoleMenuPK();
                    pk.setRoleId(roleId);
                    pk.setMenuId(differenceSet.get(i));
                    RoleMenu roleMenu = new RoleMenu();
                    roleMenu.setId(pk);
                    this.roleMenuRepository.delete(roleMenu);
                    //取差集中在新ID中进行存储
                } else if (UIDS.contains(differenceSet.get(i)) && differenceSet.get(i) != null && !"".equals(differenceSet.get(i)) && UIDS != null && !"".equals(UIDS)) {
                    RoleMenuPK pk = new RoleMenuPK();
                    pk.setRoleId(roleId);
                    pk.setMenuId(differenceSet.get(i));
                    RoleMenu roleMenu = new RoleMenu();
                    roleMenu.setId(pk);
                    this.roleMenuRepository.saveAndFlush(roleMenu);
                }
            }
        }
    }

    /**
     * @param roleName
     * @param id
     * @return boolean
     * @throws
     * @Description: 根据角色名查询
     * @author wanghw
     * @date 2018年2月5日
     */
    @Transactional(propagation = Propagation.SUPPORTS)
    public boolean findByName(String roleName, String id) {
        List<Role> roles = new ArrayList<Role>();
        if (roleName != null && roleName.length() > 0) {
            roles = this.roleRepository.findByRoleName(roleName, id);
            if (roles.size() > 0) {
                return false;
            }
        }
        return true;
    }

    @Transactional(propagation = Propagation.SUPPORTS)
    public List<Role> findRoleByName(String roleName, String id) {
        List<Role> roles = new ArrayList<Role>();
        if (roleName != null && roleName.length() > 0) {
            roles = this.roleRepository.findByRoleName(roleName, id);
            if (roles.size() > 0) {
                return roles;
            }
        }
        return roles;
    }


    /**
     * @param roleList
     * @param salt
     * @return List<RoleDTO>
     * @throws
     * @Description: 集合对象转换
     * @author chixue
     * @date 2017年5月12日
     */
    public List<RoleDTO> objList2DtoList(List<Role> roleList, String salt) {
        List<RoleDTO> dtos = new ArrayList<RoleDTO>();
        for (Role r : roleList) {
            dtos.add(obj2Dto(r, salt));
        }
        return dtos;
    }

    /**
     * @param role
     * @param salt
     * @return RoleDTO
     * @throws
     * @Description: 对象转换
     * @author chixue
     * @date 2017年5月12日
     */
    public RoleDTO obj2Dto(Role role, String salt) {
        RoleDTO dto = new RoleDTO();
        BeanUtils.copyProperties(role, dto);
        dto.generateToken(salt);
        return dto;
    }

    public RoleDTO obj2Dto(Role role) {
        RoleDTO dto = new RoleDTO();
        BeanUtils.copyProperties(role, dto);
        return dto;
    }
}
