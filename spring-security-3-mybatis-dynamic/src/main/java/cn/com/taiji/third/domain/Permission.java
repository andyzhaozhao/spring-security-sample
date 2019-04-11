package cn.com.taiji.third.domain;

import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * Created by iandtop on 2018/7/26.
 */
@Data
@NoArgsConstructor
public class Permission extends Audited {
    private String id;
    //权限名称
    private String permission_name;
    //权限描述
    private String permission_descritpion;
    //授权链接
    private String url;
    //父节点id
    private String pid;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getPermission_name() {
        return permission_name;
    }

    public void setPermission_name(String permission_name) {
        this.permission_name = permission_name;
    }

    public String getPermission_descritpion() {
        return permission_descritpion;
    }

    public void setPermission_descritpion(String permission_descritpion) {
        this.permission_descritpion = permission_descritpion;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public String getPid() {
        return pid;
    }

    public void setPid(String pid) {
        this.pid = pid;
    }
}
