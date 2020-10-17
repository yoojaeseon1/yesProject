package com.bit.yes.model;

import java.sql.SQLException;
import java.util.List;
import java.util.Map;

import com.bit.yes.model.entity.BranchAddressVo;
import com.bit.yes.model.entity.BranchInfoVo;
import com.bit.yes.model.entity.UserVo;

public interface AdminDAO {
	
	
	

	public List<UserVo> allwriteList(Map<String, Object> params) throws SQLException;
	
	public List<UserVo> userwriteList(Map<String, Object> params) throws SQLException;
	public List<UserVo> branchwriteList(Map<String, Object> params) throws SQLException;
	public List<BranchInfoVo> management_writeList(int offset, int noOfRecords) throws SQLException;
	public List<BranchInfoVo> managementdel_writeList(int offset, int noOfRecords) throws SQLException;
	
	public BranchAddressVo management_address(String id) throws SQLException;
	
	public int allwriteGetCount() throws SQLException;
	public int allwriteGetCount(Map<String, Object> params) throws SQLException;
	public int userwriteGetCount() throws SQLException;
	public int userwriteGetCount(Map<String, Object> params) throws SQLException;
	public int branchwriteGetCount() throws SQLException;
	public int branchwriteGetCount(Map<String, Object> params) throws SQLException;
	public int management_writeGetCount() throws SQLException;
	public int managementdel_writeGetCount() throws SQLException;

	
	public UserVo selcetOneUser(String id) throws SQLException;
	public BranchInfoVo selectOneBranch(String id) throws SQLException;
	public BranchAddressVo selectOneBranchAddress(String id) throws SQLException;
	
	public int updateAcceptState(String id) throws SQLException;
	public int updateRegistNum(String id) throws SQLException;
	public int manage_delete(String id) throws SQLException;
	public int manage_delregistNum(String id) throws SQLException;
	public int manage_deldelete(String id) throws SQLException;
	
}
