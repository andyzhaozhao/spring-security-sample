package cn.com.taiji.firth.domain;

import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.Column;
import javax.persistence.Embeddable;
import java.io.Serializable;

/**
 * 类名称：RoleMenuKeyPK   角色菜单中间表构成属性对象
 * 类描述：
 * 创建人：wanghw
 * 创建时间：2018年2月5日 下午8:43:52
 */
@Embeddable
@Data
@NoArgsConstructor
public class RoleMenuPK implements Serializable {

    private static final long serialVersionUID = 4040666385792166495L;

    @Column(name = "menu_id", insertable = false, updatable = false)
    private String menuId;

    @Column(name = "role_id", insertable = false, updatable = false)
    private String roleId;


    public boolean equals(Object other) {
        if (this == other) {
            return true;
        }
        if (!(other instanceof RoleMenuPK)) {
            return false;
        }
        RoleMenuPK castOther = (RoleMenuPK) other;
        return
                this.menuId.equals(castOther.menuId)
                        && this.roleId.equals(castOther.roleId);
    }

    public int hashCode() {
        final int prime = 31;
        int hash = 17;
        hash = hash * prime + this.menuId.hashCode();
        hash = hash * prime + this.roleId.hashCode();

        return hash;
    }
}