package com.bit.yes.model;

import java.sql.SQLException;
import java.util.List;

import com.bit.yes.model.entity.CCsVo;
import com.bit.yes.model.entity.ImageVo;
import com.bit.yes.model.entity.SCsVo;
import com.bit.yes.model.entity.UserVo;
import com.bit.yes.model.entity.BranchAddressVo;
import com.bit.yes.model.entity.BranchInfoVo;

public interface CounselAllDAO {

	CCsVo cselcetOne(int index) throws SQLException;
	
	List<CCsVo> cwriteList(int coffset, int cnoOfRecords) throws SQLException;
	
	List<CCsVo> sacwriteList(int coffset, int cnoOfRecords, String sDate, String eDate) throws SQLException;
	
	int sacwriteGetCount(String sDate, String eDate) throws SQLException;

	List<SCsVo> saswriteList(int soffset, int snoOfRecords, String sDate, String eDate) throws SQLException;
	
	int saswriteGetCount(String sDate, String eDate) throws SQLException;
	
	List<BranchInfoVo> creserveList(String id) throws SQLException;

	List<BranchInfoVo> creserveOne(String id) throws SQLException;
	
	int cwriteGetCount() throws SQLException;
	
	UserVo c_selectnickname(String id) throws SQLException;
	
	int cupdateOne(CCsVo bean) throws SQLException;
	
	
	////////////////////////////////////////////////////////
	
	SCsVo sselcetOne(int index) throws SQLException;
	
	List<SCsVo> swriteList(int soffset, int snoOfRecords) throws SQLException;
	
	int swriteGetCount() throws SQLException;
	
	UserVo s_selectnickname(String id) throws SQLException;
	
	int supdateOne(SCsVo bean) throws SQLException;
	
	////////////////////////////////////////////////////////
	
	List<ImageVo> c_counselSubImage(int index) throws SQLException;
	
	List<ImageVo> s_counselSubImage(int index) throws SQLException;
	
	BranchAddressVo c_selectAddress(String id) throws SQLException;
	
}
