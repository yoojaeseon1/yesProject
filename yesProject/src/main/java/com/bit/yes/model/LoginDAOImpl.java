package com.bit.yes.model;

import java.util.Map;

import org.apache.ibatis.session.SqlSession;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.bit.yes.model.entity.UserVo;

@Repository
public class LoginDAOImpl implements LoginDAO {

	Logger logger = LoggerFactory.getLogger(LoginDAOImpl.class);
	
	@Autowired
	SqlSession sqlSession;

	@Override
	public int insertOne(UserVo user) throws Exception {

		return sqlSession.insert("userInfo.insertOne", user);

	}

	@Override
	public UserVo selectUserInfo(UserVo user) throws Exception {
		
		return sqlSession.selectOne("userInfo.selectUserInfo", user);
	
	}
	

	@Override
	public String selectID(String id) throws Exception{

		return sqlSession.selectOne("userInfo.selectID", id);
	}
	
	@Override
	public String selectEmail(String email) throws Exception {
		
		return sqlSession.selectOne("userInfo.selectEmail", email);
	}
	
//	@Override
//	public UserVo selectUserByID(String id) throws Exception{
//		
//		return sqlSession.selectOne("userInfo.selectID", id);
//	}
//	
//	@Override
//	public UserVo selectUserByEmail(String email) throws Exception {
//		
//		return sqlSession.selectOne("userInfo.selectEmail", email);
//	}

	@Override
	public String findID(UserVo user) throws Exception {
		logger.info("findID - user : " + user);
		String id = sqlSession.selectOne("userInfo.findID", user);
		logger.info("findID - id : " + id); 
 		return sqlSession.selectOne("userInfo.findID", user);
	}
//	@Override
//	public String findID(Map<String, String> params) throws Exception {
//		
//		return sqlSession.selectOne("userInfo.findID", params);
//	}

	@Override
	public String selectPassword(UserVo currentUser) throws Exception {

		logger.info("selectPassword - currentUser : " + currentUser);
		
		return sqlSession.selectOne("userInfo.selectPassword", currentUser);
	}
	
//	@Override
//	public String selectPassword(Map<String, String> params) throws Exception {
//		
//		return sqlSession.selectOne("userInfo.selectPassword", params);
//	}

	@Override
	public int updatePW(UserVo user) throws Exception {
		
		return sqlSession.update("userInfo.updatePW", user);
	}

	@Override
	public int updateInfo(UserVo user) throws Exception {
		return 0;
	}

	@Override
	public int deleteOne(String id) throws Exception {
		return 0;
	}






}
