package com.hzxt.gj.controller;

import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.hzxt.gj.service.AdminService;
import com.hzxt.gj.utils.ShiroUtil;
import com.hzxt.gj.vo.AdminRoles;
import com.hzxt.gj.vo.ResultMsg;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;

@Api(tags = "管理端用户接口")
@RequestMapping("/admin")
@RestController
public class AdminController {

	@Autowired
	private AdminService adminService;

	@ApiOperation(value = "查询用户", httpMethod = "GET", notes = "取管理端用户及角色信息")
	@ApiImplicitParam(name = "adminId", value = "用户id", dataType = "int", paramType = "path", required = true)
	@GetMapping("/query/{adminId}")
	public ResultMsg queryAdmin(@PathVariable Integer adminId) {

		return new ResultMsg(adminService.findById(adminId));
	}

	@ApiOperation(value = "用户分页", httpMethod = "GET", notes = "分页查询管理用户")
	@ApiImplicitParams({
			@ApiImplicitParam(name = "current", value = "当前页数", dataType = "int", paramType = "path", defaultValue = "1"),
			@ApiImplicitParam(name = "size", value = "每页数量", dataType = "int", paramType = "path", defaultValue = "20") })
	@GetMapping("/items/{current}/{size}")
	public ResultMsg getAdminList(@PathVariable("current") Integer current, @PathVariable("size") Integer size) {

		return new ResultMsg(adminService.findByPage(current, size));
	}

	@ApiOperation(value = "用户总数", httpMethod = "GET", notes = "取得管理用户总数")
	@GetMapping("/items/total/")
	public ResultMsg getAdminTotal() {

		return new ResultMsg(adminService.findTotal());
	}

	@ApiOperation(value = "创建用户", httpMethod = "POST", notes = "提交管理用户及用户组信息")
	@ApiImplicitParam(name = "admin", value = "用户信息", dataType = "AdminRoles", paramType = "body", required = true)
	@PostMapping
	public ResultMsg postAdmin(@RequestBody AdminRoles admin) {

		return new ResultMsg(adminService.saveObject(admin, ShiroUtil.getPrincipal().getAccount()));
	}

	@ApiOperation(value = "更新用户", httpMethod = "PUT", notes = "修改管理用户及用户组信息")
	@ApiImplicitParam(name = "admin", value = "用户信息", dataType = "AdminRoles", paramType = "body", required = true)
	@PutMapping
	public ResultMsg putAdmin(@RequestBody AdminRoles admin) {

		return new ResultMsg(adminService.modifyAdminAndRole(admin));
	}

	@ApiOperation(value = "删除用户", httpMethod = "DELETE", notes = "删除管理用户及用户组关系")
	@ApiImplicitParam(name = "adminId", value = "用户id", dataType = "int", paramType = "path", required = true)
	@RequiresPermissions("sys:admin:delete")
	@DeleteMapping("/{adminId}")
	public ResultMsg deleteAdmin(@PathVariable Integer adminId) {

		return new ResultMsg(adminService.removeById(adminId));
	}
}
