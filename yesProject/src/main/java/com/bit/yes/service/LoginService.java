package com.bit.yes.service;

import java.util.Map;

import com.bit.yes.model.entity.UserVo;

public interface LoginService {
	
	public int insertOne(UserVo bean) throws Exception; 
	
	public UserVo loginCheck(Map<String,String> params) throws Exception;
	
	public String findPw(Map<String, String> params) throws Exception;
	
	public int updatePW(Map<String, String> param) throws Exception;
	
	public UserVo checkIDDup(String id) throws Exception;
	
	public String findID(Map<String, String> params) throws Exception;

}
