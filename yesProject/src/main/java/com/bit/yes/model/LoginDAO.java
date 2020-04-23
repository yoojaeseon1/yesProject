package com.bit.yes.model;

import java.util.Map;

import com.bit.yes.model.entity.UserVo;

public interface LoginDAO {
	public int insertOne(UserVo bean) throws Exception;
	public UserVo loginCheck(Map<String, String> params) throws Exception;
	public UserVo checkIDDup(String id) throws Exception;
	public String findID(Map<String, String> params) throws Exception;
	public String findPW(Map<String, String> params) throws Exception;
	public int updatePW(Map<String, String> param) throws Exception;
	public int updateInfo(UserVo bean) throws Exception;
	public int deleteOne(String id)throws Exception;
}
