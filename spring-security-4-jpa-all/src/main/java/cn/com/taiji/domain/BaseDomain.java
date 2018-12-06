package cn.com.taiji.domain;

import lombok.Data;
import org.springframework.data.annotation.CreatedBy;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedBy;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;

import javax.persistence.*;
import java.util.Calendar;
import java.util.UUID;

@MappedSuperclass
@Data
public class BaseDomain {

    @Id
    @Column(name = "id")
    private String id;

    @CreatedBy
    protected String createdBy;
    @CreatedDate
    protected Calendar createdDate;

    @LastModifiedBy
    protected String modifiedBy;
    @LastModifiedDate
    protected Calendar modifiedDate;

    public Integer flag;

    /**
     * @throws
     * @author chixue
     * @Description: 新增前设置自定义UUID，设置flag，设置createDate，设置modifiedDate，设置createBy
     * @date 2016年5月9日
     */
    @PrePersist
    public void createAuditInfo() {
        SecurityContext context = SecurityContextHolder.getContext();

        if (null != context.getAuthentication() && !context.getAuthentication().isAuthenticated()) {
            UserDetails userDetails = (UserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
            setCreatedBy(null == userDetails ? "" : userDetails.getUsername());
        }

        setCreatedDate(Calendar.getInstance());
        setModifiedDate(Calendar.getInstance());
        setId(UUID.randomUUID().toString().replaceAll("-", ""));
        setFlag(1);// 默认初始化
    }

    /**
     * @throws
     * @author chixue
     * @Description: 更新记录前，设置ModifyBy，ModifiedDate
     * @date 2016年5月9日
     */
    @PreUpdate
    public void updateAuditInfo() {
        SecurityContext context = SecurityContextHolder.getContext();
        if (null != context.getAuthentication() && !context.getAuthentication().isAuthenticated()) {
            UserDetails userDetails = (UserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
            setModifiedBy(null == userDetails ? "" : userDetails.getUsername());
        }

        setModifiedDate(Calendar.getInstance());
    }

}
