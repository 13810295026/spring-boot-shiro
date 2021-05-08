package com.hzxt.gj.service;

import java.util.List;

import com.hzxt.gj.pojo.Admin;
import com.hzxt.gj.vo.AdminRoles;

public interface AdminService {

	Admin findById(Integer id);

	Admin findByAccount(String acount);

	List<Admin> findByPage(Integer current, Integer size);

	Integer findTotal();

	Integer saveObject(AdminRoles admin, String createAccount);

	Boolean modifyObject(Admin admin);

	Boolean modifyAdminAndRole(AdminRoles admin);

	Boolean removeById(Integer id);
}
