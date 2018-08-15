package cn.com.taiji.fourth.domain;

import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.NamedQuery;
import javax.persistence.Table;
import java.io.Serializable;

/**
 * 类名称：RoleUser  角色用户中间表
 * 类描述：
 * 创建人：wanghw
 * 创建时间：2018年2月5日 下午8:45:51
 */
@Entity
@Table(name = "role_user")
@NamedQuery(name = "RoleUser.findAll", query = "SELECT r FROM RoleUser r")
@Data
@NoArgsConstructor
public class RoleUser implements Serializable {

    private static final long serialVersionUID = -5549613676858069574L;
    @EmbeddedId
    private RoleUserPK id;

}