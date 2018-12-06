
package cn.com.taiji.dto;

import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.util.Date;

/**
 * 类名称：RoleDTO   角色dto
 * 类描述：
 * 创建人：wanghw
 * 创建时间：2018年2月5日 下午8:48:31
 */
@Data
@NoArgsConstructor
public class RoleDTO extends BaseDTO implements Serializable {

    private static final long serialVersionUID = 8089520763539561510L;

    private String parentId;

    private String roleDesc;

    private Integer roleIndex;

    private Integer state;

    private String roleName;

    private Integer showUsers;

//    private List<Menu> menus;
//
//    private List<User> users;

    private Date createTime;

    private String creatorId;

    private Date updateTime;

    private String updaterId;

    private Integer flag;

    @Override
    protected String getSerialVersionUID() {
        return Long.toString(serialVersionUID);
    }
}