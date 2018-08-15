package cn.com.taiji.fourth.dto;

import lombok.Data;
import lombok.NoArgsConstructor;
import org.apache.commons.codec.digest.DigestUtils;
import org.apache.commons.lang3.StringUtils;

import java.io.Serializable;

/**
 *
 */
@Data
@NoArgsConstructor
public class OauthClientDetailsDTO extends BaseDTO implements Serializable {
    private static final long serialVersionUID = 8654485582322279564L;

    /**
     * 客户端id
     */
    private String clientId;
    /**
     * 资源
     */
    private String resourceIds;
    /**
     * 客户隐私
     */
    private String clientSecret;
    /**
     * 范围
     */
    private String scope;
    /**
     * 授权类型
     */
    private String authorizedGrantTypes;
    /**
     * 服务器重定向URI
     */
    private String webServerRedirectUri;
    /**
     * 权限
     */
    private String authorities;
    /**
     * 访问令牌的有效性
     */
    private int accessTokenValidity;
    /**
     * 刷新令牌的有效性
     */
    private int refreshTokenValidity;
    /**
     * 额外信息
     */
    private String additionalInformation;
    /**
     * 自动批准
     */
    private String autoapprove;

    /**
     * 标记
     */
    // private int flag;
    @Override
    protected String getSerialVersionUID() {
        return Long.toString(serialVersionUID);
    }

    public void generateToken(String salt) {
        if (salt == null)
            setToken(DigestUtils.sha1Hex(StringUtils.trimToEmpty(clientId) + getSerialVersionUID()));
        else
            setToken(DigestUtils.sha1Hex(StringUtils.trimToEmpty(clientId) + salt));

    }

    public boolean tokenKeeped(String salt) {
        if (salt == null)
            return (DigestUtils.sha1Hex(StringUtils.trimToEmpty(clientId)
                    + Long.toString(serialVersionUID))).equals(getToken());
        else
            return DigestUtils.sha1Hex(StringUtils.trimToEmpty(clientId) + salt).equals(
                    getToken());
    }

}