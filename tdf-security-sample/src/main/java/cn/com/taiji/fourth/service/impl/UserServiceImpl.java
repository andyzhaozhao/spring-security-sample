package cn.com.taiji.fourth.service.impl;

import cn.com.taiji.fourth.domain.Role;
import cn.com.taiji.fourth.domain.User;
import cn.com.taiji.fourth.dto.RoleDTO;
import cn.com.taiji.fourth.dto.UserDTO;
import cn.com.taiji.fourth.exception.BusinessException;
import cn.com.taiji.fourth.repository.UserRepository;
import cn.com.taiji.fourth.service.RoleService;
import cn.com.taiji.fourth.service.UserService;
import org.apache.commons.codec.digest.DigestUtils;
import org.apache.commons.lang3.StringUtils;
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
import org.springframework.web.multipart.MultipartFile;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;
import java.io.IOException;
import java.util.*;

/**
 * 用户Service
 *
 * @author SunJingyan
 * @date 2014-05-20
 */
@Service
public class UserServiceImpl implements UserService {
    @Autowired
    private UserRepository userRepository;

    @Autowired
    private RoleService roleService;

    public final String roleIdKey = "5b66ecf45d634159a08468898b1b3217";

    /**
     * 查询用户信息
     *
     * @param searchParameters 查询参数的map集合
     * @return 查询的结果, map类型 total:总条数 users:查询结果list集合
     */

    @Transactional(propagation = Propagation.SUPPORTS)
    public Map getPage(final Map searchParameters, String salt) {
        Map map = new HashMap();
        int page = 0;
        int pageSize = 10;
        Page<User> pageList;
        if (searchParameters != null && searchParameters.size() > 0 && searchParameters.get("page") != null) {
            page = Integer.parseInt(searchParameters.get("page").toString()) - 1;
        }
        if (searchParameters != null && searchParameters.size() > 0 && searchParameters.get("pageSize") != null) {
            pageSize = Integer.parseInt(searchParameters.get("pageSize").toString());
        }
        if (pageSize < 1)
            pageSize = 1;
        if (pageSize > 100)
            pageSize = 100;
        List<Map> orderMaps = (List<Map>) searchParameters.get("sort");
        List<Order> orders = new ArrayList<Order>();
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
            Sort s = new Sort(Direction.ASC, "userIndex");
            pageable = new PageRequest(page, pageSize, s);
        }
        Map filter = (Map) searchParameters.get("filter");
        if (filter != null) {
            final List<Map> filters = (List<Map>) filter.get("filters");
            Specification<User> spec = new Specification<User>() {
                @Override
                public Predicate toPredicate(Root<User> root, CriteriaQuery<?> query, CriteriaBuilder cb) {
                    List<Predicate> pl = new ArrayList<Predicate>();
                    for (Map f : filters) {
                        String field = f.get("field").toString().trim();
                        String value = f.get("value").toString().trim();
                        if (value != null && value.length() > 0) {
                            if ("loginName".equalsIgnoreCase(field)) {
                                pl.add(cb.equal(root.<String>get(field), value));
                            } else if ("userName".equalsIgnoreCase(field)) {
                                pl.add(cb.like(root.<String>get(field), value + "%"));
                            } else if ("email".equalsIgnoreCase(field)) {
                                pl.add(cb.like(root.<String>get(field), value + "%"));
                            } else if ("state".equalsIgnoreCase(field)) {
                                pl.add(cb.equal(root.<Integer>get("state"), value));
                            }
                        }
                    }
                    // 查询出未删除的
                    pl.add(cb.equal(root.<Integer>get("flag"), 1));
                    // pl.add(cb.equal(root.<Integer>get("state"), 1));
                    return cb.and(pl.toArray(new Predicate[0]));
                }
            };
            pageList = userRepository.findAll(spec, pageable);

        } else {
            Specification<User> spec = new Specification<User>() {
                public Predicate toPredicate(Root<User> root, CriteriaQuery<?> query, CriteriaBuilder cb) {
                    List<Predicate> list = new ArrayList<Predicate>();
                    // 查询出未删除的
                    list.add(cb.equal(root.<Integer>get("flag"), 1));
                    list.add(cb.equal(root.<Integer>get("state"), 1));
                    return cb.and(list.toArray(new Predicate[0]));
                }
            };
            pageList = userRepository.findAll(spec, pageable);

        }

