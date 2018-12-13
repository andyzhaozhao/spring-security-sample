package cn.com.taiji.domain;

import javax.persistence.*;
import java.util.List;

/**
 * Created by iandtop on 2018/12/11.
 */
@Entity
public class UserInfo {
    @Id
    @GeneratedValue
    private long uid;

    private String username;

    private String password;

    //EAGER,立即从数据库中进行加载数据;
    @ManyToMany(fetch = FetchType.EAGER)
    @JoinTable(name = "UserRole"
            , joinColumns = {@JoinColumn(name = "uid")}
            , inverseJoinColumns = {@JoinColumn(name = "rid")})
    private List<Role> roles;

//    @Enumerated(EnumType.STRING)
//    private Role role;

//    public enum Role {
//        ROLE_ADMIN, ROLE_USER, ROLE_FOO
//    }

    public long getUid() {
        return uid;
    }

    public void setUid(long uid) {
        this.uid = uid;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

//    public Role getRole() {
//        return role;
//    }
//
//    public void setRole(Role role) {
//        this.role = role;
//    }


    public List<Role> getRoles() {
        return roles;
    }

    public void setRoles(List<Role> roles) {
        this.roles = roles;
    }
}
