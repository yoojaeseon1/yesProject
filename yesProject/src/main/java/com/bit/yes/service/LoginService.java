package com.bit.yes.service;

import java.util.Map;

import com.bit.yes.model.entity.UserVo;

public interface LoginService {
	
	public int insertOne(UserVo bean) throws Exception; 
	
	public UserVo selectUserInfo(UserVo bean) throws Exception;
	
	public String selectPassword(Map<String, String> params) throws Exception;
	
	public int updatePW(Map<String, String> param) throws Exception;
	
	public String selectID(String id) throws Exception;
	
	public String selectEmail(String email) throws Exception;
	
/*	public UserVo checkIDDup(String id) throws Exception;
	
	public UserVo checkEmailDup(String email) throws Exception;
*/	
	public String findID(Map<String, String> params) throws Exception;

}
