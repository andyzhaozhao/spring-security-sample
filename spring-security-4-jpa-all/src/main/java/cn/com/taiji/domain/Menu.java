package cn.com.taiji.domain;

import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.Where;

import javax.persistence.*;
import java.io.Serializable;
import java.util.LinkedList;
import java.util.List;


/**
 * The persistent class for the menu database table.
 */
@Entity
@Table(name = "menu")
@NamedQuery(name = "Menu.findAll", query = "SELECT m FROM Menu m")
@NamedQueries({
        @NamedQuery(name = "findMenuTree", query = "select o from Menu o left join fetch o.children"),
        @NamedQuery(name = "Menu.findRoots", query = "select o from Menu o where o.parent is null")})
@Data
@NoArgsConstructor
public class Menu extends BaseDomain implements Serializable {

    private static final long serialVersionUID = 7381374907067127702L;

    public static long getSerialversionuid() {
        return serialVersionUID;
    }

    @Column(name = "controller_class")
    private String controllerClass;

    @Lob
    @Column(name = "icon_path")
    private byte[] iconPath;

    @Column(name = "menu_desc")
    private String menuDesc;

    @Column(name = "menu_index")
    private Integer menuIndex;

    @Column(name = "menu_name")
    private String menuName;

    @Column(name = "menu_url")
    private String menuUrl;

    private String remark;

    @Lob
    @Column(name = "small_icon_path")
    private byte[] smallIconPath;

    @ManyToOne(cascade = {CascadeType.REFRESH}, optional = true)
    @JoinColumn(name = "parent_id", insertable = true, updatable = true)
    private Menu parent = null;

    @OrderBy("menuIndex ASC")
    @Where(clause = "flag = 1")
    @OneToMany(cascade = {CascadeType.PERSIST, CascadeType.REFRESH, CascadeType.MERGE}, fetch = FetchType.EAGER)
    @JoinColumn(name = "parent_id", insertable = false, updatable = false)
    private List<Menu> children = new LinkedList<Menu>();

    @ManyToMany(cascade = {CascadeType.PERSIST, CascadeType.REFRESH, CascadeType.MERGE}, fetch = FetchType.EAGER)
    @JoinTable(name = "role_menu", joinColumns = @JoinColumn(name = "menu_id"),
            inverseJoinColumns = @JoinColumn(name = "role_id"))
    private List<Role> roles;

    private String mark;//初始化菜单记号
}