package cn.com.taiji.domain;

import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.io.Serializable;
import java.util.List;

/**
 * 类名称：Role角色
 * 类描述：
 * 创建人：wanghw
 * 创建时间：2018年2月5日 下午8:41:57
 */
@Entity
@Table(name = "role")
@NamedQuery(name = "Role.findAll", query = "SELECT r FROM Role r")
@Data
@NoArgsConstructor
public class Role extends BaseDomain implements Serializable {

    private static final long serialVersionUID = 4641095039785783252L;

    @Column(name = "role_description")
    private String roleDesc;

    @Column(name = "role_index")
    private Integer roleIndex;

    @Column(name = "role_name")
    private String roleName;

    //uni-directional many-to-many association to Menu
    @ManyToMany(mappedBy = "roles", fetch = FetchType.EAGER)
    private List<Menu> menus;

    @ManyToMany(mappedBy = "roles", fetch = FetchType.LAZY)
    private List<User> users;

    private String remark;
}