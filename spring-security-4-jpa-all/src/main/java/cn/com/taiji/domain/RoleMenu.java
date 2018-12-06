package cn.com.taiji.domain;

import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.NamedQuery;
import javax.persistence.Table;
import java.io.Serializable;


/**
 * 
 * 类名称：RoleMenu   角色菜单中间表
 * 类描述：   
 * 创建人：wanghw
 * 创建时间：2018年2月5日 下午8:43:27 
 * @version
 */
@Entity
@Table(name="role_menu")
@NamedQuery(name="RoleMenu.findAll", query="SELECT r FROM RoleMenu r")
@Data
@NoArgsConstructor
public class RoleMenu implements Serializable {

	private static final long serialVersionUID = -3204190138251678123L;
	@EmbeddedId
	private RoleMenuPK id;

}