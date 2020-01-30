package com.changgou.user.pojo;

import javax.persistence.Column;
import javax.persistence.Id;
import javax.persistence.Table;
import java.io.Serializable;

/****
 * @Author:admin
 * @Description:OauthClientDetails构建
 * @Date 2019/6/14 19:13
 *****/
@Table(name="oauth_client_details")
public class OauthClientDetails implements Serializable{

	@Id
    @Column(name = "client_id")
	private String clientId;//客户端ID，主要用于标识对应的应用

    @Column(name = "resource_ids")
	private String resourceIds;//

    @Column(name = "client_secret")
	private String clientSecret;//客户端秘钥，BCryptPasswordEncoder加密算法加密

    @Column(name = "scope")
	private String scope;//对应的范围

    @Column(name = "authorized_grant_types")
	private String authorizedGrantTypes;//认证模式

    @Column(name = "web_server_redirect_uri")
	private String webServerRedirectUri;//认证后重定向地址

    @Column(name = "authorities")
	private String authorities;//

    @Column(name = "access_token_validity")
	private Integer accessTokenValidity;//令牌有效期

    @Column(name = "refresh_token_validity")
	private Integer refreshTokenValidity;//令牌刷新周期

    @Column(name = "additional_information")
	private String additionalInformation;//

    @Column(name = "autoapprove")
	private String autoapprove;//



	//get方法
	public String getClientId() {
		return clientId;
	}

	//set方法
	public void setClientId(String clientId) {
		this.clientId = clientId;
	}
	//get方法
	public String getResourceIds() {
		return resourceIds;
	}

	//set方法
	public void setResourceIds(String resourceIds) {
		this.resourceIds = resourceIds;
	}
	//get方法
	public String getClientSecret() {
		return clientSecret;
	}

	//set方法
	public void setClientSecret(String clientSecret) {
		this.clientSecret = clientSecret;
	}
	//get方法
	public String getScope() {
		return scope;
	}

	//set方法
	public void setScope(String scope) {
		this.scope = scope;
	}
	//get方法
	public String getAuthorizedGrantTypes() {
		return authorizedGrantTypes;
	}

	//set方法
	public void setAuthorizedGrantTypes(String authorizedGrantTypes) {
		this.authorizedGrantTypes = authorizedGrantTypes;
	}
	//get方法
	public String getWebServerRedirectUri() {
		return webServerRedirectUri;
	}

	//set方法
	public void setWebServerRedirectUri(String webServerRedirectUri) {
		this.webServerRedirectUri = webServerRedirectUri;
	}
	//get方法
	public String getAuthorities() {
		return authorities;
	}

	//set方法
	public void setAuthorities(String authorities) {
		this.authorities = authorities;
	}
	//get方法
	public Integer getAccessTokenValidity() {
		return accessTokenValidity;
	}

	//set方法
	public void setAccessTokenValidity(Integer accessTokenValidity) {
		this.accessTokenValidity = accessTokenValidity;
	}
	//get方法
	public Integer getRefreshTokenValidity() {
		return refreshTokenValidity;
	}

	//set方法
	public void setRefreshTokenValidity(Integer refreshTokenValidity) {
		this.refreshTokenValidity = refreshTokenValidity;
	}
	//get方法
	public String getAdditionalInformation() {
		return additionalInformation;
	}

	//set方法
	public void setAdditionalInformation(String additionalInformation) {
		this.additionalInformation = additionalInformation;
	}
	//get方法
	public String getAutoapprove() {
		return autoapprove;
	}

	//set方法
	public void setAutoapprove(String autoapprove) {
		this.autoapprove = autoapprove;
	}


}
