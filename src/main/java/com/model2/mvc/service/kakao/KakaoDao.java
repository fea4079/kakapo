package com.model2.mvc.service.kakao;


import com.model2.mvc.service.domain.User;

public interface KakaoDao {
	public void addUser(User user) throws Exception ;
	
	public User getUser(String userId) throws Exception;
	
	public boolean checkDuplication(String userId) throws Exception;
}
