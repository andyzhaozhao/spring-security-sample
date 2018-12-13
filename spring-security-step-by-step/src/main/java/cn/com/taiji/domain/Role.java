package cn.com.taiji.domain;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;

/**
 * Created by iandtop on 2018/12/11.
 */
@Entity
public class Role {
    @Id
    @GeneratedValue
    private long rid;

    //role name
    private String name;

    //描述
    private String description;

    public long getRid() {
        return rid;
    }

    public void setRid(long rid) {
        this.rid = rid;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }
}