        map.put("total", pageList.getTotalElements());
        map.put("users", userList2DtoList(pageList.getContent(), salt));
        return map;
    }

    /**
     * @param list
     * @param salt
     * @return List<UserDTO>
     * @throws @author dourl
     * @Description: user集合对象转换为DTO集合
     * @date 2017年5月11日
     */
    @Transactional(propagation = Propagation.SUPPORTS)
    public List<UserDTO> userList2DtoList(List<User> list, String salt) {
        List<UserDTO> dtos = new ArrayList<UserDTO>();
        if (list != null && list.size() > 0) {
            for (User d : list) {
                dtos.add(user2Dto(d, salt));
            }
        }
        return dtos;
    }

    /**
     * @param user
     * @return UserDTO
     * @throws @author dourl
     * @Description: 实体对象转化为前台对象其中包括子对象比如角色、用户
     * @date 2017年5月23日
     */
    @Transactional(propagation = Propagation.SUPPORTS)
    public UserDTO user2Dto(User user) {
        UserDTO userDto = new UserDTO();
        BeanUtils.copyProperties(user, userDto);
        List<Role> roleList = user.getRoles();
        if (roleList != null && !roleList.isEmpty()) {
            Set set = new HashSet(roleService.objList2DtoList(roleList, ""));
            userDto.setRoles(set);
        }

        return userDto;
    }

    /**
     * @param user
     * @param salt 页面的调用时 必须要传的
     * @return UserDTO
     * @throws @author chixue
     * @Description: user对象转换为DTO对象
     * @date 2017年5月2日
     */
    @Transactional(propagation = Propagation.SUPPORTS)
    public UserDTO user2Dto(User user, String salt) {
        UserDTO userDto = new UserDTO();
        BeanUtils.copyProperties(user, userDto);
        userDto.generateToken(salt);
        List<Role> roleList = user.getRoles();
        if (roleList != null && !roleList.isEmpty()) {
            Set set = new HashSet(roleService.objList2DtoList(roleList, ""));
            userDto.setRoles(set);
        }
        return userDto;

    }

    /**
     * @param list void
     * @throws @author chixue
     * @Description: 添加或修改用户信息 按登录名成判断是否已经存在该登录名称
     * @date 2016年5月10日
     */
    @Transactional(propagation = Propagation.REQUIRED)
    public void save(List<UserDTO> list) {
        if (null != list && list.size() > 0) {
            for (UserDTO dto : list) {
                if (dto.getId() == null) {
                    dto.setId("");
                }
                if (!findByName(dto.getLoginName(), dto.getId())) {
                    throw new BusinessException("登录名[" + dto.getLoginName() + "]已存在！");
                }
                User user = new User();
                BeanUtils.copyProperties(dto, user);
                if (StringUtils.isEmpty(dto.getPassword())) {
                    // 设置默认密码
                    user.setPassword(DigestUtils.sha256Hex("123456"));
                }
                saveUser(user);

            }
        }
    }

    /**
     * @param dto
     * @throws @author dourl
     * @Description: 带有角色的用户保存(为community单独的方法)
     * @date 2018年3月22日
     */
    @Transactional(propagation = Propagation.REQUIRED)
    public String saveUserWithRole(UserDTO dto, String salt) {
        String id = "";
        if (null != dto) {

            if (dto.getId() == null) {
                dto.setId("");
            }
            //这里加token判断
            if (!dto.tokenKeepedForCommunity(salt)) {
                throw new BusinessException("您无权修改！");
            }
            if (!findByName(dto.getLoginName(), dto.getId())) {
                throw new BusinessException("登录名[" + dto.getLoginName() + "]已存在！");
            }

            User user = new User();

            BeanUtils.copyProperties(dto, user);

            if (!StringUtils.isEmpty(dto.getId())) {
                User u = this.userRepository.findOne(dto.getId());

                if (u != null) {
                    user.setCreatedDate(u.getCreatedDate());
                    user.setLoginName(u.getLoginName());
                    user.setEmail(u.getEmail());
                }

            }
            List<Role> roleList = new ArrayList<>();
            if (null != dto.getRoles() && dto.getRoles().size() > 0) {
                for (RoleDTO roleDto : dto.getRoles()) {
                    Role role = new Role();
                    BeanUtils.copyProperties(roleDto, role);
                    roleList.add(role);
                }
            }
            if (!roleList.isEmpty() && roleList.size() > 0) {
                user.setRoles(roleList);
            }

            if (StringUtils.isEmpty(dto.getPassword())) {
                // 设置默认密码
                user.setPassword(DigestUtils.sha256Hex("123456"));
            }

            id = saveUser(user);

        }
        return id;

    }

    /**
     * @param dto
     * @param avatar
     * @throws IOException
     * @Description: 用户保存方法，保存时自动级联绑定当前用户的角色、机构、用户组
     */
    @Transactional(propagation = Propagation.REQUIRED)
    public void save(UserDTO dto, MultipartFile avatar, String salt) throws IOException {

        if (!findByName(dto.getLoginName(), dto.getId())) {
            throw new BusinessException("登录名[" + dto.getLoginName() + "]已存在！");
        }

        if (!dto.tokenKeeped(salt)) {
            throw new BusinessException("您无权修改！");
        }
        User user = new User();
        BeanUtils.copyProperties(dto, user);
        if (StringUtils.isEmpty(dto.getPassword())) {
            // 设置默认密码
            user.setPassword(DigestUtils.sha256Hex("123456"));
        }

        String id = saveUser(user);

    }

    /**
     * @param list void
     * @throws @author dourl
     * @Description: 保存由Excel导入的数据 保存到数据库
     * @date 2017年5月23日
     */

    @Transactional(propagation = Propagation.REQUIRED)
    public String saveExcelUser(List<UserDTO> list) {
        StringBuffer result = new StringBuffer();
        if (null != list && list.size() > 0) {
            for (UserDTO dto : list) {
                if (dto.getId() == null) {
                    dto.setId("");
                }
                if (!findByName(dto.getLoginName(), dto.getId())) {
                    result.append(dto.getLoginName() + "-");
                } else {
                    User user = new User();
                    BeanUtils.copyProperties(dto, user);
                    // 设置默认密码
                    user.setPassword(DigestUtils.sha256Hex("123456"));
                    saveUser(user);

                }

            }

        }
        return result.toString();
    }

    /**
     * 修改密码
     *
     * @param userId
     * @param password
     */
    @Transactional(propagation = Propagation.REQUIRED)
    public void savePwd(String userId, String password) {
        User user = userRepository.findOne(userId);
        if (null != user) {
            password = DigestUtils.sha256Hex(password);
            user.setPassword(password);
        }
        saveUser(user);
    }

    /**
     * @param idList
     * @param tag    状态标识 0-》未激活 1 -》激活
     */
    @Transactional(propagation = Propagation.REQUIRED)
    public void changeUserState(List<String> idList, String tag) {
        for (String id : idList) {
            User user = userRepository.findOne(id);
            if (null != user) {
                if (StringUtils.isNotEmpty(tag)) {
                    user.setState(tag);
                }
            }
            saveUser(user);
        }

    }


    /**
     * 保存用户并返回用户ID
     *
     * @param user
     * @return
     */
    @Transactional(propagation = Propagation.REQUIRED)
    public String saveUser(User user) {
        // 修改时添加相关联
        if (!StringUtils.isEmpty(user.getId())) {
            User u = this.userRepository.findOne(user.getId());
            if (u != null) {
                if (null != u.getRoles() && u.getRoles().size() > 0) {
                    user.setRoles(u.getRoles());
                }
            }
        }
        return this.userRepository.save(user).getId();
    }

    /**
     * @param list void
     * @throws @author dourl
     * @Description: 现用的多条删除
     * @date 2018年1月10日
     */
