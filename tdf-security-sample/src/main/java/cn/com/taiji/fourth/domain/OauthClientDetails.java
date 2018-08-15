package cn.com.taiji.fourth.domain;

import lombok.Data;

import javax.persistence.*;
import java.io.Serializable;

@Data
@Entity
@Table(name = "oauth_client_details")
@NamedQuery(name = "OauthClientDetails.findAll", query = "SELECT c FROM OauthClientDetails c")
public class OauthClientDetails implements Serializable {
    private static final long serialVersionUID = 1724450140216701197L;

    /**
     * 客户端id
     */
    @Id
    @Column(name = "client_id")
    private String clientId;
    /**
     * 资源
     */
    @Column(name = "resource_ids")
    private String resourceIds;
    /**
     * 客户隐私
     */
    @Column(name = "client_secret")
    private String clientSecret;
    /**
     * 范围
     */
    @Column(name = "scope")
    private String scope;
    /**
     * 授权类型
     */
    @Column(name = "authorized_grant_types")
    private String authorizedGrantTypes;
    /**
     * 服务器重定向URI
     */
    @Column(name = "web_server_redirect_uri")
    private String webServerRedirectUri;
    /**
     * 权限
     */
    @Column(name = "authorities")
    private String authorities;
    /**
     * 访问令牌的有效性
     */
    @Column(name = "access_token_validity", columnDefinition = "TINYINT(1) DEFAULT 7200")
    private int accessTokenValidity;
    /**
     * 刷新令牌的有效性
     */
    @Column(name = "refresh_token_validity", columnDefinition = "tinyint default 72000")
    private int refreshTokenValidity;
    /**
     * 额外信息
     */
    @Column(name = "additional_information")
    private String additionalInformation;
    /**
     * 自动批准(实际显示的是 应用名称)
     */
    @Column(name = "autoapprove")
    private String autoapprove;
}
