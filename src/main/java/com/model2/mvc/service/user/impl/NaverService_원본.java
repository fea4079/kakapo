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

import org.apache.ibatis.reflection.SystemMetaObject;
import org.json.simple.JSONObject;
import org.json.simple.JSONValue;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Controller;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.model2.mvc.service.domain.User;
import com.model2.mvc.service.naver.NaverDao;

@Controller
public class NaverService_원본{
    
	@Autowired
	@Qualifier("NaverDaoImpl")
	private NaverDao naverDao;
 
	public String getAccessToken(String authorize_code) {
		String access_Token = "";
		String refresh_Token = "";
		String reqURL = "https://nid.naver.com/oauth2.0/token";

		try {
			URL url = new URL(reqURL);
            
			HttpURLConnection conn = (HttpURLConnection) url.openConnection();
			// POST ��û�� ���� �⺻���� false�� setDoOutput�� true��
            
			conn.setRequestMethod("POST");
			conn.setDoOutput(true);
			// POST ��û�� �ʿ�� �䱸�ϴ� �Ķ���� ��Ʈ���� ���� ����
            
			BufferedWriter bw = new BufferedWriter(new OutputStreamWriter(conn.getOutputStream()));
			StringBuilder sb = new StringBuilder();
			sb.append("grant_type=authorization_code");
            
			sb.append("&client_id=FzMGbETEgw2xNeSUlIIF"); //������ �߱޹��� key
			sb.append("&client_secret=voluTpxuLM"); // ������ �߱޹��� secret                                         ���̹��� �߰���
			sb.append("&redirect_uri=http://192.168.0.159:8080/user/naverLogin"); // ������ ������ �ּ�
			
//			grant_type=authorization_code&client_id=3d89a9ef169b204afc54cc08fa20632d&redirect_uri=http://127.0.0.1:8080/user/kakaoLogin&code=" + authorize_code
            //sb.append("&scope=profaccount_email");
			sb.append("&code=" + authorize_code);
			bw.write(sb.toString());
			bw.flush();
            
			// ��� �ڵ尡 200�̶�� ����
			int responseCode = conn.getResponseCode();
			System.out.println("responseCode : " + responseCode);
            
			// ��û�� ���� ���� JSONŸ���� Response �޼��� �о����
			BufferedReader br = new BufferedReader(new InputStreamReader(conn.getInputStream(), "EUC-KR"));
			String line = "";
			String result = "";
            
			while ((line = br.readLine()) != null) {
				result += line;
			}
			System.out.println("NaverService.java response body1 : " + result);
			
			ObjectMapper objectMapper = new ObjectMapper();
			// JSON String -> Map
			Map<String, Object> jsonMap = objectMapper.readValue(result, new TypeReference<Map<String, Object>>() {
			});
				
			access_Token = jsonMap.get("access_token").toString();
			refresh_Token = jsonMap.get("refresh_token").toString();

			System.out.println("NaverService access_token : " + access_Token);
			System.out.println("NaverService refresh_token : " + refresh_Token);
            
			br.close();
			bw.close();
			
			
			
		} catch (IOException e) {
			e.printStackTrace();
			System.out.println("Error in getAccessToken: " + e.getMessage());
		}
		return access_Token;
	}
	
	
	
	public HashMap<String, Object> getUserInfo(String access_Token) throws Exception {

		// ��û�ϴ� Ŭ���̾�Ʈ���� ���� ������ �ٸ� �� �ֱ⿡ HashMapŸ������ ����
		HashMap<String, Object> userInfo = new HashMap<String, Object>();
		String reqURL = "https://openapi.naver.com/v1/nid/me";
		try {
			URL url = new URL(reqURL);
			HttpURLConnection conn = (HttpURLConnection) url.openConnection();
			conn.setRequestMethod("GET");

			// ��û�� �ʿ��� Header�� ���Ե� ����
			conn.setRequestProperty("Authorization", "Bearer " + access_Token);
			System.out.println("NaverService"+access_Token);
			
			
			int responseCode = conn.getResponseCode();
			System.out.println("NaverService.java responseCode : " + responseCode);

			BufferedReader br = new BufferedReader(new InputStreamReader(conn.getInputStream()));

			String line = "";
			String result = "";

			while ((line = br.readLine()) != null) {
				result += line;
			}
			System.out.println("NaverService.java response body : " + result);
			
			ObjectMapper mapper = new ObjectMapper();
			
			//JSONParser parser = new JSONParser(result);
			JSONObject element = (JSONObject) JSONValue.parse(result);
		
			String userId = mapper.readValue(element.get("id").toString(), String.class);
			System.out.println("NaverService.java id : "+userId);
			//JSONObject id = mapper.convertValue(element.get("id"), JSONObject.class);
			JSONObject properties = mapper.convertValue(element.get("properties"), JSONObject.class);
			System.out.println("NaverService.java properties ����ȯ : "+properties);
			JSONObject naver_account = mapper.convertValue(element.get("naver_account"), JSONObject.class);
			//JSONObject properties =(JSONObject) element.get("properties");
			//JSONObject kakao_account = (JSONObject) element.get("kakao_account");
			//String userId = mapper.convertValue(id.toString(), String.class);
			String nickname = mapper.convertValue(properties.get("nickname"), String.class);
			String email = mapper.convertValue(naver_account.get("email"), String.class);
			//String nickname = (String) element.get("nickname");
			//String email = (String) element.get("email");

			userInfo.put("nickname", nickname);
			userInfo.put("email", email);
			userInfo.put("id", userId);
			System.out.println("NaverService.java#####�г���"+userInfo.get("nickname"));
			System.out.println("NaverService.java#####�̸���"+userInfo.get("email"));
			
			NaverService_원본 ns = new NaverService_원본(); 
			
			
			
			if(naverDao.checkDuplication(userId)==false) {
				User user = new User();
				user.setUserId(userId);
				user.setPassword(userId);
				user.setUserName(nickname);
				user.setEmail(email);
				naverDao.addUser(user);
			}
				
			
		} catch (IOException e) {
			e.printStackTrace();
		}
		return userInfo;
	}
	
	public User getNaverUser(String userId) throws Exception {
		User user = naverDao.getUser(userId);
		return user;
	}
	
}
