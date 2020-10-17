package com.bit.yes.model;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.ibatis.session.SqlSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.bit.yes.model.entity.BranchAddressVo;
import com.bit.yes.model.entity.BranchInfoVo;
import com.bit.yes.model.entity.UserVo;


@Repository
public class AdminDAOImpl implements AdminDAO {

	@Autowired
	SqlSession sqlSession;
	int alloffset;
	int allnoOfRecords;
	int useroffset;
	int usernoOfRecords;
	int branchoffset;
	int branchnoOfRecords;
	int offset;
	int noOfRecords;


	@Override
	public int updateAcceptState(String id) throws SQLException {
		return sqlSession.update("admin.updateAcceptState", id);
	}
	
	@Override
	public int updateRegistNum(String id) throws SQLException {
		return sqlSession.update("admin.updateRegistNum", id);
	}
	
	
	
	@Override
	public List<UserVo> allwriteList(Map<String, Object> params) throws SQLException {
		
		List<UserVo> allwriteList = new ArrayList<UserVo>();
		
		allwriteList = sqlSession.selectList("all_writeList", params);
		this.allnoOfRecords = sqlSession.selectOne("all_writeGetCount", params);
		
		return allwriteList;
	}

	@Override
	public int allwriteGetCount() throws SQLException {
		return sqlSession.selectOne("all_writeGetCount");
	}
	
	@Override
	public int allwriteGetCount(Map<String, Object> params) throws SQLException {
		return sqlSession.selectOne("all_writeGetCount", params);
	}

	@Override
	public UserVo selcetOneUser(String id) throws SQLException {
		return sqlSession.selectOne("admin.user_selectOne", id);
	}

	@Override
	public BranchInfoVo selectOneBranch(String id) throws SQLException {
		return sqlSession.selectOne("admin.user_branch_selectOne",id);
	}

	@Override
	public BranchAddressVo selectOneBranchAddress(String id) throws SQLException {
		return sqlSession.selectOne("admin.user_branch_selectOne_address",id);
	}

	@Override
	public List<UserVo> userwriteList(Map<String, Object> params) throws SQLException {
		List<UserVo> userwriteList = new ArrayList<UserVo>();
		
		
		userwriteList = sqlSession.selectList("user_writeList", params);
		this.usernoOfRecords = sqlSession.selectOne("user_writeGetCount");
		
		return userwriteList;
	}

	@Override
	public List<UserVo> branchwriteList(Map<String, Object> params) throws SQLException {
		List<UserVo> branchwriteList = new ArrayList<UserVo>();
		
		
		branchwriteList = sqlSession.selectList("branch_writeList", params);
		this.branchnoOfRecords = sqlSession.selectOne("branch_writeGetCount");
		
		return branchwriteList;
	}

	@Override
	public int userwriteGetCount() throws SQLException {
		return sqlSession.selectOne("user_writeGetCount");
	}
	
	@Override
	public int userwriteGetCount(Map<String, Object> params) throws SQLException {
		return sqlSession.selectOne("user_writeGetCount", params);
	}

	@Override
	public int branchwriteGetCount() throws SQLException {
		return sqlSession.selectOne("branch_writeGetCount");
	}
	
	@Override
	public int branchwriteGetCount(Map<String, Object> params) throws SQLException {
		return sqlSession.selectOne("branch_writeGetCount", params);
	}

	@Override
	public List<BranchInfoVo> management_writeList(int offset, int noOfRecords) throws SQLException {
		List<BranchInfoVo> managementwriteList = new ArrayList<BranchInfoVo>();
		
		Map<String, Object> params = new HashMap<String, Object>();
		
		params.put("offset", offset);
		params.put("noOfRecords", noOfRecords);
		managementwriteList = sqlSession.selectList("management_writeList", params);
		this.noOfRecords = sqlSession.selectOne("management_writeGetCount");

		return managementwriteList;
	}

	@Override
	public int management_writeGetCount() throws SQLException {
		return sqlSession.selectOne("admin.management_writeGetCount");
	}

	@Override
	public BranchAddressVo management_address(String id) throws SQLException {
		return sqlSession.selectOne("admin.management_address", id);
	}



	@Override
	public int manage_delete(String id) throws SQLException {
		return sqlSession.delete("admin.manage_delete", id);
	}



	@Override
	public int manage_delregistNum(String id) throws SQLException {
		return sqlSession.update("admin.manage_delregistNum", id);
	}

	@Override
	public int manage_deldelete(String id) throws SQLException {
		return sqlSession.delete("admin.manage_deldelete", id);
	}

	@Override
	public List<BranchInfoVo> managementdel_writeList(int offset, int noOfRecords) throws SQLException {
		List<BranchInfoVo> managementdel_writeList = new ArrayList<BranchInfoVo>();
		
		Map<String, Object> params = new HashMap<String, Object>();
		
		params.put("offset", offset);
		params.put("noOfRecords", noOfRecords);
		
		managementdel_writeList = sqlSession.selectList("admin.managementdel_writeList", params);
		this.noOfRecords = sqlSession.selectOne("admin.managementdel_writeGetCount");
		
		return managementdel_writeList;
	}

	@Override
	public int managementdel_writeGetCount() throws SQLException {
		return sqlSession.selectOne("admin.managementdel_writeGetCount");
	}







}
