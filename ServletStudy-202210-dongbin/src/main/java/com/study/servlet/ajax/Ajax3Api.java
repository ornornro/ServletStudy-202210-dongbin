package com.study.servlet.ajax;

import java.io.IOException;
import java.io.PrintWriter;
import java.nio.charset.StandardCharsets;
import java.util.List;
import java.util.Map;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonObject;
import com.study.util.DTO;

@WebServlet("/api/ajax3")
public class Ajax3Api extends HttpServlet {
	private static final long serialVersionUID = 1L;
       

	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		Gson gson = new GsonBuilder().setPrettyPrinting().create();
		
		String jsonData = request.getParameter("data");
		Map<String, Object> dataMap = gson.fromJson(jsonData, Map.class);
		
//		List<String> phoneNumbers = new ArrayList<>();
//		
//		dataMap.forEach((key, value) -> {
//			if(value.getClass() == String.class) {
//				phoneNumbers.add((String)value);
//			}
//		});
		
		response.setContentType("application/json; charset=utf8");
		response.setCharacterEncoding(StandardCharsets.UTF_8.name());
		PrintWriter out = response.getWriter();
		
		StringBuilder stringBuilder = new StringBuilder(); // 쓸데없는 메모리의 할당을 막기위해 사용한다.
		dataMap.forEach((key, value) -> {
			stringBuilder.append(value);
			stringBuilder.append("-");
		});
		
		stringBuilder.delete(stringBuilder.length() - 1, stringBuilder.length()); // 마지막에 붙는 - 없애는 방법 
		
		JsonObject jsonObject = new JsonObject();
		jsonObject.addProperty("responseData", stringBuilder.toString());
		
		out.print(jsonObject.toString()); // toString 꼭 해야지만 문자열로 관주 
		
	}

}
