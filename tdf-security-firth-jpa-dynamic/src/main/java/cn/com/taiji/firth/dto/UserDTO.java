package cn.com.taiji.firth.dto;

import lombok.Data;
import lombok.NoArgsConstructor;
import org.apache.commons.codec.digest.DigestUtils;
import org.apache.commons.lang3.StringUtils;

import java.io.Serializable;
import java.util.Set;

/**
 * 类名称：UserDTO 类描述： 用户DTO 创建人：dourl 创建时间：2018年2月5日 下午2:07:16
 */
@Data
@NoArgsConstructor
public class UserDTO extends BaseDTO implements Serializable {
    private static final long serialVersionUID = 1170018455276020707L;

    private Integer userIndex;
    private String email;
    private String loginName;
    private String password;
    private String state;
    private String userName;
    private String phoneNum;
    private Integer flag;

    // 昵称
    private String nickname;
    // 性别
    private String gender;
    // 居住地
    private String address;
    // 博客
    private String blog;
    // 标签
    private String tag;
    // 头像路径
    private String avatar;
    // 身份证号
    private String idNumber;
    // 出生日期
    private String birthday;
    // 用户积分
    private Integer integral;

    private String file;

    private Set<RoleDTO> roles;

    @Override
    protected String getSerialVersionUID() {
        return Long.toString(serialVersionUID);
    }

    public void generateTokenForCommunity(String salt) {
        if (salt == null)
            setToken(DigestUtils.sha1Hex(StringUtils.trimToEmpty(userName) +
                    StringUtils.trimToEmpty(email) + StringUtils.trimToEmpty(getId()) + Long.toString(serialVersionUID)));
        else
            setToken(DigestUtils.sha1Hex(StringUtils.trimToEmpty(userName) +
                    StringUtils.trimToEmpty(email) + StringUtils.trimToEmpty(getId()) + salt));

    }

    public boolean tokenKeepedForCommunity(String salt) {
        if (salt == null)
            return (DigestUtils.sha1Hex(StringUtils.trimToEmpty(userName) +
                    StringUtils.trimToEmpty(email) + StringUtils.trimToEmpty(getId()) + Long.toString(serialVersionUID))).equals(getToken());
        else
            return DigestUtils.sha1Hex(StringUtils.trimToEmpty(userName) +
                    StringUtils.trimToEmpty(email) + StringUtils.trimToEmpty(getId()) + salt).equals(getToken());

    }
}