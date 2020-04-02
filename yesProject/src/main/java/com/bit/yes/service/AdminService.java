package com.bit.yes.service;

import java.sql.SQLException;
import java.util.HashMap;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.bit.yes.model.AdminDao;
import com.bit.yes.model.entity.UserVo;
import com.bit.yes.model.entity.BranchAddressVo;
import com.bit.yes.model.entity.BranchInfoVo;

@Service
public class AdminService {

	@Autowired
	AdminDao adminDao;
	
	public UserVo user_selectPage(String id) throws SQLException {
		return adminDao.user_selcetOne(id);
	}
	
	public BranchInfoVo user_branch_selectOne(String id) throws SQLException {
		return adminDao.user_branch_selectOne(id);
	}
	
	public BranchAddressVo user_branch_selectOne_address(String id) throws SQLException {
		return adminDao.user_branch_selectOne_address(id);
	}
	
	
	// ����¡ ó��
	public List<UserVo> allwriteList(HashMap<String, Object> params) throws SQLException {
		return adminDao.allwriteList(params);
	}
	
	public int allwriteGetCount() throws Exception {
		return adminDao.allwriteGetCount();
	}
	public int allwriteGetCount(HashMap<String, Object> params) throws Exception {
		return adminDao.allwriteGetCount(params);
	}
	// ����¡ ó��
//	int useroffset, int usernoOfRecords
	public List<UserVo> userwriteList(HashMap<String, Object> params) throws SQLException {
		return adminDao.userwriteList(params);
	}
	
	public int userwriteGetCount() throws Exception {
		return adminDao.userwriteGetCount();
	}
	
	public int userwriteGetCount(HashMap<String, Object> params) throws SQLException {
		return adminDao.userwriteGetCount(params);
	}
	// ����¡ ó��
//	int branchoffset, int branchnoOfRecords
	public List<UserVo> branchwriteList(HashMap<String, Object> params) throws SQLException {
		return adminDao.branchwriteList(params);
	}
	
	public int branchwriteGetCount() throws Exception {
		return adminDao.branchwriteGetCount();
	}
	
	public int branchwriteGetCount(HashMap<String, Object> params) throws SQLException {
		return adminDao.branchwriteGetCount(params);
	}
	
	// ����¡ ó��
	public List<BranchInfoVo> management_writeList(int offset, int noOfRecords) throws SQLException {
		return adminDao.management_writeList(offset, noOfRecords);
	}
	
	public int management_writeGetCount() throws Exception {
		return adminDao.management_writeGetCount();
	}
	
	public BranchAddressVo management_address(String id) throws SQLException{
		return adminDao.management_address(id);
	}
	
	// ����¡ ó��
		public List<BranchInfoVo> managementdel_writeList(int offset, int noOfRecords) throws SQLException {
			return adminDao.managementdel_writeList(offset, noOfRecords);
		}
		
		public int managementdel_writeGetCount() throws Exception {
			return adminDao.managementdel_writeGetCount();
		}
	//
	public void manage_update(String id) throws SQLException {
		adminDao.manage_update(id);
	}
	public void manage_registNum(String id) throws SQLException {
		adminDao.manage_registNum(id);
	}
	public void manage_delete(String id) throws SQLException {
		adminDao.manage_delete(id);
	}
	
	public void manage_delregistNum(String id) throws SQLException {
		adminDao.manage_delregistNum(id);
	}
	public void manage_deldelete(String id) throws SQLException {
		adminDao.manage_deldelete(id);
	}
	
	
}
