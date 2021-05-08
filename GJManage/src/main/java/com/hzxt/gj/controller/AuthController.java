package com.hzxt.gj.controller;

import java.util.Calendar;
import java.util.Date;

import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.AuthenticationException;
import org.apache.shiro.authc.IncorrectCredentialsException;
import org.apache.shiro.authc.UsernamePasswordToken;
import org.apache.shiro.subject.Subject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.hzxt.gj.pojo.Admin;
import com.hzxt.gj.realm.ShiroAccountRealm;
import com.hzxt.gj.service.AdminService;
import com.hzxt.gj.service.AuthService;
import com.hzxt.gj.utils.ShiroUtil;
import com.hzxt.gj.vo.ResultMsg;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;

@Api(tags = "管理端权限接口")
@RequestMapping("/auth")
@RestController
public class AuthController {

	@Autowired
	private AuthService authService;

	@Autowired
	private AdminService adminService;

	@Autowired
	private ShiroAccountRealm shiroAccountRealm;

	@Value("${auth.login.try-time}")
	private Integer tryTime;

	@Value("${auth.login.interval-time}")
	private Integer intervalTime;

	@ApiOperation(value = "用户登录", httpMethod = "POST", notes = "用户登录验证")
	@ApiImplicitParams({
		@ApiImplicitParam(name = "u", value = "登陆帐号", dataType = "String", paramType = "form", defaultValue = "admin", required = true),
		@ApiImplicitParam(name = "p", value = "登陆密码", dataType = "String", paramType = "form", defaultValue = "0000", required = true) })
	@PostMapping(value = "/login", consumes = "application/x-www-form-urlencoded")
	public ResultMsg login(@RequestParam("u") String account, @RequestParam("p") String password) {
		UsernamePasswordToken token = new UsernamePasswordToken(account, password);
		Subject subject = SecurityUtils.getSubject();
		Admin entity = adminService.findByAccount(account);
		Admin admin = new Admin();

		try {
			subject.login(token);
		} catch (IncorrectCredentialsException e) {
			Calendar calendar = Calendar.getInstance();
			calendar.setTime(entity.getErrorTime());
			calendar.add(Calendar.MINUTE, intervalTime);
			if (new Date().before(calendar.getTime())) {
				if (entity.getErrorCount() == tryTime - 1) {
					admin.setErrorCount(tryTime);
					admin.setErrorTime(new Date());
				} else {
					admin.setErrorCount(entity.getErrorCount() + 1);
				}
			} else {
				admin.setErrorCount(1);
				admin.setErrorTime(new Date());
			}
			adminService.modifyObject(admin);
			
			throw new AuthenticationException("输入的密码不正确");
		}

		admin.setId(entity.getId());
		admin.setLastLoginTime(new Date());
		adminService.modifyObject(admin);

		return new ResultMsg(entity.getId());
	}

	@ApiOperation(value = "用户登出", httpMethod = "GET", notes = "用户退出登录")
	@GetMapping("/logout")
	public void logout() {

	}
	
	@ApiOperation(value = "当前用户", httpMethod = "GET", notes = "取得登录用户")
	@GetMapping("/admin")
	public ResultMsg getAdmin() {

		return new ResultMsg(ShiroUtil.getPrincipal());
	}

	@ApiOperation(value = "菜单权限", httpMethod = "GET", notes = "取得用户拥有的菜单权限列表")
	@GetMapping("/menus/{adminId}")
	public ResultMsg getAdminMenu(@PathVariable Integer adminId) {
		return new ResultMsg(authService.findAuthByAdminId(adminId));
	}

	@ApiOperation(value = "功能权限", httpMethod = "GET", notes = "取得用户拥有的功能权限列表")
	@GetMapping("/permission")
	public ResultMsg getAdminAuth() {
		return new ResultMsg(shiroAccountRealm.doGetAuthorizationInfo(SecurityUtils.getSubject().getPrincipals()));
	}
}
