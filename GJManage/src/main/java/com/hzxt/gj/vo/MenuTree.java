package com.hzxt.gj.vo;

import java.io.Serializable;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonProperty;

public class MenuTree implements Serializable {

	private static final long serialVersionUID = -3226338682446570368L;

	private Integer id;
	@JsonProperty("label")
	private String name;
	private String url;
	private Integer type;
	private Integer sort;
	private String desc;
	private String permission;
	@JsonProperty("selected")
	private Boolean authorized;
	@JsonProperty("parent")
	private Integer parentId;
	@JsonProperty("children")
	private List<MenuTree> childs;

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

	public String getUrl() {
		return url;
	}

	public void setUrl(String url) {
		this.url = url;
	}

	public Integer getType() {
		return type;
	}

	public void setType(Integer type) {
		this.type = type;
	}

	public Integer getSort() {
		return sort;
	}

	public void setSort(Integer sort) {
		this.sort = sort;
	}

	public String getDesc() {
		return desc;
	}

	public void setDesc(String desc) {
		this.desc = desc;
	}

	public String getPermission() {
		return permission;
	}

	public void setPermission(String permission) {
		this.permission = permission;
	}

	public Boolean getAuthorized() {
		return authorized;
	}

	public void setAuthorized(Boolean authorized) {
		this.authorized = authorized;
	}

	public Integer getParentId() {
		return parentId;
	}

	public void setParentId(Integer parentId) {
		this.parentId = parentId;
	}

	public List<MenuTree> getChilds() {
		return childs;
	}

	public void setChilds(List<MenuTree> childs) {
		this.childs = childs;
	}

}
