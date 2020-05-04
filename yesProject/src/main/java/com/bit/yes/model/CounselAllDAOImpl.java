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
import com.bit.yes.model.entity.SCsVo;
import com.bit.yes.model.entity.UserVo;
import com.bit.yes.model.entity.BranchAddressVo;
import com.bit.yes.model.entity.BranchInfoVo;

@Repository
public class CounselAllDAOImpl implements CounselAllDAO {

	@Autowired
	SqlSession sqlSession;
	int cnoOfRecords;
	int snoOfRecords;
	
	@Override
	public CCsVo cselcetOne(int index) throws SQLException {
		// TODO Auto-generated method stub
		return sqlSession.selectOne("yes.c_selectOne", index);
	}

	@Override
	public List<CCsVo> cwriteList(int coffset, int cnoOfRecords) throws SQLException {
		List<CCsVo> cwriteList = new ArrayList<CCsVo>();
		
		HashMap<String, Object> params = new HashMap<String, Object>();
		
		params.put("coffset", coffset);
		params.put("cnoOfRecords", cnoOfRecords);
		
		cwriteList = sqlSession.selectList("ac_writeList", params);
		this.cnoOfRecords = sqlSession.selectOne("ac_writeGetCount");
		
		return cwriteList;
	}

	@Override
	public int cwriteGetCount() throws SQLException {
		// TODO Auto-generated method stub
		return sqlSession.selectOne("ac_writeGetCount");
	}

	@Override
	public List<BranchInfoVo> creserveList(String id) throws SQLException {
		// TODO Auto-generated method stub
		return sqlSession.selectList("yes.areserveselectAll", id);
	}

	@Override
	public List<BranchInfoVo> creserveOne(String id) throws SQLException {
		// TODO Auto-generated method stub
		return sqlSession.selectList("yes.areserveOne", id);
	}


	@Override
	public UserVo c_selectnickname(String id) throws SQLException {
		// TODO Auto-generated method stub
		return sqlSession.selectOne("yes.ac_selectnickname", id);
	}

	@Override
	public SCsVo sselcetOne(int index) throws SQLException {
		// TODO Auto-generated method stub
		return sqlSession.selectOne("yes.as_selectOne", index);
	}

	@Override
	public List<SCsVo> swriteList(int soffset, int snoOfRecords) throws SQLException {
		List<SCsVo> swriteList = new ArrayList<SCsVo>();
		
		HashMap<String, Object> params = new HashMap<String, Object>();
		
		params.put("soffset", soffset);
		params.put("snoOfRecords", snoOfRecords);
		
		swriteList = sqlSession.selectList("as_writeList", params);
		this.snoOfRecords = sqlSession.selectOne("as_writeGetCount");
		
		return swriteList;
	}

	@Override
	public int swriteGetCount() throws SQLException {
		// TODO Auto-generated method stub
		return sqlSession.selectOne("as_writeGetCount");
	}

	@Override
	public UserVo s_selectnickname(String id) throws SQLException {
		// TODO Auto-generated method stub
		return sqlSession.selectOne("yes.as_selectnickname", id);
	}

	@Override
	public int cupdateOne(CCsVo bean) throws SQLException {
		sqlSession.update("yes.acupdateOne", bean);
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public int supdateOne(SCsVo bean) throws SQLException {
		sqlSession.update("yes.asupdateOne", bean);
		return 0;
	}

	@Override
	public List<CCsVo> sacwriteList(int coffset, int cnoOfRecords, String sDate, String eDate) throws SQLException {
		List<CCsVo> sacwriteList = new ArrayList<CCsVo>();
		HashMap<String, Object> params = new HashMap<String, Object>();
		params.put("coffset", coffset);
		params.put("cnoOfRecords", cnoOfRecords);
		params.put("sDate", sDate);
		params.put("eDate", eDate);
		sacwriteList = sqlSession.selectList("yes.sac_writeList", params);
		this.cnoOfRecords = sqlSession.selectOne("yes.sac_writeGetCount");
		return sacwriteList;
	}

	@Override
	public int sacwriteGetCount(String sDate, String eDate) throws SQLException {
		// TODO Auto-generated method stub
		HashMap<String, Object> params = new HashMap<String, Object>();
		params.put("sDate", sDate);
		params.put("eDate", eDate);
		return sqlSession.selectOne("yes.sac_writeGetCount",params);
	}

	@Override
	public List<SCsVo> saswriteList(int soffset, int snoOfRecords, String sDate, String eDate) throws SQLException {
		List<SCsVo> saswriteList = new ArrayList<SCsVo>();
		HashMap<String, Object> params = new HashMap<String, Object>();
		params.put("soffset", soffset);
		params.put("snoOfRecords", cnoOfRecords);
		params.put("sDate", sDate);
		params.put("eDate", eDate);
		saswriteList = sqlSession.selectList("yes.sas_writeList", params);
		this.snoOfRecords = sqlSession.selectOne("yes.sas_writeGetCount");
		return saswriteList;
	}

	@Override
	public int saswriteGetCount(String sDate, String eDate) throws SQLException {
		HashMap<String, Object> params = new HashMap<String, Object>();
		params.put("sDate", sDate);
		params.put("eDate", eDate);
		return sqlSession.selectOne("yes.sas_writeGetCount",params);
	}

	@Override
	public List<ImageVo> c_counselSubImage(int index) throws SQLException {
		// TODO Auto-generated method stub
		return sqlSession.selectList("yes.c_counselSubImage", index);
	}

	@Override
	public List<ImageVo> s_counselSubImage(int index) throws SQLException {
		// TODO Auto-generated method stub
		return sqlSession.selectList("yes.s_counselSubImage", index);
	}

	@Override
	public BranchAddressVo c_selectAddress(String id) throws SQLException {
		// TODO Auto-generated method stub
		return sqlSession.selectOne("yes.c_selectAddress", id);
	}

}
