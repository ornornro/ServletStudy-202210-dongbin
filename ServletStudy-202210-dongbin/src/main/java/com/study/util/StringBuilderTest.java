package com.study.util;

public class StringBuilderTest {
	public static void main(String[] args) {
		String str = "문자열1";
		str += "문자열2";
		str = str.replaceAll("문자열2", "문자열3");
		
		System.out.println(str);
		
		StringBuilder builder = new StringBuilder(16); // 비동기 // capa 여유공간! stringbuilder는 값이 계속 더해줘도 주소가 바뀌지않고 메모리만 계속해서 늘어난다.
		// 필요없으면 자동으로 또 지운다.
		
		StringBuffer buffer = new StringBuffer(); // 동기 thread 환경에서는 buffer를 사용 아니라면 builder를 사용.
	}
}
