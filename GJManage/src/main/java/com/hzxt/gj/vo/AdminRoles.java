package com.hzxt.gj.vo;

import java.util.List;

import com.hzxt.gj.pojo.Admin;

public class AdminRoles extends Admin {

	private static final long serialVersionUID = -7881521990882610649L;
	
	private List<RoleInfo> roles;

	public List<RoleInfo> getRoles() {
		return roles;
	}

	public void setRoles(List<RoleInfo> roles) {
		this.roles = roles;
	}
}
