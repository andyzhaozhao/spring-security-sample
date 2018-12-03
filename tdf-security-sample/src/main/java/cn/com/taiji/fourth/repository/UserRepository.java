package cn.com.taiji.fourth.repository;

import cn.com.taiji.fourth.domain.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * 用户dao
 *
 * @author SunJingyan
 * @date 2014年4月21日
 */
@Repository
public interface UserRepository extends JpaRepository<User, String>, JpaSpecificationExecutor<User>, PagingAndSortingRepository<User, String> {


    List<User> findAll();

    /**
     * 根据用户名称查询用户
     *
     * @param userName
     * @return
     */
    @Query("select u from User u where u.userName =:userName and u.flag=1")
    User findByUserName(@Param("userName") String userName);

    /**
     * 根据登录名称查询用户
     *
     * @param loginName
     * @return
     */
    @Query("select u from User u where u.loginName =:loginName and u.flag=1")
    User findByLoginName(@Param("loginName") String loginName);

    /**
     * 根据登录名称和密码查询用户
     *
     * @param loginName
     * @param password
     * @return
     */
    @Query("SELECT u FROM User u WHERE u.loginName = :loginName and u.password =:password and u.flag=1")
    User login(@Param("loginName") String loginName, @Param("password") String password);

    /**
     * 标记为删除
     *
     * @param id
     * @return
     */

    @Modifying
    @Query("update User u set u.flag=0 where u.id=:id")
    void updateFlag(@Param("id") String id);

    /**
     * 查询所有未标记为删除的用户集合
     *
     * @return
     */
    @Query("select u from User u where u.flag=1  and u.state=1 order by u.userIndex")
    List<User> findAllUsers();

    /**
     * 修改密码
     *
     * @param id
     * @param pwd
     */
    @Modifying
    @Query("update User u set u.password=:pwd where u.id=:id")
    void updatePwd(@Param("id") String id, @Param("pwd") String pwd);

    /**
     * 根据loginName查询出记录
     *
     * @param loginName
     * @return
     */
    @Query("select u from User u where u.loginName = :loginName and u.id!= :id and u.flag=1")
    List<User> findByUserName(@Param("loginName") String loginName, @Param("id") String id);

    /**
     * 根据登录名称查询用户
     *
     * @param loginName
     * @return
     */
    @Query("select u from User u where u.loginName =:loginName and u.flag=1")
    User findByAvatarContent(@Param("loginName") String loginName);

    /**
     * 修改头像
     *
     * @param loginName
     * @param avatarContent
     */
    @Modifying
    @Query("update User u set u.avatarContent=:avatarContent,u.avatar=:avatar  where u.loginName=:loginName")
    void updateAvatar(@Param("loginName") String loginName, @Param("avatar") String avatar, @Param("avatarContent") byte[] avatarContent);


    /**
     * 根据机构ID和角色ID查询用户集合
     * @param roleId
     * @return
     */
    @Query("select u.id from Role r JOIN r.users u  where r.id = :roleId")
    List<String> findUserIdsByRoleId(@Param("roleId") String roleId);
}
