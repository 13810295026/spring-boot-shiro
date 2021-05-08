package com.hzxt.gj.vo;

import java.io.Serializable;

public class RoleInfo implements Serializable {

	private static final long serialVersionUID = 1947798246484825088L;

	private Integer adminId;
	private Integer roleId;
	private String roleName;

	public Integer getAdminId() {
		return adminId;
	}

	public void setAdminId(Integer adminId) {
		this.adminId = adminId;
	}

	public Integer getRoleId() {
		return roleId;
	}

	public void setRoleId(Integer roleId) {
		this.roleId = roleId;
	}

	public String getRoleName() {
		return roleName;
	}

	public void setRoleName(String roleName) {
		this.roleName = roleName;
	}

}
