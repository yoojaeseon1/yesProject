package com.bit.yes.service;

import java.sql.SQLException;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.springframework.ui.Model;

import com.bit.yes.model.entity.BranchAddressVo;
import com.bit.yes.model.entity.BranchInfoVo;
import com.bit.yes.model.entity.UserVo;


public interface AdminService {
	
	public String updatebranchAcceptState(String id) throws SQLException;
	
	public String showBranchInfo(String userID, Model model) throws SQLException;
	
	public String listBeforeAcceptedBranch(Model model, HttpServletRequest req) throws SQLException;
	
	public UserVo user_selectPage(String id) throws SQLException;
	public BranchInfoVo user_branch_selectOne(String id) throws SQLException;
	public BranchAddressVo user_branch_selectOne_address(String id) throws SQLException;
	public List<UserVo> allwriteList(Map<String, Object> params) throws SQLException;
	public int allwriteGetCount() throws Exception;
	
	public int allwriteGetCount(Map<String, Object> params) throws Exception;
	public List<UserVo> userwriteList(Map<String, Object> params) throws SQLException;
	public int userwriteGetCount() throws Exception;
	public int userwriteGetCount(Map<String, Object> params) throws SQLException;
	public List<UserVo> branchwriteList(Map<String, Object> params) throws SQLException;
	public int branchwriteGetCount() throws Exception;
	public int branchwriteGetCount(Map<String, Object> params) throws SQLException;
	
	public List<BranchInfoVo> management_writeList(int offset, int noOfRecords) throws SQLException;
	public int management_writeGetCount() throws Exception;
	public BranchAddressVo management_address(String id) throws SQLException;
	public List<BranchInfoVo> managementdel_writeList(int offset, int noOfRecords) throws SQLException;
	public int managementdel_writeGetCount() throws Exception;
	public void manage_update(String id) throws SQLException;
	public void manage_registNum(String id) throws SQLException ;
	public void manage_delete(String id) throws SQLException;
	public void manage_delregistNum(String id) throws SQLException;
	public void manage_deldelete(String id) throws SQLException;
	
	
	
	
	
}
