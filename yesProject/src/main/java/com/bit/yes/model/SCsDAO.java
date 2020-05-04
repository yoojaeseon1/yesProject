package com.bit.yes.model;

import java.sql.SQLException;
import java.util.HashMap;
import java.util.List;

import com.bit.yes.model.entity.ImageVo;
import com.bit.yes.model.entity.SCsVo;
import com.bit.yes.model.entity.UserVo;


public interface SCsDAO {

	SCsVo selcetOne(int index) throws SQLException;
	
	int insertOne(SCsVo bean) throws SQLException;
	
	//List<S_CsVo> writeList(int offset, int noOfRecords,String writer) throws SQLException;
	List<SCsVo> writeList(HashMap<String, Object> params) throws SQLException;
	
	//int writeGetCount(String clientID) throws SQLException;
	int writeGetCount(HashMap<String, Object> params) throws SQLException;
	
	UserVo s_selectnickname(String id) throws SQLException;
	
	List<ImageVo> s_counselSubImage(int index) throws SQLException;
	
	int s_counselImgUpload(ImageVo bean) throws SQLException;
	
	
}
