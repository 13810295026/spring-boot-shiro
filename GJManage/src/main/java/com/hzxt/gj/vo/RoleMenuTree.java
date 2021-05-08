package com.hzxt.gj.vo;

import java.io.Serializable;
import java.util.Date;
import java.util.List;

public class RoleMenuTree implements Serializable {

	private static final long serialVersionUID = -676061169016859413L;

	private Integer id;
	private String name;
	private String desc;
	private Date createdTime;
	private Date modifiedTime;
	private String createdAccount;
	private String modifiedAccount;
	private List<MenuTree> menuTree;
	private List<Integer> menuIds;

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

	public List<MenuTree> getMenuTree() {
		return menuTree;
	}

	public void setMenuTree(List<MenuTree> menuTree) {
		this.menuTree = menuTree;
	}

	public List<Integer> getMenuIds() {
		return menuIds;
	}

	public void setMenuIds(List<Integer> menuIds) {
		this.menuIds = menuIds;
	}
}