//	@PreAuthorize("hasRole('ROLE_ADMIN')")
    @PreAuthorize("hasAuthority('" + roleIdKey + "')")
    @Transactional(propagation = Propagation.REQUIRED)
    public void deleteUser(List<UserDTO> list) {
        if (list != null && list.size() > 0) {
            for (UserDTO dto : list) {
                if (StringUtils.isNotEmpty(dto.getId())) {
                    User user = this.userRepository.findOne(dto.getId());
                    if (user.getRoles() != null && user.getRoles().size() > 0) {
                        throw new BusinessException("该用户与角色关联，不能删除！");
                    } else {
                        deleteUser(user);
                    }
                }

            }
        }
    }

    @Transactional(propagation = Propagation.REQUIRED)
    public void deleteUser(User user) {
        this.userRepository.updateFlag(user.getId());

    }

    /**
     * 查询出所有的用户集合
     *
     * @return
     */
    @Transactional(propagation = Propagation.SUPPORTS)
    public List<UserDTO> findAllUsers() {
        List<User> AllUsers = this.userRepository.findAllUsers();
        List<UserDTO> list = new ArrayList<UserDTO>();
        if (AllUsers != null && AllUsers.size() > 0) {
            for (User user : AllUsers) {
                if (user.getState().equals("0")) {
                    continue;
                }
                UserDTO dto = new UserDTO();
                dto = (user == null) ? null : user2Dto(user);
                list.add(dto);
            }
        }
        return list;
    }

    /**
     * @param ids
     * @return List<UserDTO>
     * @throws @author dourl
     * @Description: 根据ID 导出excel
     * @date 2017年5月25日
     */
    @Transactional(propagation = Propagation.SUPPORTS)
    public List<UserDTO> findUsersByIds(List<String> ids) {
        List<UserDTO> list = new ArrayList<UserDTO>();
        if (ids != null && ids.size() > 0) {
            for (String id : ids) {
                UserDTO userDto = findUserById(id);
                list.add(userDto);
            }

        }
        return list;
    }

    /**
     * @param loginName
     * @param password
     * @param salt
     * @return UserDTO
     * @throws @author dourl
     * @Description: 登录查询
     * @date 2017年5月11日
     */
    @Transactional(propagation = Propagation.SUPPORTS)
    public UserDTO login(String loginName, String password, String salt) {
        User user = userRepository.login(loginName, password);
        return (user == null) ? null : user2Dto(user);
    }

    /**
     * @param loginName
     * @return UserDTO
     * @throws @author dourl
     * @Description: 根据用户登录名查询用户
     * @date 2017年5月23日
     */
    @Transactional(propagation = Propagation.SUPPORTS)
    public UserDTO findByLoginName(String loginName) {
        User user = this.userRepository.findByLoginName(loginName);
        return (user != null) ? user2Dto(user) : null;
    }

    /**
     * 根据用户名查询，用于前台验证输入的登录名成是否重复
     *
     * @param
     * @return
     */
    @Transactional(propagation = Propagation.SUPPORTS)
    public boolean findByName(String loginName, String id) {
        List<User> users = new ArrayList<User>();
        if (loginName != null && loginName.length() > 0) {
            users = this.userRepository.findByUserName(loginName.trim(), id);
            if (users.size() > 0) {
                return false;
            }
        }
        return true;
    }

    /**
     * 按用户名查询用户
     *
     * @param name
     * @return
     */
    @Transactional(propagation = Propagation.SUPPORTS)
    public UserDTO findByUserName(String name) {
        User user = this.userRepository.findByUserName(name);
        return user2Dto(user);
    }

    /**
     * @param userId
     * @return User
     * @throws @author chixue
     * @Description: 按用户Id查询用户信息
     * @date 2016年5月11日
     */
    @Transactional(propagation = Propagation.SUPPORTS)
    public UserDTO findUserById(String userId) {
        User user = userRepository.findOne(userId);
        if (user != null) {
            return user2Dto(user);
        }
        return null;
    }

    /**
     * @param userId
     * @param salt
     * @return UserDTO
     * @throws @author chixue
     * @Description: 按用户Id查询用户信息
     * @date 2017年5月2日
     */
    @Transactional(propagation = Propagation.SUPPORTS)
    public UserDTO findUserById(String userId, String salt) {
        User user = userRepository.findOne(userId);
        if (user != null) {
            return user2Dto(user, salt);
        }
        return null;
    }

    /**
     * 保存用户头像
     *
     * @param loginName
     * @param newName
     * @param file
     * @throws IOException
     */
    @Transactional(propagation = Propagation.REQUIRED)
    public void saveUserAvatar(String loginName, String newName, MultipartFile file) throws IOException {
        byte[] avatarContent = null;
        avatarContent = file.getBytes();
        this.userRepository.updateAvatar(loginName, newName, avatarContent);
    }


    @Transactional(propagation = Propagation.SUPPORTS)
    public User findOne(String loginName) {
        User user = this.userRepository.findByLoginName(loginName);
        return user;
    }

    @Override
    public List<String> findUserIdsByRoleId(String roleId) {
        List<String> userIds = userRepository.findUserIdsByRoleId(roleId);
        return userIds;
    }
}