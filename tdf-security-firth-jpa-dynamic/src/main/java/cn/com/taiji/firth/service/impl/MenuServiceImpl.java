package cn.com.taiji.firth.service.impl;

import cn.com.taiji.firth.domain.Menu;
import cn.com.taiji.firth.domain.Role;
import cn.com.taiji.firth.dto.MenuDTO;
import cn.com.taiji.firth.exception.BusinessException;
import cn.com.taiji.firth.repository.MenuRepository;
import cn.com.taiji.firth.service.MenuService;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by andyzhao on 2018/7/30.
 */
@Service
public class MenuServiceImpl implements MenuService {
    private static final Logger log = LoggerFactory.getLogger(MenuService.class);

    @Autowired
    private MenuRepository menuRepository;

    public final String roleIdKey = "5b66ecf45d634159a08468898b1b3217";

    /**
     * 查询菜单信息
     *
     * @param searchParameters 查询参数的map集合
     * @return 查询的结果, map类型 total:总条数 menus:查询结果list集合
     */
    @Transactional(propagation = Propagation.REQUIRED)
    public Map getPage(final Map searchParameters, String salt) {
        Map map = new HashMap();
        int page = 0;
        int pageSize = 10;
        Page<Menu> pageList;
        // Page<MenuDTO> pageDtoList;
        if (searchParameters != null && searchParameters.size() > 0
                && searchParameters.get("page") != null) {
            page = Integer.parseInt(searchParameters.get("page").toString()) - 1;
        }
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
        List<Sort.Order> orders = new ArrayList<Sort.Order>();
        if (orderMaps != null) {
            for (Map m : orderMaps) {
                if (m.get("field") == null)
                    continue;
                String field = m.get("field").toString();
                if (!StringUtils.isEmpty(field)) {
                    String dir = m.get("dir").toString();
                    if ("DESC".equalsIgnoreCase(dir)) {
                        orders.add(new Sort.Order(Sort.Direction.DESC, field));
                    } else {
                        orders.add(new Sort.Order(Sort.Direction.ASC, field));
                    }
                }
            }
        }
        PageRequest pageable;
        if (orders.size() > 0) {
            pageable = new PageRequest(page, pageSize, new Sort(orders));
        } else {
            Sort s = new Sort(Sort.Direction.ASC, "menuIndex");
            pageable = new PageRequest(page, pageSize, s);
        }
        Map filter = (Map) searchParameters.get("filter");
        if (filter != null) {
            // String logic = filter.get("logic").toString();
            final List<Map> filters = (List<Map>) filter.get("filters");
            Specification<Menu> spec = new Specification<Menu>() {
                @Override
                public Predicate toPredicate(Root<Menu> root,
                                             CriteriaQuery<?> query, CriteriaBuilder cb) {
                    List<Predicate> pl = new ArrayList<Predicate>();
                    for (Map f : filters) {
                        // String operator = ((String)
                        // f.get("operator")).trim();
                        String field = f.get("field").toString().trim();
                        String value = f.get("value").toString().trim();
                        if (value != null && value.length() > 0) {
                            if ("menuIndex".equalsIgnoreCase(field)) {
                                pl.add(cb.equal(root.<String>get(field), value));
                            }
                            if ("menuName".equalsIgnoreCase(field)) {
                                pl.add(cb.like(root.<String>get(field), "%"
                                        + value + "%"));
                            } else if ("menuUrl".equalsIgnoreCase(field)) {
                                pl.add(cb.like(root.<String>get(field), "%"
                                        + value + "%"));
                            } else if ("menuDesc".equalsIgnoreCase(field)) {
                                pl.add(cb.like(root.<String>get(field), "%"
                                        + value + "%"));
                            } else if ("parentId".equalsIgnoreCase(field)) {
                                Menu parent = menuRepository.findOne(value);
                                pl.add(cb.equal(root.<String>get("parent"), parent));
                            } else {

                            }
                        }

                    }
                    // 查询出未删除的
                    pl.add(cb.equal(root.<Integer>get("flag"), 1));
                    return cb.and(pl.toArray(new Predicate[0]));
                }
            };
            pageList = menuRepository.findAll(spec, pageable);

        } else {
            Specification<Menu> spec = new Specification<Menu>() {
                public Predicate toPredicate(Root<Menu> root,
                                             CriteriaQuery<?> query, CriteriaBuilder cb) {
                    List<Predicate> list = new ArrayList<Predicate>();
                    // 查询出未删除的
                    list.add(cb.equal(root.<Integer>get("flag"), 1));
                    return cb.and(list.toArray(new Predicate[0]));
                }
            };
            pageList = menuRepository.findAll(spec, pageable);

        }
        map.put("total", pageList.getTotalElements());
        map.put("menus", object2DtoList(pageList.getContent()));
        return map;
    }


