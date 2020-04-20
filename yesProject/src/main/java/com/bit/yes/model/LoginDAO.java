package com.bit.yes.model;

import java.sql.SQLException;
import java.util.Map;

import com.bit.yes.model.entity.UserVo;
public interface LoginDAO {
	public int insertOne(UserVo bean) throws Exception;
	public UserVo loginCheck(Map<String, String> params) throws Exception;
	public UserVo login(String id) throws SQLException;
	public String findId(String name,String email,String birth) throws Exception;
//	public String findPw(String id,String name,String birth,String email,String answer) throws SQLException;
	public String findPw(Map<String, String> params) throws Exception;
	public int updatePW(Map<String, String> param) throws Exception;
	public int updateInfo(UserVo bean) throws Exception;
	public int deleteOne(String id)throws Exception;
}
