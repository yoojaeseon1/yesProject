package com.bit.yes.service;

import java.sql.SQLException;
import java.util.HashMap;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.bit.yes.model.AdminDAO;
import com.bit.yes.model.entity.UserVo;
import com.bit.yes.model.entity.BranchAddressVo;
import com.bit.yes.model.entity.BranchInfoVo;

@Service
public class AdminService {

	@Autowired
	AdminDAO adminDAO;
	
	public UserVo user_selectPage(String id) throws SQLException {
		return adminDAO.user_selcetOne(id);
	}
	
	public BranchInfoVo user_branch_selectOne(String id) throws SQLException {
		return adminDAO.user_branch_selectOne(id);
	}
	
	public BranchAddressVo user_branch_selectOne_address(String id) throws SQLException {
		return adminDAO.user_branch_selectOne_address(id);
	}
	
	
	// ����¡ ó��
	public List<UserVo> allwriteList(HashMap<String, Object> params) throws SQLException {
		return adminDAO.allwriteList(params);
	}
	
	public int allwriteGetCount() throws Exception {
		return adminDAO.allwriteGetCount();
	}
	public int allwriteGetCount(HashMap<String, Object> params) throws Exception {
		return adminDAO.allwriteGetCount(params);
	}
	// ����¡ ó��
//	int useroffset, int usernoOfRecords
	public List<UserVo> userwriteList(HashMap<String, Object> params) throws SQLException {
		return adminDAO.userwriteList(params);
	}
	
	public int userwriteGetCount() throws Exception {
		return adminDAO.userwriteGetCount();
	}
	
	public int userwriteGetCount(HashMap<String, Object> params) throws SQLException {
		return adminDAO.userwriteGetCount(params);
	}
	// ����¡ ó��
//	int branchoffset, int branchnoOfRecords
	public List<UserVo> branchwriteList(HashMap<String, Object> params) throws SQLException {
		return adminDAO.branchwriteList(params);
	}
	
	public int branchwriteGetCount() throws Exception {
		return adminDAO.branchwriteGetCount();
	}
	
	public int branchwriteGetCount(HashMap<String, Object> params) throws SQLException {
		return adminDAO.branchwriteGetCount(params);
	}
	
	// ����¡ ó��
	public List<BranchInfoVo> management_writeList(int offset, int noOfRecords) throws SQLException {
		return adminDAO.management_writeList(offset, noOfRecords);
	}
	
	public int management_writeGetCount() throws Exception {
		return adminDAO.management_writeGetCount();
	}
	
	public BranchAddressVo management_address(String id) throws SQLException{
		return adminDAO.management_address(id);
	}
	
	// ����¡ ó��
		public List<BranchInfoVo> managementdel_writeList(int offset, int noOfRecords) throws SQLException {
			return adminDAO.managementdel_writeList(offset, noOfRecords);
		}
		
		public int managementdel_writeGetCount() throws Exception {
			return adminDAO.managementdel_writeGetCount();
		}
	//
	public void manage_update(String id) throws SQLException {
		adminDAO.manage_update(id);
	}
	public void manage_registNum(String id) throws SQLException {
		adminDAO.manage_registNum(id);
	}
	public void manage_delete(String id) throws SQLException {
		adminDAO.manage_delete(id);
	}
	
	public void manage_delregistNum(String id) throws SQLException {
		adminDAO.manage_delregistNum(id);
	}
	public void manage_deldelete(String id) throws SQLException {
		adminDAO.manage_deldelete(id);
	}
	
	
}
