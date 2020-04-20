package com.bit.yes.service;

import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.bit.yes.model.LoginDAO;
import com.bit.yes.model.entity.UserVo;

@Service
public class LoginServiceImpl implements LoginService{

	@Autowired
	LoginDAO loginDAO;

	@Override
	public int insertOne(UserVo bean) throws Exception {
		
		return loginDAO.insertOne(bean);
		
	}

	@Override
	public UserVo loginCheck(Map<String, String> params) throws Exception {
		
		return loginDAO.loginCheck(params);
	}
	

	@Override
	public String findPw(Map<String, String> params) throws Exception {
		
		return null;
	}

	@Override
	public int updatePW(Map<String, String> param) throws Exception {
		
		return 0;
	}
	
	
	
	
	
}
