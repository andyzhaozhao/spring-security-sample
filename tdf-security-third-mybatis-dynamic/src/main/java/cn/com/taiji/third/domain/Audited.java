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
}
