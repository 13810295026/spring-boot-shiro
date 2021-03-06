package com.hzxt.gj.filter;

import java.io.IOException;

import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.shiro.web.filter.authc.FormAuthenticationFilter;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.hzxt.gj.vo.ResultMsg;

public class ShiroLoginFilter extends FormAuthenticationFilter {

	private static ObjectMapper objectMapper = new ObjectMapper();

	/**
	 * 如果isAccessAllowed返回false 则执行onAccessDenied
	 * 
	 * @param request
	 * @param response
	 * @param mappedValue
	 * @return
	 */
	@Override
	protected boolean isAccessAllowed(ServletRequest request, ServletResponse response, Object mappedValue) {
		if (request instanceof HttpServletRequest) {
			if (((HttpServletRequest) request).getMethod().toUpperCase().equals("OPTIONS")) {
				return true;
			}
		}

		return super.isAccessAllowed(request, response, mappedValue);
	}

	/**
	 * 在访问controller前判断是否登录，返回json，不进行重定向。
	 *
	 * @param request
	 * @param response
	 * @return true-继续往下执行，false-该filter过滤器已经处理，不继续执行其他过滤器
	 * @throws Exception
	 */
	@Override
	protected boolean onAccessDenied(ServletRequest request, ServletResponse response) throws IOException {
		HttpServletResponse httpServletResponse = (HttpServletResponse) response;
		// 如果不设置的接受的访问源,那么前端都会报跨域错误,因为这里还没到corsConfig里面
		httpServletResponse.setHeader("Access-Control-Allow-Origin",
				((HttpServletRequest) request).getHeader("Origin"));
		httpServletResponse.setHeader("Access-Control-Allow-Credentials", "true");
		httpServletResponse.setCharacterEncoding("UTF-8");
		httpServletResponse.setContentType("application/json");

//		if ("/proxy.stream".equals(((HttpServletRequest) request).getRequestURI())||
//			"/hystrix.stream".equals(((HttpServletRequest) request).getRequestURI())) {
//			return true;
//		}
		System.out.println(((HttpServletRequest) request).getRequestURI());

		ResultMsg result = new ResultMsg(210, "未登录");
		httpServletResponse.getWriter().write(objectMapper.writeValueAsString(result));

		return false;
	}
}
