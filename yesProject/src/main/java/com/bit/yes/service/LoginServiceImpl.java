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
	public UserVo selectUserInfo(UserVo bean) throws Exception {
		
		return loginDAO.selectUserInfo(bean);
	}
	

	@Override
	public String selectPassword(Map<String, String> params) throws Exception {
		
		return loginDAO.selectPassword(params);
	}

	@Override
	public int updatePW(Map<String, String> param) throws Exception {
		
		return 0;
	}

	@Override
	public String selectID(String id) throws Exception {
		
		return loginDAO.selectID(id);
	}
	
	@Override
	public String selectEmail(String email) throws Exception {
		
		return loginDAO.selectEmail(email);
		
	}

	@Override
	public String findID(Map<String, String> params) throws Exception {
		
		return loginDAO.findID(params);
	}

}
