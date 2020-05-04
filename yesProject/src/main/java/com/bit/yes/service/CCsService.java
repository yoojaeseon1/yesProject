package com.bit.yes.service;

import java.sql.SQLException;
import java.util.HashMap;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.bit.yes.model.CCsDAO;
import com.bit.yes.model.entity.CCsVo;
import com.bit.yes.model.entity.ImageVo;
import com.bit.yes.model.entity.UserVo;
import com.bit.yes.model.entity.BranchAddressVo;
import com.bit.yes.model.entity.BranchInfoVo;

@Service
public class CCsService {

	@Autowired
	CCsDAO csDAO;
	
	public CCsVo selectPage(int idx) throws SQLException {
		return csDAO.selcetOne(idx);
	}
	
	public void addPage(CCsVo bean) throws SQLException {
		csDAO.insertOne(bean);
	}
	
	public List<BranchInfoVo> reserveList(String id) throws SQLException{
		return csDAO.reserveList(id);
	}
	
	public List<BranchInfoVo> reserveOne(String id) throws SQLException{
		return csDAO.reserveOne(id);
	}
	
	// ����¡ ó��
//	public List<C_CsVo> writeList(int offset, int noOfRecords,String clientID) throws SQLException {
//		return csDAO.writeList(offset, noOfRecords, clientID);
//	}
//	
//	public int writeGetCount(String clientID) throws Exception {
//		return csDAO.writeGetCount(clientID);
//	}

	public UserVo selectNick(String id) throws SQLException{
		return csDAO.c_selectnickname(id);
	}
	public List<CCsVo> writeList(HashMap<String, Object> params) throws SQLException {
		return csDAO.writeList(params);
	}
	
	public int writeGetCount(HashMap<String, Object> params) throws Exception {
		return csDAO.writeGetCount(params);
	}
	
	// ���ε� ó��
	public void c_counselImgUpload(ImageVo bean) throws SQLException {
		csDAO.c_counselImgUpload(bean);
	}

	public List<ImageVo> c_counselSubImage(int index) throws SQLException {
		return csDAO.c_counselSubImage(index);
	}
	
	public BranchAddressVo c_selectAddress(String id) throws SQLException{
		return csDAO.c_selectAddress(id);
	}
	
	public UserVo user_selectOne(String id) throws SQLException {
		return csDAO.user_selcetOne(id);
	}
}
