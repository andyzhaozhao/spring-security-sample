package cn.com.taiji.third.domain;

import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.util.List;

@Data
@NoArgsConstructor
public class User extends Audited implements Serializable {
    private static final long serialVersionUID = -3149974916027750041L;
    private String user_name;     //中文名
    private Integer user_index;        //排序索引
    private String login_name;        //登录名
    private String password;        //密码
    private String email;        //邮箱
    private String phone_num;    //手机
    private String state;//状态
    private String remark;    //个人简介
    private String nickname;    //昵称
    private String gender;        //性别
    private String address;    //居住地
    private String blog;//博客
    private String tag;//标签
    private String avatar;    //头像
    private byte[] avatar_content;
    private String id_number;    //身份证号
    private String birthday;        //出生日期
    private Integer integral;        //用户积分

    private List<Role> roles;
}