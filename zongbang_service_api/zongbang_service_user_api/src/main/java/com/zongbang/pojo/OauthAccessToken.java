package com.zongbang.pojo;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

import javax.persistence.Column;
import javax.persistence.Id;
import javax.persistence.Table;
import java.io.Serializable;

/****
 * @Author:www.itheima.com
 * @Description:OauthAccessToken构建
 * @Date www.itheima.com 19:13
 *****/
@ApiModel(description = "OauthAccessToken",value = "OauthAccessToken")
@Table(name="oauth_access_token")
public class OauthAccessToken implements Serializable{

	@ApiModelProperty(value = "",required = false)
    @Column(name = "token_id")
	private String tokenId;//

	@ApiModelProperty(value = "",required = false)
    @Column(name = "token")
	private String token;//

	@ApiModelProperty(value = "",required = false)
	@Id
    @Column(name = "authentication_id")
	private String authenticationId;//

	@ApiModelProperty(value = "",required = false)
    @Column(name = "user_name")
	private String userName;//

	@ApiModelProperty(value = "",required = false)
    @Column(name = "client_id")
	private String clientId;//

	@ApiModelProperty(value = "",required = false)
    @Column(name = "authentication")
	private String authentication;//

	@ApiModelProperty(value = "",required = false)
    @Column(name = "refresh_token")
	private String refreshToken;//



	//get方法
	public String getTokenId() {
		return tokenId;
	}

	//set方法
	public void setTokenId(String tokenId) {
		this.tokenId = tokenId;
	}
	//get方法
	public String getToken() {
		return token;
	}

	//set方法
	public void setToken(String token) {
		this.token = token;
	}
	//get方法
	public String getAuthenticationId() {
		return authenticationId;
	}

	//set方法
	public void setAuthenticationId(String authenticationId) {
		this.authenticationId = authenticationId;
	}
	//get方法
	public String getUserName() {
		return userName;
	}

	//set方法
	public void setUserName(String userName) {
		this.userName = userName;
	}
	//get方法
	public String getClientId() {
		return clientId;
	}

	//set方法
	public void setClientId(String clientId) {
		this.clientId = clientId;
	}
	//get方法
	public String getAuthentication() {
		return authentication;
	}

	//set方法
	public void setAuthentication(String authentication) {
		this.authentication = authentication;
	}
	//get方法
	public String getRefreshToken() {
		return refreshToken;
	}

	//set方法
	public void setRefreshToken(String refreshToken) {
		this.refreshToken = refreshToken;
	}


}
