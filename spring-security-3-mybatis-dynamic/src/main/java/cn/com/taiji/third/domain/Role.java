package cn.com.taiji.third.domain;

import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

@Data
@NoArgsConstructor
public class Role extends Audited implements Serializable {
    private static final long serialVersionUID = 4641095039785783252L;
    private String role_description;
    private Integer role_index;
    private String role_name;
    private Integer show_users;

    public static long getSerialVersionUID() {
        return serialVersionUID;
    }

    public String getRole_description() {
        return role_description;
    }

    public void setRole_description(String role_description) {
        this.role_description = role_description;
    }

    public Integer getRole_index() {
        return role_index;
    }

    public void setRole_index(Integer role_index) {
        this.role_index = role_index;
    }

    public String getRole_name() {
        return role_name;
    }

    public void setRole_name(String role_name) {
        this.role_name = role_name;
    }

    public Integer getShow_users() {
        return show_users;
    }

    public void setShow_users(Integer show_users) {
        this.show_users = show_users;
    }
}