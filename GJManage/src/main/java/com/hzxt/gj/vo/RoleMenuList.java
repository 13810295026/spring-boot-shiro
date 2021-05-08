package com.hzxt.gj.vo;

import java.io.Serializable;
import java.util.Date;
import java.util.List;

public class RoleMenuList implements Serializable {

	private static final long serialVersionUID = 7470677777218024835L;

	private Integer id;
	private String name;
	private String desc;
	private Date createdTime;
	private Date modifiedTime;
	private String createdAccount;
	private String modifiedAccount;
	private List<Integer> roleMenuList;

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getDesc() {
		return desc;
	}

	public void setDesc(String desc) {
		this.desc = desc;
	}

	public Date getCreatedTime() {
		return createdTime;
	}

	public void setCreatedTime(Date createdTime) {
		this.createdTime = createdTime;
	}

	public Date getModifiedTime() {
		return modifiedTime;
	}

	public void setModifiedTime(Date modifiedTime) {
		this.modifiedTime = modifiedTime;
	}

	public String getCreatedAccount() {
		return createdAccount;
	}

	public void setCreatedAccount(String createdAccount) {
		this.createdAccount = createdAccount;
	}

	public String getModifiedAccount() {
		return modifiedAccount;
	}

	public void setModifiedAccount(String modifiedAccount) {
		this.modifiedAccount = modifiedAccount;
	}

	public List<Integer> getRoleMenuList() {
		return roleMenuList;
	}

	public void setRoleMenuList(List<Integer> roleMenuList) {
		this.roleMenuList = roleMenuList;
	}

}
