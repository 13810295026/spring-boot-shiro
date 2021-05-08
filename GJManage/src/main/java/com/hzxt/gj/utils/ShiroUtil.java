package com.hzxt.gj.utils;

import org.apache.shiro.SecurityUtils;

import com.hzxt.gj.pojo.Admin;

public class ShiroUtil {

	public static Admin getPrincipal() {
		return (Admin) SecurityUtils.getSubject().getPrincipal();
	}
}
