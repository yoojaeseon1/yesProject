package com.bit.yes.service;

import java.sql.SQLException;
import java.util.HashMap;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.bit.yes.model.C_CsDao;
import com.bit.yes.model.entity.C_CsVo;
import com.bit.yes.model.entity.ImageVo;
import com.bit.yes.model.entity.UserVo;
import com.bit.yes.model.entity.BranchAddressVo;
import com.bit.yes.model.entity.BranchInfoVo;

@Service
public class C_CsService {

	@Autowired
	C_CsDao csDao;
	
	public C_CsVo selectPage(int idx) throws SQLException {
		return csDao.selcetOne(idx);
	}
	
	public void addPage(C_CsVo bean) throws SQLException {
		csDao.insertOne(bean);
	}
	
	public List<BranchInfoVo> reserveList(String id) throws SQLException{
		return csDao.reserveList(id);
	}
	
	public List<BranchInfoVo> reserveOne(String id) throws SQLException{
		return csDao.reserveOne(id);
	}
	
	// ����¡ ó��
//	public List<C_CsVo> writeList(int offset, int noOfRecords,String clientID) throws SQLException {
//		return csDao.writeList(offset, noOfRecords, clientID);
//	}
//	
//	public int writeGetCount(String clientID) throws Exception {
//		return csDao.writeGetCount(clientID);
//	}

	public UserVo selectNick(String id) throws SQLException{
		return csDao.c_selectnickname(id);
	}
	public List<C_CsVo> writeList(HashMap<String, Object> params) throws SQLException {
		return csDao.writeList(params);
	}
	
	public int writeGetCount(HashMap<String, Object> params) throws Exception {
		return csDao.writeGetCount(params);
	}
	
	// ���ε� ó��
	public void c_counselImgUpload(ImageVo bean) throws SQLException {
		csDao.c_counselImgUpload(bean);
	}

	public List<ImageVo> c_counselSubImage(int index) throws SQLException {
		return csDao.c_counselSubImage(index);
	}
	
	public BranchAddressVo c_selectAddress(String id) throws SQLException{
		return csDao.c_selectAddress(id);
	}
	
	public UserVo user_selectOne(String id) throws SQLException {
		return csDao.user_selcetOne(id);
	}
}
