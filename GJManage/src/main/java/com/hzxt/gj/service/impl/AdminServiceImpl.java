package com.hzxt.gj.service.impl;

import java.util.Date;
import java.util.List;

import org.apache.shiro.crypto.hash.SimpleHash;
import org.apache.shiro.util.ByteSource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.baomidou.mybatisplus.mapper.EntityWrapper;
import com.baomidou.mybatisplus.plugins.Page;
import com.hzxt.gj.mapper.AdminMapper;
import com.hzxt.gj.mapper.AdminRoleMapper;
import com.hzxt.gj.pojo.Admin;
import com.hzxt.gj.pojo.AdminRole;
import com.hzxt.gj.service.AdminService;
import com.hzxt.gj.utils.StringUtil;
import com.hzxt.gj.vo.AdminRoles;

@Service
public class AdminServiceImpl implements AdminService {

	@Autowired
	private AdminMapper adminMapper;

	@Autowired
	private AdminRoleMapper adminRoleMapper;

	@Override
	public Admin findById(Integer id) {
		return adminMapper.selectAdminRoleById(id);
	}

	@Override
	public Admin findByAccount(String account) {
		return adminMapper.selectByAccount(account);
	}

	@Override
	public List<Admin> findByPage(Integer current, Integer size) {
		return adminMapper.selectPage(new Page<Admin>(current, size), null);
	}

	@Override
	public Integer findTotal() {
		return adminMapper.selectCount(null);
	}

	@Override
	@Transactional
	public Integer saveObject(AdminRoles admin, String account) {
		checkAdmin(admin);

		Date currentTime = new Date();
		String uuid = StringUtil.guid();
		ByteSource salt = ByteSource.Util.bytes(uuid);
		admin.setPassword(new SimpleHash("md5", admin.getPassword(), salt, 1).toString());
		admin.setSalt(uuid);
		admin.setErrorTime(currentTime);
		admin.setErrorCount(0);
		admin.setCreateTime(currentTime);
		admin.setCreateAccount(account);
		admin.setStatus(0);
		adminMapper.insert(admin);

		admin.getRoles().forEach(x -> {
			AdminRole adminRole = new AdminRole();
			adminRole.setAdminId(admin.getId());
			adminRole.setRoleId(x.getRoleId());
			adminRoleMapper.insert(adminRole);
		});

		return admin.getId();
	}

	@Override
	public Boolean modifyObject(Admin admin) {
		admin.setPassword(null);
		admin.setPicture(null);

		return 1 == adminMapper.updateById(checkAdmin(admin));
	}

	@Override
	@Transactional
	public Boolean modifyAdminAndRole(AdminRoles admin) {
		admin.setPassword(null);
		admin.setPicture(null);
		adminMapper.updateById(checkAdmin(admin));
		// 删除所有该用户的权限
		adminRoleMapper.delete(new EntityWrapper<AdminRole>().where("admin_id = {0}", admin.getId()));
		// 添加所有
		if (admin.getRoles().size() > 0) {
			admin.getRoles().forEach(x -> {
				AdminRole adminRole = new AdminRole();
				adminRole.setAdminId(admin.getId());
				adminRole.setRoleId(x.getRoleId());
				adminRoleMapper.insert(adminRole);
			});
		}

		return true;
	}

	@Override
	@Transactional
	public Boolean removeById(Integer id) {
		adminMapper.deleteAdminRole(id);
		return 1 == adminMapper.deleteById(id);
	}

	private Admin checkAdmin(Admin admin) {
		if (null != admin.getName() && admin.getName().length() > 50) {
			throw new IllegalArgumentException("用户名长度不能超过50位。");
		}
		if (null != admin.getAccount() && admin.getAccount().length() > 50) {
			throw new IllegalArgumentException("账号长度不能超过50位。");
		}
		if (null != admin.getMobile() && admin.getMobile().length() > 11) {
			throw new IllegalArgumentException("手机号长度不能超过11位。");
		}
		return admin;
	}
}
