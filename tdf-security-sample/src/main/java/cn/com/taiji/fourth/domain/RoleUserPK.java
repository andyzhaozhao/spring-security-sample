package cn.com.taiji.fourth.domain;

import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.Column;
import javax.persistence.Embeddable;
import java.io.Serializable;

/**
 * 类名称：RoleUserPK   角色用户中间表构成属性对象
 * 类描述：
 * 创建人：wanghw
 * 创建时间：2018年2月5日 下午8:46:13
 */
@Embeddable
@Data
@NoArgsConstructor
public class RoleUserPK implements Serializable {

    private static final long serialVersionUID = -6773592495408197838L;

    @Column(name = "role_id", insertable = false, updatable = false)
    private String roleId;

    @Column(name = "user_id", insertable = false, updatable = false)
    private String userId;

    public boolean equals(Object other) {
        if (this == other) {
            return true;
        }
        if (!(other instanceof RoleUserPK)) {
            return false;
        }
        RoleUserPK castOther = (RoleUserPK) other;
        return
                this.roleId.equals(castOther.roleId)
                        && this.userId.equals(castOther.userId);
    }

    public int hashCode() {
        final int prime = 31;
        int hash = 17;
        hash = hash * prime + this.roleId.hashCode();
        hash = hash * prime + this.userId.hashCode();

        return hash;
    }
}