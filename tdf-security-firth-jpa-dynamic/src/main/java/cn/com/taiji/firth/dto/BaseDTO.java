package cn.com.taiji.firth.dto;

import lombok.Data;
import org.apache.commons.codec.digest.DigestUtils;
import org.apache.commons.lang3.StringUtils;

import java.io.Serializable;

/**
 * 类名称：MenuDto
 * 类描述：   菜单DTO
 * 创建人：dourl
 * 创建时间：2018年2月5日 下午2:06:50
 */
@Data
public abstract class BaseDTO implements Serializable {
    private String token = "";
    private String id;
    private String remark;
    protected abstract String getSerialVersionUID();
    public void generateToken(String salt) {
        if (salt == null) {
            token = DigestUtils.sha1Hex(StringUtils.trimToEmpty(id)
                    + getSerialVersionUID());
        } else {
            token = DigestUtils.sha1Hex(StringUtils.trimToEmpty(id) + salt);
        }
    }

    public boolean tokenKeeped(String salt) {
        if (salt == null) {
            return (DigestUtils.sha1Hex(StringUtils.trimToEmpty(id)
                    + getSerialVersionUID())).equals(token);
        } else {
            return DigestUtils.sha1Hex(StringUtils.trimToEmpty(id) + salt).equals(
                    token);
        }
    }

}