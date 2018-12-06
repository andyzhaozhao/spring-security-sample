package cn.com.taiji.service;

import cn.com.taiji.domain.User;
import cn.com.taiji.dto.UserDTO;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;
import java.util.Map;

/**
 * 用户Service
 *
 * @author SunJingyan
 * @date 2014-05-20
 */
public interface UserService {
    /**
     * 查询用户信息
     *
     * @param searchParameters 查询参数的map集合
     * @return 查询的结果, map类型 total:总条数 users:查询结果list集合
     */

    Map getPage(final Map searchParameters, String salt);

    /**
     * @param list
     * @param salt
     * @return List<UserDTO>
     * @throws @author dourl
     * @Description: user集合对象转换为DTO集合
     * @date 2017年5月11日
     */
    List<UserDTO> userList2DtoList(List<User> list, String salt);

    /**
     * @param user
     * @return UserDTO
     * @throws @author dourl
     * @Description: 实体对象转化为前台对象其中包括子对象比如角色、用户
     * @date 2017年5月23日
     */
    UserDTO user2Dto(User user);

    /**
     * @param user
     * @param salt 页面的调用时 必须要传的
     * @return UserDTO
     * @throws @author chixue
     * @Description: user对象转换为DTO对象
     * @date 2017年5月2日
     */
    UserDTO user2Dto(User user, String salt);

    /**
     * @param list void
     * @throws @author chixue
     * @Description: 添加或修改用户信息 按登录名成判断是否已经存在该登录名称
     * @date 2016年5月10日
     */

    void save(List<UserDTO> list);

    /**
     * @param dto
     * @throws @author dourl
     * @Description: 带有角色的用户保存(为community单独的方法)
     * @date 2018年3月22日
     */
    String saveUserWithRole(UserDTO dto, String salt);

    /**
     * @param dto
     * @param avatar
     * @throws IOException
     * @Description: 用户保存方法，保存时自动级联绑定当前用户的角色、机构、用户组
     */
    void save(UserDTO dto, MultipartFile avatar, String salt) throws IOException;

    /**
     * @param list void
     * @throws @author dourl
     * @Description: 保存由Excel导入的数据 保存到数据库
     * @date 2017年5月23日
     */

    String saveExcelUser(List<UserDTO> list);

    /**
     * 修改密码
     *
     * @param userId
     * @param password
     */
    void savePwd(String userId, String password);

    /**
     * @param idList
     * @param tag    状态标识 0-》未激活 1 -》激活
     */
    void changeUserState(List<String> idList, String tag);

    /**
     * 保存用户并返回用户ID
     *
     * @param user
     * @return
     */
    String saveUser(User user);

    /**
     * @param list void
     * @throws @author dourl
     * @Description: 现用的多条删除
     * @date 2018年1月10日
     */
    void deleteUser(List<UserDTO> list);

    void deleteUser(User user);

    /**
     * 查询出所有的用户集合
     *
     * @return
     */
    List<UserDTO> findAllUsers();

    /**
     * @param ids
     * @return List<UserDTO>
     * @throws @author dourl
     * @Description: 根据ID 导出excel
     * @date 2017年5月25日
     */
    List<UserDTO> findUsersByIds(List<String> ids);

    /**
     * @param loginName
     * @param password
     * @param salt
     * @return UserDTO
     * @throws @author dourl
     * @Description: 登录查询
     * @date 2017年5月11日
     */
    UserDTO login(String loginName, String password, String salt);

    /**
     * @param loginName
     * @return UserDTO
     * @throws @author dourl
     * @Description: 根据用户登录名查询用户
     * @date 2017年5月23日
     */
    UserDTO findByLoginName(String loginName);

    /**
     * 根据用户名查询，用于前台验证输入的登录名成是否重复
     *
     * @param
     * @return
     */
    boolean findByName(String loginName, String id);

    /**
     * 按用户名查询用户
     *
     * @param name
     * @return
     */
    UserDTO findByUserName(String name);

    /**
     * @param userId
     * @return User
     * @throws @author chixue
     * @Description: 按用户Id查询用户信息
     * @date 2016年5月11日
     */
    UserDTO findUserById(String userId);

    /**
     * @param userId
     * @param salt
     * @return UserDTO
     * @throws @author chixue
     * @Description: 按用户Id查询用户信息
     * @date 2017年5月2日
     */
    UserDTO findUserById(String userId, String salt);

    /**
     * 保存用户头像
     *
     * @param loginName
     * @param newName
     * @param file
     * @throws IOException
     */
    void saveUserAvatar(String loginName, String newName, MultipartFile file) throws IOException;


    User findOne(String loginName);

    List<String> findUserIdsByRoleId(String roleId);
}