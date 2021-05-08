package com.hzxt.gj.service.impl;

import java.util.Arrays;
import java.util.Comparator;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.hzxt.gj.mapper.AdminMapper;
import com.hzxt.gj.mapper.MenuMapper;
import com.hzxt.gj.mapper.RoleMapper;
import com.hzxt.gj.pojo.Menu;
import com.hzxt.gj.service.AuthService;

@Service
public class AuthServiceImpl implements AuthService {

	@Autowired
	private AdminMapper adminMapper;

	@Autowired
	private MenuMapper menuMapper;

	@Autowired
	private RoleMapper roleMapper;

	@Override
	public List<Integer> findRoleIdsByAdminId(Integer adminId) {
		return adminMapper.selectRoleIdsById(adminId);
	}

	@Override
	public List<Integer> findMenuIdsByRoleIds(Integer... ids) {
		return roleMapper.selectMenuIdsByIds(ids);
	}

	@Override
	public List<String> findPermissions(Integer... ids) {
		return menuMapper.selectPermissionsByIds(ids);
	}

	@Override
	public List<Menu> findMenusByMenuIds(Integer... ids) {
		return menuMapper.selectBatchIds(Arrays.asList(ids)).stream().sorted(Comparator.comparing(Menu::getSort))
				.filter(menu -> menu.getType() == 0).collect(Collectors.toList());
	}

	@Override
	public List<Menu> findAuthByAdminId(Integer adminId) {
		Set<Integer> menuIdSet = new HashSet<Integer>();
		List<Integer> roleIds = adminMapper.selectRoleIdsById(adminId);
		List<Integer> menuIds = roleMapper.selectMenuIdsByIds(roleIds.toArray(new Integer[] {}));

		// 菜单id去重，然后再递归
		menuIds = menuIds.stream().distinct().collect(Collectors.toList());
		// 递归判断是否有上级，有上级继续查询。
		for (Integer id : menuIds) {
			menuIdSet.add(id);
			recursion(id, menuIdSet);
		}

		return findMenusByMenuIds(menuIdSet.toArray(new Integer[] {}));
	}

	private void recursion(Integer menuId, Set<Integer> menuIdSet) {
		// 通过菜单Id获取到父级Id
		Menu parent = menuMapper.selectById(menuId);
		// 如果父级不是 0 继续查询
		if (parent != null) {
			menuIdSet.add(parent.getParentId());
			if (parent.getParentId() != 0) {
				recursion(parent.getParentId(), menuIdSet);
			}
		}
	}
}