    /**
     * @param menu
     * @param salt
     * @return
     */

    public MenuDTO menu2Dto(Menu menu, String salt) {

        MenuDTO dto = new MenuDTO();
        BeanUtils.copyProperties(menu, dto);
        dto.generateToken(salt);
        if (menu != null && menu.getParent() != null
                && menu.getParent().getId() != null) {
            dto.setParentId(menu.getParent().getId());
            dto.setParentName(menu.getParent().getMenuName());
        }
        if (menu.getChildren() != null
                && !menu.getChildren().isEmpty()) {
            dto.setChildren(object2DtoList(menu.getChildren()));
        }
        return dto;
    }


    /**
     * @param dto
     * @throws
     * @Description: 添加和编辑菜单信息
     * @author dourl
     * @date 2017年6月6日
     */
    @Transactional(propagation = Propagation.REQUIRED)
    public MenuDTO saveMenu(MenuDTO dto, String salt) {
        if (dto != null) {
            if (!dto.tokenKeeped(salt)) {
                throw new BusinessException("您无权修改！");
            }
            Menu menu = new Menu();
            BeanUtils.copyProperties(dto, menu);
            if (dto.getParentId() != null && dto.getParentId().length() > 0) {
                Menu parent = this.menuRepository.findOne(dto.getParentId());
                menu.setParent(parent);
            }
            if (dto.getChildren() != null && !dto.getChildren().isEmpty() && dto.getChildren().size() > 0) {
                menu.setChildren(dto2Object(dto.getChildren()));
            }
            menu = this.menuRepository.saveAndFlush(menu);
            return menu2Dto(menu, salt);


        }
        return null;

    }

    /**
     * 多条删除
     *
     * @param list
     */
//	@PreAuthorize("hasRole('ROLE_ADMIN')")
    @PreAuthorize("hasAuthority('" + roleIdKey + "')")
    @Transactional(propagation = Propagation.REQUIRED)
    public void deleteMenu(List<MenuDTO> list) {
        if (list != null && list.size() > 0) {
            for (MenuDTO d : list) {
                //根据ID 查询
                if (d.getId() != null && d.getId().length() > 0) {
                    Menu m = findById(d.getId());
                    if (m.getChildren() != null && m.getChildren().size() > 0) {
                        throw new BusinessException("该菜单下面有子菜单，不能删除!");
                    } else if (m.getRoles() != null && m.getRoles().size() > 0) {
                        throw new BusinessException("该菜单与角色关联，不能删除!");
                    } else {
                        String id = m.getId();
                        this.menuRepository.updateFlag(id);
                    }
                }

            }
        }
    }

    /**
     * 根据ID查询菜单
     *
     * @param id
     * @return
     */
    @Transactional(propagation = Propagation.SUPPORTS)
    public Menu findById(String id) {
        return this.menuRepository.findOne(id);
    }

    /**
     * @param id
     * @param salt
     * @return MenuDTO
     * @throws
     * @Description: 根据 id 返回 菜单DTO对象
     * @author dourl
     * @date 2017年6月6日
     */
    @Transactional(propagation = Propagation.SUPPORTS)
    public MenuDTO findMenuById(String id, String salt) {
        Menu menu = this.menuRepository.findOne(id);
        return menu2Dto(menu, salt);
    }

