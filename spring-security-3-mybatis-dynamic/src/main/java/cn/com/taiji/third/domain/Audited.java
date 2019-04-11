package cn.com.taiji.third.domain;

import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Calendar;

@Data
@NoArgsConstructor
public class Audited {
    private String id;
    private String created_by;
    private Calendar created_date;
    private String modified_by;
    private Calendar modified_date;
    private Integer flag;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getCreated_by() {
        return created_by;
    }

    public void setCreated_by(String created_by) {
        this.created_by = created_by;
    }

    public Calendar getCreated_date() {
        return created_date;
    }

    public void setCreated_date(Calendar created_date) {
        this.created_date = created_date;
    }

    public String getModified_by() {
        return modified_by;
    }

    public void setModified_by(String modified_by) {
        this.modified_by = modified_by;
    }

    public Calendar getModified_date() {
        return modified_date;
    }

    public void setModified_date(Calendar modified_date) {
        this.modified_date = modified_date;
    }

    public Integer getFlag() {
        return flag;
    }

    public void setFlag(Integer flag) {
        this.flag = flag;
    }
}
