package com.bit.yes.model;

import java.sql.SQLException;
import java.util.HashMap;
import java.util.List;

import com.bit.yes.model.entity.CCsVo;
import com.bit.yes.model.entity.ImageVo;
import com.bit.yes.model.entity.UserVo;
import com.bit.yes.model.entity.BranchAddressVo;
import com.bit.yes.model.entity.BranchInfoVo;

public interface CCsDAO {

	CCsVo selcetOne(int index) throws SQLException;
	
	int insertOne(CCsVo bean) throws SQLException;
	
	//List<C_CsVo> writeList(int offset, int noOfRecords,String clientID) throws SQLException;
	List<CCsVo> writeList(HashMap<String, Object> params) throws SQLException;
	
	BranchAddressVo c_selectAddress(String id) throws SQLException;
	
	List<BranchInfoVo> reserveList(String id) throws SQLException;

	List<BranchInfoVo> reserveOne(String id) throws SQLException;
	
	int writeGetCount(HashMap<String, Object> params) throws SQLException;
	
	int writeGetCount(String clientID) throws SQLException;
	
	UserVo c_selectnickname(String id) throws SQLException;
	
	List<ImageVo> c_counselSubImage(int index) throws SQLException;
	
	int c_counselImgUpload(ImageVo bean) throws SQLException;
	
	UserVo user_selcetOne(String id) throws SQLException;
}
