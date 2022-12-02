package com.study.filter;

import java.io.IOException;
import java.nio.charset.StandardCharsets;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.annotation.WebFilter;
import javax.servlet.http.HttpFilter;
import javax.servlet.http.HttpServletRequest;

@WebFilter("/*")
public class EncordingFilter extends HttpFilter implements Filter {
       
	private static final long serialVersionUID = 1L;

	public void destroy() {
	}

	public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException {
		HttpServletRequest hsr = (HttpServletRequest) request; // 업캐스팅 
		
		if(hsr.getMethod().equalsIgnoreCase("POST")) { // 대소문자 구분없이 비교하겠다
			request.setCharacterEncoding(StandardCharsets.UTF_8.name());
		}
		// 전처
		chain.doFilter(request, response); // chain = 서블릿 
		// 후처리 
	}

	public void init(FilterConfig fConfig) throws ServletException {
	}

}
