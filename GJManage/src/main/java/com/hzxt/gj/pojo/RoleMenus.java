package com.hzxt.gj.pojo;

import java.io.Serializable;

import com.baomidou.mybatisplus.annotations.TableField;
import com.baomidou.mybatisplus.annotations.TableId;
import com.baomidou.mybatisplus.annotations.TableName;
import com.baomidou.mybatisplus.enums.IdType;

@TableName("tb_auth_role_menu")
public class RoleMenus implements Serializable {
	private static final long serialVersionUID = -4889847861865173713L;
	@TableId(value = "id", type = IdType.AUTO)
	private Integer id;
	@TableField("role_id")
	private Integer role_id;
	@TableField("menu_id")
	private Integer menu_id;

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public Integer getRole_id() {
		return role_id;
	}

	public void setRole_id(Integer role_id) {
		this.role_id = role_id;
	}

	public Integer getMenu_id() {
		return menu_id;
	}

	public void setMenu_id(Integer menu_id) {
		this.menu_id = menu_id;
	}
}
