package com.bit.yes.model;

import java.sql.SQLException;
import java.util.Map;

import org.apache.ibatis.session.SqlSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.bit.yes.model.entity.UserVo;

@Repository
public class LoginDAOImpl implements LoginDAO {

	@Autowired
	SqlSession sqlSession;

	@Override
	public int insertOne(UserVo bean) throws SQLException {

		return sqlSession.insert("userInfo.insertOne", bean);

	}

	@Override
	public UserVo loginCheck(Map<String, String> params) throws Exception {

		return sqlSession.selectOne("userInfo.loginCheck", params);
	}

	@Override
	public UserVo checkIDDup(String id) throws Exception{

		return sqlSession.selectOne("userInfo.checkIDDup", id);
	}

	@Override
	public String findID(Map<String, String> params) throws Exception {
		
		return sqlSession.selectOne("userInfo.findID", params);
	}

	@Override
	public String findPW(Map<String, String> params) throws Exception {

		return sqlSession.selectOne("userInfo.findPw", params);
	}

	@Override
	public int updatePW(Map<String, String> params) throws SQLException {
		
		return sqlSession.update("userInfo.updatePW", params);
	}

	@Override
	public int updateInfo(UserVo bean) throws SQLException {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public int deleteOne(String id) throws SQLException {
		// TODO Auto-generated method stub
		return 0;
	}

}
