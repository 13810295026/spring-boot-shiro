package com.hzxt.gj.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.hzxt.gj.service.RoleService;
import com.hzxt.gj.utils.ShiroUtil;
import com.hzxt.gj.vo.ResultMsg;
import com.hzxt.gj.vo.RoleMenuList;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;

@Api(tags = "管理端用户组接口")
@RequestMapping("/role")
@RestController
public class RoleController {

	@Autowired
	private RoleService roleService;

	@ApiOperation(value = "查询用户组(角色)", httpMethod = "GET", notes = "取得用户组及该用户组的授权信息")
	@ApiImplicitParam(name = "roleId", value = "角色id", dataType = "int", paramType = "path", required = true)
	@GetMapping("/query/{roleId}")
	public ResultMsg getRole(@PathVariable Integer roleId) {
		return new ResultMsg(roleService.findById(roleId));
	}

//	@ApiOperation(value = "查询用户组(角色)", httpMethod = "GET", notes = "取得全部用户组")
//	@GetMapping("/items")
//	public ResultMsg getRoleAllList() {
//		return new ResultMsg(roleService.findAll());
//	}

	@ApiOperation(value = "用户组分页(角色)", httpMethod = "GET", notes = "分页查询用户组")
	@ApiImplicitParams({
		@ApiImplicitParam(name = "current", value = "当前页数", dataType = "int", paramType = "path", defaultValue = "1"),
		@ApiImplicitParam(name = "size", value = "每页数量", dataType = "int", paramType = "path", defaultValue = "20") })
	@GetMapping("/items/{current}/{size}")
	public ResultMsg getRoleList(@PathVariable("current") Integer current, @PathVariable("size") Integer size) {
		return new ResultMsg(roleService.findByPage(current, size));
	}

	@ApiOperation(value = "用户组总数(角色)", httpMethod = "GET", notes = "取得用户组总数")
	@GetMapping("/items/total")
	public ResultMsg getRoleTotal() {
		return new ResultMsg(roleService.findTotal());
	}

	@ApiOperation(value = "创建用户组(角色)", httpMethod = "POST", notes = "提交用户组及授权信息")
	@ApiImplicitParam(name = "roleMenu", value = "角色信息", dataType = "RoleMenuList", paramType = "body", required = true)
	@PostMapping
	public ResultMsg postRole(@RequestBody RoleMenuList roleMenu) {
		roleMenu.setCreatedAccount(ShiroUtil.getPrincipal().getAccount());
		roleMenu.setModifiedAccount(ShiroUtil.getPrincipal().getAccount());

		return new ResultMsg(roleService.create(roleMenu));
	}

	@ApiOperation(value = "更新用户组(角色)", httpMethod = "PUT", notes = "提交用户组及授权信息")
	@ApiImplicitParam(name = "roleMenu", value = "角色信息", dataType = "RoleMenuList", paramType = "body", required = true)
	@PutMapping
	public ResultMsg putRole(@RequestBody RoleMenuList roleMenu) {
		roleMenu.setModifiedAccount(ShiroUtil.getPrincipal().getAccount());
		return new ResultMsg(roleService.modify(roleMenu));
	}

	@ApiOperation(value = "删除用户组(角色)", httpMethod = "DELETE", notes = "根据id删除用户组及用户组的菜单授权")
	@ApiImplicitParam(name = "roleId", value = "角色id", dataType = "int", paramType = "path", required = true)
	@DeleteMapping("/{roleId}")
	public ResultMsg deleteRole(@PathVariable Integer roleId) {
		return new ResultMsg(roleService.removeById(roleId));
	}
}
