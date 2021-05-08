package com.hzxt.gj.service;

import java.util.List;

import com.hzxt.gj.pojo.Menu;

public interface AuthService {

	List<Integer> findRoleIdsByAdminId(Integer adminId);

	List<Integer> findMenuIdsByRoleIds(Integer... ids);

	List<String> findPermissions(Integer... ids);

	List<Menu> findMenusByMenuIds(Integer... ids);

	List<Menu> findAuthByAdminId(Integer adminId);
}
