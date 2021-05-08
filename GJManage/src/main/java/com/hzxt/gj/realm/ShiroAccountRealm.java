package com.hzxt.gj.realm;

import java.util.Calendar;
import java.util.Date;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.apache.shiro.authc.AuthenticationException;
import org.apache.shiro.authc.AuthenticationInfo;
import org.apache.shiro.authc.AuthenticationToken;
import org.apache.shiro.authc.SimpleAuthenticationInfo;
import org.apache.shiro.authc.UsernamePasswordToken;
import org.apache.shiro.authc.credential.CredentialsMatcher;
import org.apache.shiro.authc.credential.HashedCredentialsMatcher;
import org.apache.shiro.authz.AuthorizationInfo;
import org.apache.shiro.authz.SimpleAuthorizationInfo;
import org.apache.shiro.realm.AuthorizingRealm;
import org.apache.shiro.subject.PrincipalCollection;
import org.apache.shiro.util.ByteSource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;

import com.hzxt.gj.pojo.Admin;
import com.hzxt.gj.service.AdminService;
import com.hzxt.gj.service.AuthService;

@Component
public class ShiroAccountRealm extends AuthorizingRealm {

	@Autowired
    @Lazy
    private AuthService authService;
    
	@Autowired
    @Lazy
    private AdminService adminService;
    
	@Value("${auth.login.try-time}")
	private Integer tryTime;
	
	@Value("${auth.login.interval-time}")
	private Integer intervalTime;
	
	/**
	 * 设置凭证(Credentials)匹配器
	 */
	@Override
	public void setCredentialsMatcher(
			CredentialsMatcher credentialsMatcher) {
		
		HashedCredentialsMatcher hashedCredentialsMatcher = 
				new HashedCredentialsMatcher();
		// 指定加密方式为MD5
		hashedCredentialsMatcher.setHashAlgorithmName("MD5");
		// 加密次数
		//hashedCredentialsMatcher.setHashIterations(2);
		hashedCredentialsMatcher.setStoredCredentialsHexEncoded(true);
		
		super.setCredentialsMatcher(hashedCredentialsMatcher);
	}
	
	/**
	 * 负责完成用户权限领域信息的获取以及封装
	 * @param principals
	 * @return 返回给授权管理器对象
	 */
	@Override
	public AuthorizationInfo doGetAuthorizationInfo(
			PrincipalCollection principals) {
		//1.基于用户id查找角色id
		Admin admin = (Admin) principals.getPrimaryPrincipal();
		List<Integer> roleIds=
				authService.findRoleIdsByAdminId(admin.getId());
		//2.基于角色id查找菜单id
		List<Integer> menuIds = 
				authService.findMenuIdsByRoleIds(roleIds.toArray(new Integer[] {}));
		//3.基于菜单id查找权限标识
		List<String> permissions = 
				authService.findPermissions(menuIds.toArray(new Integer[] {}));
		//4.对权限标识进行去重和空的操作
		Set<String> set = new HashSet<String>();
		for (String permission : permissions) {
			if (!StringUtils.isEmpty(permission)) {
				set.add(permission);
			}
		}
		//5.对权限标识信息进行封装
		SimpleAuthorizationInfo info = 
				new SimpleAuthorizationInfo();
		info.setStringPermissions(set);
		
		return info;
	}

	/**
	 * 负责完成认证领域信息的获取以及封装
	 * @param token
	 * @return 返回给认证管理器对象
	 */
	@Override
	protected AuthenticationInfo doGetAuthenticationInfo(
			AuthenticationToken token) 
					throws AuthenticationException {
		//1、从token对象获取用户名(用户输入的)
		UsernamePasswordToken upToken = 
				(UsernamePasswordToken) token;
		String account = upToken.getUsername();
		//2、基于用户名查询用户信息并进行身份校验
		Admin admin = adminService.findByAccount(account);
		if (null == admin) {
			throw new AuthenticationException("此用户不存在");
		}
		if (admin.getStatus() != 0) {
			throw new AuthenticationException("此用户已被禁用");
		}
		//3、判断恶意尝试密码
		Calendar calendar = Calendar.getInstance();
		calendar.setTime(admin.getErrorTime());
		calendar.add(Calendar.MINUTE, intervalTime);
		if (admin.getErrorCount() == tryTime && new Date().before(calendar.getTime())) {
			throw new AuthenticationException("密码输入错误次数过多，请" + intervalTime + "分钟后再试");
		}
		//4、对用户信息进行封装
		ByteSource credentialsSalt=
				ByteSource.Util.bytes(admin.getSalt());
		SimpleAuthenticationInfo info=
			new SimpleAuthenticationInfo(
					admin,//principal 用户身份
					admin.getPassword(),//hashedCredentials已加密的凭证
					credentialsSalt,//credentialsSalt 密码加密时使用的盐
					getName());//realmName 当前方法所在对象的名字
		
		return info;
	}
}
