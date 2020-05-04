package com.bit.yes.model;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import org.apache.ibatis.session.SqlSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.bit.yes.model.entity.CCsVo;
import com.bit.yes.model.entity.ImageVo;
import com.bit.yes.model.entity.UserVo;
import com.bit.yes.model.entity.BranchAddressVo;
import com.bit.yes.model.entity.BranchInfoVo;

@Repository
public class CCsDAOImpl implements CCsDAO {

	@Autowired
	SqlSession sqlSession;
	int noOfRecords;
	
	@Override
	public CCsVo selcetOne(int index) throws SQLException {
		// TODO Auto-generated method stub
		return sqlSession.selectOne("yes.c_selectOne", index);
	}

	@Override
	public int insertOne(CCsVo bean) throws SQLException {
		sqlSession.insert("yes.c_insertOne",bean);
		return 0;
		
	}

//	@Override
//	public List<C_CsVo> writeList(int offset, int noOfRecords,String clientID) throws SQLException {
//		List<C_CsVo> writeList = new ArrayList<C_CsVo>();
//		
//		HashMap<String, Object> params = new HashMap<String, Object>();
//		
//		params.put("offset", offset);
//		params.put("noOfRecords", noOfRecords);
//		params.put("clientID", clientID);
//		
//		writeList = sqlSession.selectList("c_writeList", params);
//		this.noOfRecords = sqlSession.selectOne("c_writeGetCount");
//		
//		return writeList;
//	}
	@Override
	public List<CCsVo> writeList(HashMap<String, Object> params) throws SQLException {
		List<CCsVo> writeList = new ArrayList<CCsVo>();

		
		writeList = sqlSession.selectList("c_writeList", params);
		this.noOfRecords = sqlSession.selectOne("c_writeGetCount");
		
		return writeList;
	}

	@Override
	public int writeGetCount(HashMap<String, Object> params) throws SQLException {
		// TODO Auto-generated method stub
		return sqlSession.selectOne("c_writeGetCount",params);
		
	}
	
	@Override
	public int writeGetCount(String clientID) throws SQLException {
		// TODO Auto-generated method stub
		return sqlSession.selectOne("c_writeGetCount",clientID);
		
	}

	@Override
	public List<BranchInfoVo> reserveList(String id) throws SQLException {
		
		return sqlSession.selectList("yes.reserveselectAll", id);
	}

	@Override
	public List<BranchInfoVo> reserveOne(String id) throws SQLException {
		// TODO Auto-generated method stub
		return sqlSession.selectList("yes.reserveOne", id);
	}

	@Override
	public UserVo c_selectnickname(String id) throws SQLException {
		// TODO Auto-generated method stub
		return sqlSession.selectOne("yes.c_selectnickname", id);
	}

	@Override
	public List<ImageVo> c_counselSubImage(int index) throws SQLException {
		// TODO Auto-generated method stub
		return sqlSession.selectList("yes.c_counselSubImage", index);
	}

	@Override
	public int c_counselImgUpload(ImageVo bean) throws SQLException {
		// TODO Auto-generated method stub
		return sqlSession.insert("yes.c_counselImgUpload", bean);
	}

	@Override
	public BranchAddressVo c_selectAddress(String id) throws SQLException {
		// TODO Auto-generated method stub
		return sqlSession.selectOne("yes.c_selectAddress", id);
	}

	@Override
	public UserVo user_selcetOne(String id) throws SQLException {
		// TODO Auto-generated method stub
		return sqlSession.selectOne("yes.user_selectOne", id);
	}

}
