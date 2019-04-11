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

    public static long getSerialVersionUID() {
        return serialVersionUID;
    }

    public String getUser_name() {
        return user_name;
    }

    public void setUser_name(String user_name) {
        this.user_name = user_name;
    }

    public Integer getUser_index() {
        return user_index;
    }

    public void setUser_index(Integer user_index) {
        this.user_index = user_index;
    }

    public String getLogin_name() {
        return login_name;
    }

    public void setLogin_name(String login_name) {
        this.login_name = login_name;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPhone_num() {
        return phone_num;
    }

    public void setPhone_num(String phone_num) {
        this.phone_num = phone_num;
    }

    public String getState() {
        return state;
    }

    public void setState(String state) {
        this.state = state;
    }

    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark;
    }

    public String getNickname() {
        return nickname;
    }

    public void setNickname(String nickname) {
        this.nickname = nickname;
    }

    public String getGender() {
        return gender;
    }

    public void setGender(String gender) {
        this.gender = gender;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getBlog() {
        return blog;
    }

    public void setBlog(String blog) {
        this.blog = blog;
    }

    public String getTag() {
        return tag;
    }

    public void setTag(String tag) {
        this.tag = tag;
    }

    public String getAvatar() {
        return avatar;
    }

    public void setAvatar(String avatar) {
        this.avatar = avatar;
    }

    public byte[] getAvatar_content() {
        return avatar_content;
    }

    public void setAvatar_content(byte[] avatar_content) {
        this.avatar_content = avatar_content;
    }

    public String getId_number() {
        return id_number;
    }

    public void setId_number(String id_number) {
        this.id_number = id_number;
    }

    public String getBirthday() {
        return birthday;
    }

    public void setBirthday(String birthday) {
        this.birthday = birthday;
    }

    public Integer getIntegral() {
        return integral;
    }

    public void setIntegral(Integer integral) {
        this.integral = integral;
    }

    public List<Role> getRoles() {
        return roles;
    }

    public void setRoles(List<Role> roles) {
        this.roles = roles;
    }
}