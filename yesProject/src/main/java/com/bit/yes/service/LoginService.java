package com.bit.yes.service;

import java.util.Map;

import com.bit.yes.model.entity.UserVo;

public interface LoginService {
	
	public int insertOne(UserVo bean) throws Exception; 
	
	public UserVo selectUserInfo(UserVo bean) throws Exception;
	
//	public String selectPassword(Map<String, String> params) throws Exception;
	public String selectPassword(UserVo currentUser) throws Exception;
	
	public String updatePW(UserVo bean) throws Exception;
	
	public String sendEmailTempPW(UserVo bean) throws Exception;
	
	public String selectID(String id) throws Exception;
	
	public String selectEmail(String email) throws Exception;
	
/*	public UserVo checkIDDup(String id) throws Exception;
	
	public UserVo checkEmailDup(String email) throws Exception;
*/	
	public String findID(UserVo user) throws Exception;
//	public String findID(Map<String, String> params) throws Exception;

}
