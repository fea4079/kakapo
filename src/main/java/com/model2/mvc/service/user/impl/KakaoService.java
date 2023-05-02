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
import com.model2.mvc.service.kakao.KakaoDao;

@Service
public class KakaoService {
	
	@Autowired
	@Qualifier("KakaoDaoImpl")
	private KakaoDao kakaoDao;
	
	public String getAccessToken(String authorize_code) {
				    
		String access_Token = "";
		String refresh_Token = "";
		String reqURL = "https://kauth.kakao.com/oauth/token";

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
            
			sb.append("&client_id=3d89a9ef169b204afc54cc08fa20632d"); //������ �߱޹��� key
			sb.append("&redirect_uri=http://192.168.0.159:8080/user/kakaoLogin"); // ������ ������ �ּ�
			
//			grant_type=authorization_code&client_id=3d89a9ef169b204afc54cc08fa20632d&redirect_uri=http://127.0.0.1:8080/user/kakaoLogin&code=" + authorize_code
            //sb.append("&scope=profaccount_email");
			sb.append("&code=" + authorize_code);
			bw.write(sb.toString());
			bw.flush();
            
			// ��� �ڵ尡 200�̶�� ����
			int responseCode = conn.getResponseCode();
			System.out.println("responseCode : " + responseCode);
            
			// ��û�� ���� ���� JSONŸ���� Response �޼��� �о����
			BufferedReader br = new BufferedReader(new InputStreamReader(conn.getInputStream(), "UTF-8"));
			String line = "";
			String result = "";
            
			while ((line = br.readLine()) != null) {
				result += line;
			}
			System.out.println("response body1 : " + result);
			
			ObjectMapper objectMapper = new ObjectMapper();
			// JSON String -> Map
			Map<String, Object> jsonMap = objectMapper.readValue(result, new TypeReference<Map<String, Object>>() {
			});
				
			access_Token = jsonMap.get("access_token").toString();
			refresh_Token = jsonMap.get("refresh_token").toString();

			System.out.println("access_token : " + access_Token);
			System.out.println("refresh_token : " + refresh_Token);
            
			br.close();
			bw.close();
			
			
			
		} catch (IOException e) {
			e.printStackTrace();
		}
		return access_Token;
	}
	
	
	
	public HashMap<String, Object> getUserInfo(String access_Token) throws Exception {

		// ��û�ϴ� Ŭ���̾�Ʈ���� ���� ������ �ٸ� �� �ֱ⿡ HashMapŸ������ ����
		HashMap<String, Object> userInfo = new HashMap<String, Object>();
		String reqURL = "https://kapi.kakao.com/v2/user/me";
		try {
			URL url = new URL(reqURL);
			HttpURLConnection conn = (HttpURLConnection) url.openConnection();
			conn.setRequestMethod("GET");

			// ��û�� �ʿ��� Header�� ���Ե� ����
			conn.setRequestProperty("Authorization", "Bearer " + access_Token);
			
			
			int responseCode = conn.getResponseCode();
			System.out.println("responseCode : " + responseCode);

			BufferedReader br = new BufferedReader(new InputStreamReader(conn.getInputStream()));

			String line = "";
			String result = "";

			while ((line = br.readLine()) != null) {
				result += line;
			}
			System.out.println("response body : " + result);
			
			ObjectMapper mapper = new ObjectMapper();
			
			//JSONParser parser = new JSONParser(result);
			JSONObject element = (JSONObject) JSONValue.parse(result);
			
			String userId = mapper.readValue(element.get("id").toString(), String.class);
			System.out.println("id : "+userId);
			//JSONObject id = mapper.convertValue(element.get("id"), JSONObject.class);
			JSONObject properties = mapper.convertValue(element.get("properties"), JSONObject.class);
			System.out.println("properties ����ȯ : "+properties);
			JSONObject kakao_account = mapper.convertValue(element.get("kakao_account"), JSONObject.class);
			//JSONObject properties =(JSONObject) element.get("properties");
			//JSONObject kakao_account = (JSONObject) element.get("kakao_account");
			//String userId = mapper.convertValue(id.toString(), String.class);
			String nickname = mapper.convertValue(properties.get("nickname"), String.class);
			String email = mapper.convertValue(kakao_account.get("email"), String.class);
			//String nickname = (String) element.get("nickname");
			//String email = (String) element.get("email");

			userInfo.put("nickname", nickname);
			userInfo.put("email", email);
			userInfo.put("id", userId);
			System.out.println("#####�г���"+userInfo.get("nickname"));
			System.out.println("#####�̸���"+userInfo.get("email"));
			
			KakaoService ks = new KakaoService(); 
			
			
			
			if(kakaoDao.checkDuplication(userId)==false) {
				User user = new User();
				user.setUserId(userId);
				user.setPassword(userId);
				user.setUserName(nickname);
				user.setEmail(email);
				kakaoDao.addUser(user);
			}
				
			
		} catch (IOException e) {
			e.printStackTrace();
		}
		return userInfo;
	}
	
	public User getKakaoUser(String userId) throws Exception {
		User user = kakaoDao.getUser(userId);
		return user;
	}
	
}