    /**
     * 查询出所有的菜单集合
     *
     * @return
     */
    @Transactional(propagation = Propagation.SUPPORTS)
    public List<MenuDTO> findAllMenus() {
        List<Menu> AllMenus = this.menuRepository.findAllMenus();
        List<MenuDTO> list = new ArrayList<MenuDTO>();
        if (AllMenus != null && AllMenus.size() > 0) {
            for (Menu u : AllMenus) {
                MenuDTO dto = new MenuDTO();
                BeanUtils.copyProperties(u, dto);
                if (u != null && u.getParent() != null
                        && u.getParent().getId() != null) {
                    dto.setParentId(u.getParent().getId());
                    dto.setParentName(u.getParent().getMenuName());
                }
                list.add(dto);
            }
        }
        MenuDTO nullMenu = new MenuDTO();
        nullMenu.setId(null);
        nullMenu.setMenuName("无");
        list.add(nullMenu);
        return list;
    }

    /**
     * 上传菜单图标文件
     * 暂时无用
     *
     * @param menuId
     * @param iconPath
     */
    @Transactional(propagation = Propagation.REQUIRED)
    public void uploadPic(String menuId, byte[] iconPath,
                          UserDetails userDetails) {

        Menu menu = null;
        if (menuId != null) {
            menu = this.menuRepository.findOne(menuId);
        }
        if (menu != null && iconPath != null && iconPath.length > 0) {
            menu.setIconPath(iconPath);
            this.menuRepository.saveAndFlush(menu);
        }
    }

    /**
     * 获得认证获得被授权树
     * 暂时没有
     *
     * @return
     */
    @Transactional(propagation = Propagation.SUPPORTS)
    public List<MenuDTO> findRootByAuthorization(List<Role> roles) {
        List<Menu> AllMenus = this.menuRepository.findRootByAuthorization(roles);
        List<MenuDTO> list = new ArrayList<MenuDTO>();
        if (AllMenus != null && AllMenus.size() > 0) {
            for (Menu u : AllMenus) {
                MenuDTO dto = new MenuDTO();
                BeanUtils.copyProperties(u, dto);
                if (u != null && u.getParent() != null
                        && u.getParent().getId() != null) {
                    dto.setParentId(u.getParent().getId());
                    dto.setParentName(u.getParent().getMenuName());
                }
                list.add(dto);
            }
        }
        MenuDTO nullMenu = new MenuDTO();
        nullMenu.setId(null);
        nullMenu.setMenuName("无");
        list.add(nullMenu);
        return list;
    }

    /**
     * 获得认证获得被授权树
     *
     * @return
     */
    @Transactional(propagation = Propagation.SUPPORTS)
    public List<Menu> findRootByAuthorization(List<Role> roles, String rootId) {
        return this.menuRepository
                .findMenuByRootAndAuthorization(roles, rootId);
    }

    /**
     * 根据节点ID获得树
     *
     * @param rootId
     * @return
     */
    @Transactional(propagation = Propagation.SUPPORTS)
    public List<MenuDTO> findMenuByRootId(String rootId) {
        List<Menu> AllMenus = this.menuRepository.findMenuByRootId(rootId);
        List<MenuDTO> list = new ArrayList<MenuDTO>();
        if (AllMenus != null && AllMenus.size() > 0) {
            for (Menu u : AllMenus) {
                MenuDTO dto = new MenuDTO();
                BeanUtils.copyProperties(u, dto);
                if (u != null && u.getParent() != null
                        && u.getParent().getId() != null) {
                    dto.setParentId(u.getParent().getId());
                    dto.setParentName(u.getParent().getMenuName());
                }
                list.add(dto);
            }
        }
        return list;
    }

