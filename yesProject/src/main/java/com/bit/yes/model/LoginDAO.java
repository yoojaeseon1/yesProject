package com.bit.yes.model;

import java.util.Map;

import com.bit.yes.model.entity.UserVo;

public interface LoginDAO {
	public int insertOne(UserVo user) throws Exception;
	public String selectID(String id) throws Exception;
	public String selectEmail(String email) throws Exception;
	public UserVo selectUserInfo(UserVo user) throws Exception;
	public String findID(UserVo user) throws Exception;
//	public String findID(Map<String, String> params) throws Exception;
	public String selectPassword(UserVo currentUser) throws Exception;
//	public String selectPassword(Map<String, String> params) throws Exception;
	public int updatePW(UserVo user) throws Exception;
	public int updateInfo(UserVo user) throws Exception;
	public int deleteOne(String id)throws Exception;
}
