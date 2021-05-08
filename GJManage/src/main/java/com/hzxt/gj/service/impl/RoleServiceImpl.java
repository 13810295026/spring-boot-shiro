package com.hzxt.gj.service.impl;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.baomidou.mybatisplus.plugins.Page;
import com.hzxt.gj.mapper.MenuMapper;
import com.hzxt.gj.mapper.RoleMapper;
import com.hzxt.gj.mapper.RoleMenuMapper;
import com.hzxt.gj.pojo.Role;
import com.hzxt.gj.pojo.RoleMenus;
import com.hzxt.gj.service.RoleService;
import com.hzxt.gj.vo.MenuTree;
import com.hzxt.gj.vo.RoleMenuList;
import com.hzxt.gj.vo.RoleMenuTree;

@Service
public class RoleServiceImpl implements RoleService {

	@Autowired
	private RoleMapper roleMapper;

	@Autowired
	private MenuMapper menuMapper;
	
	@Autowired
	private RoleMenuMapper roleMenuMapper;

	/**
	 * 取得角色菜单权限
	 * @return RoleMenuTree
	 * 		1、映射角色信息(存在则返回,不存在为null)
	 * 		2、角色菜单树(一定返回)
	 * 		3、角色菜单id列表(存在则返回,不存在为null)
	 */
	@Override
	public RoleMenuTree findById(Integer id) {
		Optional<RoleMenuTree> roleMenutree = 
				Optional.ofNullable(roleMapper.selectRoleMenuById(id));
		// 如果存在则返回，如果不存在则返回空的全映射
		return roleMenutree.orElseGet(() -> getEmptyRoleMenuTree(id));
	}

	private RoleMenuTree getEmptyRoleMenuTree(Integer id) {
		RoleMenuTree roleMenu = new RoleMenuTree();
		List<MenuTree> list = menuMapper.selectByRoleId(id);
		roleMenu.setMenuTree(list);
		
		return roleMenu;
	}
	
	@Override
	public List<Role> findByPage(Integer current, Integer size) {
		Page<Role> page = new Page<Role>(current, size);

		List<Role> result = new ArrayList<>();
		List<Role> roles = roleMapper.selectPage(page, null);
		// 分页返回的List并非ArrayList<>
		roles.forEach(info -> {
			result.add(info);
		});

		return result;
	}
	
	@Override
	public List<Role> findAll() {
		return roleMapper.selectList(null);
	}

	@Override
	public Integer findTotal() {
		return roleMapper.selectCount(null);
	}

	@Override
	@Transactional
	public Boolean removeById(Integer id) {
		roleMapper.deleteById(id);
		roleMapper.deleteRoleMenusById(id);

		return true;
	}

	@Override
	@Transactional
	public Boolean create(RoleMenuList roleMenu) {
		roleMenu.setCreatedTime(new Date());
		roleMenu.setModifiedTime(roleMenu.getCreatedTime());
		
		Role role = convertToPojo(roleMenu);
		roleMapper.insert(role);
		roleMenu.getRoleMenuList().forEach(x->{
			RoleMenus roleMenus = new RoleMenus();
			roleMenus.setRole_id(role.getId());
			roleMenus.setMenu_id(x);
			roleMenuMapper.insert(roleMenus);
		});

		return true;
	}

	@Override
	@Transactional
	public Boolean modify(RoleMenuList roleMenu) {
		roleMenu.setCreatedAccount(null);
		roleMenu.setCreatedTime(null);
		roleMenu.setModifiedTime(new Date());

		roleMapper.updateById(convertToPojo(roleMenu));
		roleMapper.deleteRoleMenusById(roleMenu.getId());
		
		roleMenu.getRoleMenuList().forEach(x->{
			RoleMenus roleMenus = new RoleMenus();
			roleMenus.setRole_id(roleMenu.getId());
			roleMenus.setMenu_id(x);
			roleMenuMapper.insert(roleMenus);
		});

		return true;
	}

	private Role convertToPojo(RoleMenuList roleMenu) {
		Role role = new Role();
		role.setName(roleMenu.getName());
		role.setDesc(roleMenu.getDesc());
		role.setCreatedTime(roleMenu.getCreatedTime());
		role.setModifiedTime(roleMenu.getModifiedTime());
		role.setCreatedAccount(roleMenu.getCreatedAccount());
		role.setModifiedAccount(roleMenu.getModifiedAccount());

		return role;
	}
}
