package com.bit.yes.service;

import java.sql.SQLException;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.bit.yes.model.CounselAllDAO;
import com.bit.yes.model.entity.CCsVo;
import com.bit.yes.model.entity.ImageVo;
import com.bit.yes.model.entity.SCsVo;
import com.bit.yes.model.entity.UserVo;
import com.bit.yes.model.entity.BranchAddressVo;
import com.bit.yes.model.entity.BranchInfoVo;

@Service
public class CounselAllService {

	@Autowired
	CounselAllDAO csaDAO;
	
	public CCsVo cselectPage(int idx) throws SQLException {
		return csaDAO.cselcetOne(idx);
	}
	
	public List<BranchInfoVo> creserveList(String id) throws SQLException{
		return csaDAO.creserveList(id);
	}
	
	public List<BranchInfoVo> creserveOne(String id) throws SQLException{
		return csaDAO.creserveOne(id);
	}
	
	// ����¡ ó��
	public List<CCsVo> cwriteList(int coffset, int cnoOfRecords) throws SQLException {
		return csaDAO.cwriteList(coffset, cnoOfRecords);
	}
	
	public int cwriteGetCount() throws Exception {
		return csaDAO.cwriteGetCount();
	}

	public UserVo cselectNick(String id) throws SQLException{
		return csaDAO.c_selectnickname(id);
	}
	
	public void cupdatePage(CCsVo bean) throws SQLException {
		csaDAO.cupdateOne(bean);
	}
	
	////////////////////////////////////////////
	
	public SCsVo sselectPage(int idx) throws SQLException {
		return csaDAO.sselcetOne(idx);
	}
	
	// ����¡ ó��
	public List<SCsVo> swriteList(int soffset, int snoOfRecords) throws SQLException {
		return csaDAO.swriteList(soffset, snoOfRecords);
	}
	
	public int swriteGetCount() throws Exception {
		return csaDAO.swriteGetCount();
	}
	
	public UserVo sselectNick(String id) throws SQLException{
		return csaDAO.s_selectnickname(id);
	}
	
	public void supdatePage(SCsVo bean) throws SQLException {
		csaDAO.supdateOne(bean);
		
	}
	
	// ��¥ �˻� �� ����¡ ó��
	
	public List<CCsVo> sacwriteList(int coffset, int cnoOfRecords,String sDate, String eDate) throws SQLException {
		return csaDAO.sacwriteList(coffset, cnoOfRecords, sDate, eDate);
	}
	
	public int sacwriteGetCount(String sDate, String eDate) throws Exception {
		return csaDAO.sacwriteGetCount(sDate, eDate);
	}
	// ��¥ �˻� �� ����¡ ó��
	
	public List<SCsVo> saswriteList(int soffset, int snoOfRecords,String sDate, String eDate) throws SQLException {
		return csaDAO.saswriteList(soffset, snoOfRecords, sDate, eDate);
	}
	
	public int saswriteGetCount(String sDate, String eDate) throws Exception {
		return csaDAO.saswriteGetCount(sDate, eDate);
	}
	
	// ���ε� ó��
	
	public List<ImageVo> s_counselSubImage(int index) throws SQLException {
		return csaDAO.s_counselSubImage(index);
	}

	public List<ImageVo> c_counselSubImage(int index) throws SQLException {
		return csaDAO.c_counselSubImage(index);
	}
	
	public BranchAddressVo c_selectAddress(String id) throws SQLException{
		return csaDAO.c_selectAddress(id);
	}
}
