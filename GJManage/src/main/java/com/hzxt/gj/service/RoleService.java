package com.hzxt.gj.service;

import java.util.List;

import com.hzxt.gj.pojo.Role;
import com.hzxt.gj.vo.RoleMenuList;
import com.hzxt.gj.vo.RoleMenuTree;

public interface RoleService {

	List<Role> findByPage(Integer current, Integer size);

	List<Role> findAll();

	Integer findTotal();

	RoleMenuTree findById(Integer id);

	Boolean create(RoleMenuList roleMenu);

	Boolean modify(RoleMenuList roleMenu);

	Boolean removeById(Integer id);
}
