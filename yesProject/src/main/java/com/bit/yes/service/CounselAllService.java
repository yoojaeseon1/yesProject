package com.bit.yes.service;

import java.sql.SQLException;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.bit.yes.model.CounselAllDao;
import com.bit.yes.model.entity.C_CsVo;
import com.bit.yes.model.entity.ImageVo;
import com.bit.yes.model.entity.S_CsVo;
import com.bit.yes.model.entity.UserVo;
import com.bit.yes.model.entity.BranchAddressVo;
import com.bit.yes.model.entity.BranchInfoVo;

@Service
public class CounselAllService {

	@Autowired
	CounselAllDao csaDao;
	
	public C_CsVo cselectPage(int idx) throws SQLException {
		return csaDao.cselcetOne(idx);
	}
	
	public List<BranchInfoVo> creserveList(String id) throws SQLException{
		return csaDao.creserveList(id);
	}
	
	public List<BranchInfoVo> creserveOne(String id) throws SQLException{
		return csaDao.creserveOne(id);
	}
	
	// ����¡ ó��
	public List<C_CsVo> cwriteList(int coffset, int cnoOfRecords) throws SQLException {
		return csaDao.cwriteList(coffset, cnoOfRecords);
	}
	
	public int cwriteGetCount() throws Exception {
		return csaDao.cwriteGetCount();
	}

	public UserVo cselectNick(String id) throws SQLException{
		return csaDao.c_selectnickname(id);
	}
	
	public void cupdatePage(C_CsVo bean) throws SQLException {
		csaDao.cupdateOne(bean);
	}
	
	////////////////////////////////////////////
	
	public S_CsVo sselectPage(int idx) throws SQLException {
		return csaDao.sselcetOne(idx);
	}
	
	// ����¡ ó��
	public List<S_CsVo> swriteList(int soffset, int snoOfRecords) throws SQLException {
		return csaDao.swriteList(soffset, snoOfRecords);
	}
	
	public int swriteGetCount() throws Exception {
		return csaDao.swriteGetCount();
	}
	
	public UserVo sselectNick(String id) throws SQLException{
		return csaDao.s_selectnickname(id);
	}
	
	public void supdatePage(S_CsVo bean) throws SQLException {
		csaDao.supdateOne(bean);
		
	}
	
	// ��¥ �˻� �� ����¡ ó��
	
	public List<C_CsVo> sacwriteList(int coffset, int cnoOfRecords,String sDate, String eDate) throws SQLException {
		return csaDao.sacwriteList(coffset, cnoOfRecords, sDate, eDate);
	}
	
	public int sacwriteGetCount(String sDate, String eDate) throws Exception {
		return csaDao.sacwriteGetCount(sDate, eDate);
	}
	// ��¥ �˻� �� ����¡ ó��
	
	public List<S_CsVo> saswriteList(int soffset, int snoOfRecords,String sDate, String eDate) throws SQLException {
		return csaDao.saswriteList(soffset, snoOfRecords, sDate, eDate);
	}
	
	public int saswriteGetCount(String sDate, String eDate) throws Exception {
		return csaDao.saswriteGetCount(sDate, eDate);
	}
	
	// ���ε� ó��
	
	public List<ImageVo> s_counselSubImage(int index) throws SQLException {
		return csaDao.s_counselSubImage(index);
	}

	public List<ImageVo> c_counselSubImage(int index) throws SQLException {
		return csaDao.c_counselSubImage(index);
	}
	
	public BranchAddressVo c_selectAddress(String id) throws SQLException{
		return csaDao.c_selectAddress(id);
	}
}
