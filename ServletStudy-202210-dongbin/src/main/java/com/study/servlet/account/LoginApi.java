package com.study.servlet.account;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.study.domain.User;
import com.study.service.AccountService;
import com.study.util.DTO;

@WebServlet("/auth/login")
public class LoginApi extends HttpServlet {
	private static final long serialVersionUID = 1L;

	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		Map<String, String> loginUser = DTO.getParams(request);
		AccountService accountService = AccountService.getInstance();
		
		User user = accountService.loadUserByUsername(loginUser.get("username"));
		
		if(user == null) {
			System.out.println("아이디 틀림!");
			// error_login.html 만들고 -> script에서 사용자 정보를 확인해주세요. history.back(); 로그인 창으로 돌려쥐기 
			return;
		}
		
		if(!accountService.checkPassword(user, loginUser.get("password"))) {
			System.out.println("비밀번호 틀림!");
			return;
		}
		
		// 로그인 성
		
	}

}
