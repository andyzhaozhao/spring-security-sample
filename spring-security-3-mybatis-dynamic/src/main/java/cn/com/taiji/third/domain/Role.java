package cn.com.taiji.third.domain;

import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

@Data
@NoArgsConstructor
public class Role extends Audited implements Serializable {
    private static final long serialVersionUID = 4641095039785783252L;
    private String role_description;
    private Integer role_index;
    private String role_name;
    private Integer show_users;
}