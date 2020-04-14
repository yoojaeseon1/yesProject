package com.bit.yes.service;

import java.sql.SQLException;
import java.util.HashMap;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.bit.yes.model.S_CsDAO;
import com.bit.yes.model.entity.ImageVo;
import com.bit.yes.model.entity.S_CsVo;
import com.bit.yes.model.entity.UserVo;

@Service
public class S_CsService {

	@Autowired
	S_CsDAO scsDAO;
	
	public S_CsVo selectPage(int idx) throws SQLException {
		return scsDAO.selcetOne(idx);
	}
	
	public void addPage(S_CsVo bean) throws SQLException {
		scsDAO.insertOne(bean);
	}
	
	// 페이징 처리
//	public List<S_CsVo> writeList(int offset, int noOfRecords,String writer) throws SQLException {
//		return scsDAO.writeList(offset, noOfRecords, writer);
//	}
	
//	public int writeGetCount(String writer) throws Exception {
//		return scsDAO.writeGetCount(writer);
//	}
	
	public UserVo selectNick(String id) throws SQLException{
		return scsDAO.s_selectnickname(id);
	}
	
	public List<S_CsVo> writeList(HashMap<String, Object> params) throws SQLException {
		return scsDAO.writeList(params);
//		return scsDAO.writeList(offset, noOfRecords, writer);
	}
	
	public int writeGetCount(HashMap<String, Object> params) throws Exception {
		return scsDAO.writeGetCount(params);
	}

	// 업로드 처리
	public void s_counselImgUpload(ImageVo bean) throws SQLException {
		scsDAO.s_counselImgUpload(bean);
	}

	public List<ImageVo> s_counselSubImage(int index) throws SQLException {
		return scsDAO.s_counselSubImage(index);
	}
}
