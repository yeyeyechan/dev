package com.multi.oauth10server.model;

import java.io.Serializable;

import com.fasterxml.jackson.annotation.JsonIgnore;

public class UsersVO implements Serializable {
	
	private static final long serialVersionUID = 1L;
	
	private String userid;
	private String password;
	private String username;
	private long userno;
	
	public String toString() {
		return "userid=" + this.getUserid() + 
			", password=" + this.getPassword() +
			", username=" + this.getUsername() + 
			", userno=" + this.getUserno();
	}
	
	public long getUserno() {
		return userno;
	}

	public void setUserno(long userno) {
		this.userno = userno;
	}
	
	public String getUserid() {
		return userid;
	}

	public void setUserid(String userid) {
		this.userid = userid;
	}
	@JsonIgnore
	public String getPassword() {
		return password;
	}
	@JsonIgnore
	public void setPassword(String password) {
		this.password = password;
	}
	public String getUsername() {
		return username;
	}
	public void setUsername(String username) {
		this.username = username;
	}
	
	public UsersVO(String userid, String password, String username, long userno) {
		super();
		this.userid = userid;
		this.password = password;
		this.username = username;
		this.userno = userno;
	}
	public UsersVO() {
		super();
		// TODO Auto-generated constructor stub
	}
	
}
