package com.model2.mvc.service.user.impl;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.HashMap;
import java.util.Map;

import org.json.simple.JSONObject;
import org.json.simple.JSONValue;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.model2.mvc.service.domain.User;
import com.model2.mvc.service.naver.NaverDao;

@Service
public class NaverService {
    
	@Autowired
	@Qualifier("NaverDaoImpl")
	private NaverDao naverDao;
	
	 
	public String getAccessToken(String authorize_code) throws IOException{ //stat�κ� �߰�
		
		String access_Token = "";
		String refresh_Token = "";
		String reqURL = "https://nid.naver.com/oauth2.0/token";

		try {
			URL url = new URL(reqURL);
			System.out.println("여기까지 실행됨111111111111111");
            
			HttpURLConnection conn = (HttpURLConnection) url.openConnection();
			// POST ��û�� ���� �⺻���� false�� setDoOutput�� true��
            
			conn.setRequestMethod("POST");
			conn.setDoOutput(true);
			System.out.println("여기까지 실행됨22");
			// POST ��û�� �ʿ�� �䱸�ϴ� �Ķ���� ��Ʈ���� ���� ����
            
			BufferedWriter bw = new BufferedWriter(new OutputStreamWriter(conn.getOutputStream()));
			StringBuilder sb = new StringBuilder();
			sb.append("grant_type=authorization_code");
			sb.append("&client_id=FzMGbETEgw2xNeSUlIIF"); //������ �߱޹��� key
			sb.append("&redirect_uri=http://192.168.0.159:8080/user/naverLogin"); // ������ ������ �ּ�
			sb.append("&code=" + authorize_code);
			sb.append("&client_secret=voluTpxuLM"); // ������ �߱޹��� secret                                         ���̹��� �߰���
//			sb.append("&state=test"); //���� �ٽ� �߰�
			bw.write(sb.toString());
			bw.flush();
			System.out.println("여기까지 실행됨33");
            
			// ��� �ڵ尡 200�̶�� ����
			int responseCode = conn.getResponseCode();
			System.out.println("responseCode : " + responseCode);
            
			// ��û�� ���� ���� JSONŸ���� Response �޼��� �о����
			BufferedReader br = new BufferedReader(new InputStreamReader(conn.getInputStream(), "UTF-8"));
			String line = "";
			String result = "";
			System.out.println("여기까지 실행됨44");
			while ((line = br.readLine()) != null) {
				result += line;
			}
			System.out.println("NaverService.java response body1 : " + result);
			
			ObjectMapper objectMapper = new ObjectMapper();
			// JSON String -> Map
			System.out.println("여기까지 실행됨55");
			Map<String, Object> jsonMap = objectMapper.readValue(result, Map.class);
//			Map<String, Object> jsonMap = objectMapper.readValue(result, new TypeReference<Map<String, Object>>() {
//			});
				
			access_Token = jsonMap.get("access_token").toString();
			refresh_Token = jsonMap.get("refresh_token").toString();

			System.out.println("NaverService access_token : " + access_Token);
			System.out.println("NaverService refresh_token : " + refresh_Token);
            
			br.close();
			bw.close();
			System.out.println("여기까지 실행됨66");
			
			
			
		} catch (IOException e) {
			e.printStackTrace();
			System.out.println("Error in getAccessToken: " + e.getMessage());
		}
		return access_Token;
	}
	
	
	
	public Map<String, Object> getUserInfo(String access_Token) throws Exception {

		Map<String, Object> userInfo = new HashMap<>();
		String postURL = "https://openapi.naver.com/v1/nid/me";
		
		try {
			URL url = new URL(postURL);
			HttpURLConnection conn = (HttpURLConnection) url.openConnection();
			conn.setRequestMethod("POST");
			
			conn.setRequestProperty("Authorization", "Bearer " + access_Token);
			
			int responseCode = conn.getResponseCode();
			System.out.println("responseCode : " + responseCode);
			
			BufferedReader br = new BufferedReader(new InputStreamReader(conn.getInputStream()));
			String line = "";
	        String result ="";

	        while ((line = br.readLine()) != null) {
				result += line;
			}
	        System.out.println("response body : " + result);

	        // [방법 1 : String 사용]
//	        String data = "{\"id}
	        
	        ObjectMapper objectMapper = new ObjectMapper();
	        Map<String, Object> elvis_presley = objectMapper.readValue(result, Map.class);
	        
	        System.out.println("5678"+elvis_presley+"5678");
	        
	        
	        Map<String, Object> properties = (Map<String, Object>) elvis_presley.get("response");
	        
	        String nickname = properties.get("name").toString();
	        String id = properties.get("id").toString().substring(0,10);
            String email = properties.get("email").toString();
	        String name = properties.get("name").toString();
            
	        userInfo.put("name", nickname);
	        userInfo.put("id", id);
	        userInfo.put("email", email);
	        
	        System.out.println("닉네임 : "+nickname);
	        System.out.println("이메일 : "+email);
	        System.out.println("아이디 : "+id);
	        System.out.println(userInfo);
	        
	    if(naverDao.checkDuplication(id) == false) {
	    
	    	User user = new User();
	    	user.setUserId(id);
	    	user.setEmail(email);
	    	user.setPassword(id);
	    	user.setUserName(name);
	    	naverDao.addUser(user);
	    	System.out.println("호날두"+user+"메시");
	    }
	        
		}catch (IOException exception) {
			exception.printStackTrace();
		}
		return userInfo;
	}
	
	public User getNaverUser(String userId) throws Exception {
		User user = naverDao.getUser(userId);
		return user;
	}
	
}
	
