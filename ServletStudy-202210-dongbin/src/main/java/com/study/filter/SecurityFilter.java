package com.study.filter;

import java.io.IOException;
import java.util.Arrays;
import java.util.List;
import java.util.concurrent.atomic.AtomicBoolean;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.annotation.WebFilter;
import javax.servlet.http.HttpFilter;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import com.study.domain.User;

@WebFilter("/*")
public class SecurityFilter extends HttpFilter implements Filter {
       
	private static final long serialVersionUID = 1L;

	public void destroy() {}

	public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException {
		HttpServletRequest req = (HttpServletRequest) request;
		HttpServletResponse resp = (HttpServletResponse) response;
		
		String requestURI = req.getRequestURI();
		String antMatchers1 = "/mypage, /mypage/password";
		String antMatchers2 = "/login, /register";
		String logoutURI = "/logout";
		String adminPage = "/admin";
		
		
		if(antMatchers1.contains(requestURI) && !authorization(req.getSession())) {
			resp.sendRedirect("/login");
			return;
		}
		
		if(antMatchers2.contains(requestURI) && authorization(req.getSession())) {
			resp.sendRedirect("/mypage");
			return;
		}
		
		if(logoutURI.equalsIgnoreCase(requestURI)) {
			req.getSession().invalidate(); // session 강제로 죽이기 로그인 정보 다 날라감  
			resp.sendRedirect("/login");
			return;
		}
		
		if(requestURI.contains(adminPage) && !hasRole(req.getSession(), "ADMIN")) { // 어드민에서 권한이 없을때!
			resp.sendError(403, "Forbidden");
			return;
		}
		
		chain.doFilter(request, response);
	}
	
	private boolean authorization(HttpSession session) {
		User principalUser = (User) session.getAttribute("principal");
		return principalUser != null;
	}
	
	private boolean hasRole(HttpSession session, String role) {
		AtomicBoolean result = new AtomicBoolean(false);
		
		if(authorization(session)) {
			
			User principalUser = (User) session.getAttribute("principal");
			
			String[] roleArray = principalUser.getRoles().replaceAll(" ", "").split(",");
			List<String> roleList = Arrays.asList(roleArray);
			
			roleList.forEach(r -> {
				if(r.startsWith("ROLE_") && r.contains(role)) { // ROLE_ 로 시작하면 true, 유저를 포함하고 있는가 있으면 true
					result.set(true);
				}
			});
			
		}
		
		return result.get();
	}

	public void init(FilterConfig fConfig) throws ServletException {}

}
