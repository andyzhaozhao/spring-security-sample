package cn.com.taiji.firth.domain;

import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.io.Serializable;
import java.util.List;

/**
 * 类名称：User
 * 类描述： 用户数据库表的持久类
 * 创建人：chixue
 * 创建时间：2016年5月5日 下午2:48:24
 */
@Entity
@Table(name = "user_info")
@NamedQuery(name = "User.findAll", query = "SELECT u FROM User u")
@Data
@NoArgsConstructor
public class User extends BaseDomain implements Serializable {
    private static final long serialVersionUID = -3149974916027750041L;

    @Column(name = "user_name")
    private String userName;     //中文名
    @Column(name = "user_index")
    private Integer userIndex;        //排序索引
    @Column(name = "login_name")
    private String loginName;        //登录名
    private String password;        //密码
    private String email;        //邮箱
    @Column(name = "phone_num")
    private String phoneNum;    //手机
    private String state;//状态
    private String remark;    //个人简介
    private String nickname;    //昵称
    private String gender;        //性别
    private String address;    //居住地
    private String blog;//博客
    private String tag;//标签
    private String avatar;    //头像
    @Lob
    @Column(name = "avatarContent")
    @Basic(fetch = FetchType.LAZY)
    private byte[] avatarContent;
    private String idNumber;    //身份证号
    private String birthday;        //出生日期
    @Column(name = "integral", columnDefinition = "tinyint default 0")
    private Integer integral;        //用户积分
    @ManyToMany(fetch = FetchType.EAGER)
    @JoinTable(name = "role_user", joinColumns = @JoinColumn(name = "user_id"),
            inverseJoinColumns = @JoinColumn(name = "role_id"))
    private List<Role> roles;
}