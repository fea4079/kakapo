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
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import com.model2.mvc.service.domain.User;
import com.model2.mvc.service.naver.NaverDao;

@Controller
public class NaverService {
    
	@Autowired
	@Qualifier("NaverDaoImpl")
	private NaverDao naverDao;
	
	 
	public String getAccessToken(String authorize_code) { //stat부분 추가
		String access_Token = "";
		String refresh_Token = "";
		String reqURL = "https://nid.naver.com/oauth2.0/token";

		try {
			URL url = new URL(reqURL);
            
			HttpURLConnection conn = (HttpURLConnection) url.openConnection();
			// POST 요청을 위해 기본값이 false인 setDoOutput을 true로
            
			conn.setRequestMethod("POST");
			conn.setDoOutput(true);
			// POST 요청에 필요로 요구하는 파라미터 스트림을 통해 전송
            
			BufferedWriter bw = new BufferedWriter(new OutputStreamWriter(conn.getOutputStream()));
			StringBuilder sb = new StringBuilder();
			sb.append("grant_type=authorization_code");
            
			sb.append("&client_id=FzMGbETEgw2xNeSUlIIF"); //본인이 발급받은 key
			sb.append("&client_secret=voluTpxuLM"); // 본인이 발급받은 secret                                         네이버에 추가함
			sb.append("&redirect_uri=http://192.168.0.159:8080/user/naverLogin"); // 본인이 설정한 주소
			
//			grant_type=authorization_code&client_id=3d89a9ef169b204afc54cc08fa20632d&redirect_uri=http://127.0.0.1:8080/user/kakaoLogin&code=" + authorize_code
            //sb.append("&scope=profaccount_email");
			sb.append("&code=" + authorize_code);
			sb.append("&state=url_parameter"); //여기 다시 추가
//			sb.append("&state=" + state);  	// 여기 추가함
			bw.write(sb.toString());
			bw.flush();
            
			// 결과 코드가 200이라면 성공
			int responseCode = conn.getResponseCode();
			System.out.println("responseCode : " + responseCode);
            
			// 요청을 통해 얻은 JSON타입의 Response 메세지 읽어오기
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

		// 요청하는 클라이언트마다 가진 정보가 다를 수 있기에 HashMap타입으로 선언
		HashMap<String, Object> userInfo = new HashMap<String, Object>();
		String reqURL = "https://openapi.naver.com/v1/nid/me";
		try {
			URL url = new URL(reqURL);
			HttpURLConnection conn = (HttpURLConnection) url.openConnection();
			conn.setRequestMethod("GET");

			// 요청에 필요한 Header에 포함될 내용
			conn.setRequestProperty("Authorization", "Bearer " + access_Token);
			System.out.println("NaverService getUserInfo "+access_Token);
			
			
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
			System.out.println("NaverService.java properties 형변환 : "+properties);
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
			System.out.println("NaverService.java#####닉네임"+userInfo.get("nickname"));
			System.out.println("NaverService.java#####이메일"+userInfo.get("email"));
			
			NaverService ns = new NaverService(); 
			
			
			
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

//public String getAccessToken (String authorize_code) {
//	String access_Token = "";
//	String reqURL = "https://nid.naver.com/oauth2.0/token";
//	
//    try {
//        URL url = new URL(reqURL);
//        HttpURLConnection conn = (HttpURLConnection) url.openConnection();
//        
//        //POST 요청을 위해 기본값이 false인 setDoOutput을 true로
//        conn.setRequestMethod("POST");
//        conn.setDoOutput(true);
//        //POST 요청에 필요로 요구하는 파라미터 스트림을 통해 전송
//        BufferedWriter bw = new BufferedWriter(new OutputStreamWriter(conn.getOutputStream()));
//        StringBuilder sb = new StringBuilder();
//        sb.append("grant_type=authorization_code");
//        sb.append("&client_id=FzMGbETEgw2xNeSUlIIF");
//        sb.append("&client_secret=voluTpxuLM");
//        sb.append("&redirect_uri=http://192.168.0.159:8080/user/naverLogin");
//        sb.append("&code="+authorize_code);
//        sb.append("&state=url_parameter");
//        bw.write(sb.toString());
//        bw.flush();
//        
//        //결과 코드가 200이라면 성공
//        int responseCode = conn.getResponseCode();
//        if(responseCode==200){
//            //요청을 통해 얻은 JSON타입의 Response 메세지 읽어오기
//            BufferedReader br = new BufferedReader(new InputStreamReader(conn.getInputStream()));
//            String line = "";
//            String result = "";
//            
//            while ((line = br.readLine()) != null) {
//                result += line;
//            }
//            
//            //Gson 라이브러리에 포함된 클래스로 JSON파싱 객체 생성
//            JsonParser parser = new JsonParser();
//            JsonElement element = parser.parse(result);
//            
//            access_Token = element.getAsJsonObject().get("access_token").getAsString();
//          //refresh_Token = element.getAsJsonObject().get("refresh_token").getAsString();
//            br.close();
//            bw.close();
//        }
//    } catch (IOException e) {
//        e.printStackTrace();
//    } 
//    
//    return access_Token;
//}
	
//	public void getUserInfo (String access_Token) {
//	    //요청하는 클라이언트마다 가진 정보가 다를 수 있기에 HashMap타입으로 선언
//	    HashMap<String, Object> naverUserInfo = new HashMap<>();
//	    String reqURL = "https://openapi.naver.com/v1/nid/me";
//	    try {
//	        URL url = new URL(reqURL);
//	        HttpURLConnection conn = (HttpURLConnection) url.openConnection();
//	        conn.setRequestMethod("POST");
//	        
//	        //요청에 필요한 Header에 포함될 내용
//	        conn.setRequestProperty("Authorization", "Bearer " + access_Token);
//	        
//	        int responseCode = conn.getResponseCode();
//	        if(responseCode == 200){
//		        BufferedReader br = new BufferedReader(new InputStreamReader(conn.getInputStream()));
//		        
//		        String line = "";
//		        String result = "";
//		        
//		        while ((line = br.readLine()) != null) {
//		            result += line;
//		        }
//		        JsonParser parser = new JsonParser();
//		        JsonElement element = parser.parse(result);
//		        
//		        JsonObject response = element.getAsJsonObject().get("response").getAsJsonObject();
//		        
//		        String name = response.getAsJsonObject().get("name").getAsString();
//		        String email = response.getAsJsonObject().get("email").getAsString();
//		        String id = "NAVER_"+response.getAsJsonObject().get("id").getAsString();
//		        
//		        naverUserInfo.put("name", name);
//		        naverUserInfo.put("email", email);
//		        naverUserInfo.put("id", id);
//		        
//	        }
//	    } catch (IOException e) {
//	        e.printStackTrace();
//	    }
//	}
	
	
