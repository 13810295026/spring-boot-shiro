package com.hzxt.gj.config;

import java.util.LinkedHashMap;
import java.util.Map;

import javax.servlet.Filter;

import org.apache.shiro.mgt.SecurityManager;
import org.apache.shiro.realm.AuthorizingRealm;
import org.apache.shiro.spring.LifecycleBeanPostProcessor;
import org.apache.shiro.spring.security.interceptor.AuthorizationAttributeSourceAdvisor;
import org.apache.shiro.spring.web.ShiroFilterFactoryBean;
import org.apache.shiro.web.mgt.DefaultWebSecurityManager;
import org.springframework.aop.framework.autoproxy.DefaultAdvisorAutoProxyCreator;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.DependsOn;

import com.hzxt.gj.filter.ShiroLoginFilter;

@Configuration
public class ShiroConfig {

//	@Bean("hashedCredentialsMatcher")
//    public HashedCredentialsMatcher hashedCredentialsMatcher() {
//        HashedCredentialsMatcher credentialsMatcher = 
//            new HashedCredentialsMatcher();
//        //指定加密方式为MD5
//        credentialsMatcher.setHashAlgorithmName("MD5");
//        //加密次数
//        credentialsMatcher.setHashIterations(2);
//        credentialsMatcher.setStoredCredentialsHexEncoded(true);
//        
//        return credentialsMatcher;
//    }

	@Bean("securityManager")
	public DefaultWebSecurityManager securityManager(
			AuthorizingRealm userRealm) {
		DefaultWebSecurityManager sManager = 
				new DefaultWebSecurityManager();
		
		// 此时必须保证realm对象已经存在了
		sManager.setRealm(userRealm);
		
		return sManager;
	}

	/**
	 * 配置Shiro的过滤器Bean工厂
	 * @param securityManager
	 * @return
	 */
	@Bean("shiroFilterFactoryBean")
	public ShiroFilterFactoryBean shiroFilterFactoryBean(
			@Qualifier("securityManager")
			DefaultWebSecurityManager securityManager) {//shiro 包
		
		ShiroFilterFactoryBean bean = new ShiroFilterFactoryBean();
		bean.setSecurityManager(securityManager);//设置安全管理器
	
        /**
         *  Shiro 内置过滤器，可以实现权限相关的拦截器
         *  常用的过滤器：
         *      anon:无需认证（登录）可以访问
         *      authc:必须认证才可以访问
         *      user:如果使用rememberMe的功能可以直接访问
         *      perms:该资源必须得到资源权限才可以访问
         *      role:该资源必须得到角色的权限才可以访问
         */
		LinkedHashMap<String, String> fcMap = new LinkedHashMap<>();
		fcMap.put("/static/**", "anon");// anon表示允许匿名访问
		//放行静态资源
		fcMap.put("/error/**", "anon");
		fcMap.put("/bower_components/**", "anon");
		fcMap.put("/build/**", "anon");
		fcMap.put("/dist/**", "anon");
		fcMap.put("/plugins/**", "anon");
		//放行第三方
		fcMap.put("/swagger-ui.html", "anon");
		fcMap.put("/swagger-resources/**", "anon");
		fcMap.put("/swagger/**", "anon");
		fcMap.put("/webjars/**", "anon");
		fcMap.put("/v2/**", "anon");
		fcMap.put("/csrf", "anon");
		//放行登陆登出
		fcMap.put("/login", "anon");
		fcMap.put("/auth/login", "anon");
		fcMap.put("/auth/logout", "logout");
		//拦截所有请求
		fcMap.put("/**", "authc");//authc标识必须授权才能访问
		
		bean.setFilterChainDefinitionMap(fcMap);

		// 当此用户是一个非认证用户,需要先登陆进行认证
		//bean.setLoginUrl("/login");

		// 用过滤器拦截非登录请求,返回Json串
		Map<String, Filter> filters = bean.getFilters();//获取filters
		filters.put("authc", new ShiroLoginFilter());
		
		return bean;
	}

	/**
	 * 配置shiro框架组件的生命周期管理对象
	 * @return
	 */
	@Bean("lifecycleBeanPostProcessor")
	public LifecycleBeanPostProcessor lifecycleBeanPostProcessor() {
		return new LifecycleBeanPostProcessor();
	}

	/**
	 * 配置负责为Bean对象(需要授权访问的方法所在的对象)
	 * 创建代理对象的Bean组件
	 * @return
	 */
	@DependsOn(value = "lifecycleBeanPostProcessor")
	@Bean
	public DefaultAdvisorAutoProxyCreator defaultAdvisorAutoProxyCreator() {
		DefaultAdvisorAutoProxyCreator defaultAdvisorAutoProxyCreator = 
				new DefaultAdvisorAutoProxyCreator();
		//defaultAdvisorAutoProxyCreator.setUsePrefix(true);
		
		return defaultAdvisorAutoProxyCreator;
	}

	/**
	 * 配置授权属性应用对象(在执行授权操作时需要用到此对象)
	 * 
	 * @param securityManager
	 * @return
	 */
	@Bean
	public AuthorizationAttributeSourceAdvisor authorizationAttributeSourceAdvisor(
			SecurityManager securityManager) {
		AuthorizationAttributeSourceAdvisor bean = 
				new AuthorizationAttributeSourceAdvisor();
		bean.setSecurityManager(securityManager);

		return bean;
	}
	
//	 @Bean
//	 public EhCacheManager newCacheManager() {
//		 EhCacheManager cManager=new EhCacheManager();
//		 cManager.setCacheManagerConfigFile("classpath:ehcache.xml");
//		 return cManager;
//	 }
}