    /**
     * 根据标示获得树
     *
     * @param mark
     * @return
     */
    @Transactional(propagation = Propagation.SUPPORTS)
    public List<MenuDTO> findMenuByMark(String mark) {
        List<Menu> menus = this.menuRepository.findMenuByMark(mark);
        List<MenuDTO> list = new ArrayList();
        for (Menu menu : menus) {
            MenuDTO dto = new MenuDTO();
            BeanUtils.copyProperties(menu, dto);
            list.add(dto);
        }

        return list;
    }

    /**
     * 获得根节点菜单
     *
     * @return
     */

    @Transactional(propagation = Propagation.SUPPORTS)
    public List<MenuDTO> findRootTree() {
        return object2DtoList(this.menuRepository.findByFlagAndParentIsNullOrderByMenuIndexAsc(1));
    }

    /**
     * @param list
     * @return List<MenuDTO>
     * @throws
     * @Description: 集合对象转换为实体
     * @author chixue
     * @date 2016年10月20日
     */
    public List<MenuDTO> object2DtoList(List<Menu> list) {

        List<MenuDTO> newList = new ArrayList<MenuDTO>();
        if (list != null && list.size() > 0) {
            for (Menu menu : list) {
                MenuDTO dto = new MenuDTO();
                BeanUtils.copyProperties(menu, dto);
                if (menu.getParent() != null
                        && menu.getParent().getId().length() > 0) {
                    dto.setParentId(menu.getParent().getId());
                    dto.setParentName(menu.getParent().getMenuName());
                }
                if (menu.getChildren() != null
                        && !menu.getChildren().isEmpty() && menu.getChildren().size() > 0) {
                    dto.setChildren(object2DtoList(menu.getChildren()));
                }
                newList.add(dto);
            }
        }
        return newList;
    }


    /**
     * @param list
     * @return List<Menu>
     * @throws
     * @Description: DTO转OBJ 编辑保存使用修改条件判断
     * @author dourl
     * @date 2017年6月15日
     */
    public List<Menu> dto2Object(List<MenuDTO> list) {
        List<Menu> newList = new ArrayList<Menu>();
        if (list != null && list.size() > 0) {
            for (MenuDTO dto : list) {
                Menu menu = new Menu();
                BeanUtils.copyProperties(dto, menu);

                if (StringUtils.isNoneEmpty(dto.getParentId())) {
                    Menu parent = this.menuRepository.findOne(dto.getParentId());
                    if (null != parent) {
                        menu.setParent(parent);
                    }
                }
                if (dto.getChildren() != null
                        && !dto.getChildren().isEmpty() && dto.getChildren().size() > 0) {
                    menu.setChildren(dto2Object(dto.getChildren()));
                }
                newList.add(menu);
            }
        }
        return newList;
    }


    /**
     * 查询出更新后的数据
     *
     * @param list
     * @return
     */
    @Transactional(propagation = Propagation.SUPPORTS)
    public List<MenuDTO> findMenus(List<MenuDTO> list, String salt) {
        List<MenuDTO> newList = new ArrayList<MenuDTO>();
        if (list != null && list.size() > 0) {
            for (MenuDTO d : list) {
                String id = d.getId();
                Menu menu = null;
                if (id != null && id.length() > 0) {
                    menu = this.menuRepository.findOne(id);
                }
                if (menu != null) {
                    MenuDTO dto = new MenuDTO();
                    BeanUtils.copyProperties(menu, dto);
                    if (menu.getParent() != null
                            && menu.getParent().getId() != null) {
                        dto.setParentId(menu.getParent().getId());
                        dto.setParentName(menu.getParent().getMenuName());
                    }
                    dto.generateToken(salt);
                    newList.add(dto);
                }
            }
        }
        return newList;
    }

    /**
     * 根据角色Id获得被授权的菜单信息
     *
     * @param roleId
     * @return
     */
    public List<MenuDTO> getMenuListByRoleId(String roleId) {
        List<Menu> menuList = this.menuRepository.getMenuListByRole(roleId);
        return object2DtoList(menuList);
    }


}
