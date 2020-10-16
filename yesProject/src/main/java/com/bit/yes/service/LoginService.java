package com.bit.yes.service;

import javax.servlet.http.HttpSession;

import com.bit.yes.model.entity.UserVo;

public interface LoginService {
	
	public int insertOne(UserVo user) throws Exception; 
	
	public UserVo selectUserInfo(UserVo user) throws Exception;
	
	public String selectPassword(UserVo user) throws Exception;
	
	public String updatePW(UserVo user) throws Exception;
	
	public String sendEmailTempPW(UserVo user) throws Exception;
	
	public String selectID(String id) throws Exception;
	
	public String selectEmail(String email) throws Exception;
	
	public String login(UserVo user, HttpSession session) throws Exception;
	
	public String loginWithNaver(UserVo user, HttpSession session) throws Exception;
	
	public String loginWithKakao(UserVo user, HttpSession session) throws Exception;
	
	public String checkLogined(String clientID, HttpSession session) throws Exception;
	
	public String findID(UserVo user) throws Exception;

}
