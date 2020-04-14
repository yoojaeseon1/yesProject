package com.bit.yes.service;

import java.sql.SQLException;
import java.util.HashMap;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.ui.Model;

import com.bit.yes.model.NoticeDAO;
import com.bit.yes.model.entity.ImageVo;
import com.bit.yes.model.entity.NoticeVo;
import com.bit.yes.model.entity.UserVo;

@Service
public class NoticeService {
	
	@Autowired
	NoticeDAO noticeDAO;
	
	public void listPage(Model model) throws SQLException {
		model.addAttribute("alist", noticeDAO.selectAll());
	}
	
	public NoticeVo selectPage(int idx) throws SQLException {
		return noticeDAO.selcetOne(idx);
	}
	
	public void addPage(NoticeVo bean) throws SQLException {
		noticeDAO.insertOne(bean);
	}
	
	public void deletePage(int index) throws SQLException {
		noticeDAO.deleteOne(index);
		
	}
	
	public void updatedeletePage(int index) throws SQLException {
		noticeDAO.updatedelete(index);
	}
	
	public void updateimgPage(ImageVo beans) throws SQLException {
		noticeDAO.updateimg(beans);
	}
	
	public void updatePage(NoticeVo bean) throws SQLException {
		noticeDAO.updateOne(bean);
	}
	
	// ����¡ ó��
	public List<NoticeVo> writeList(HashMap<String, Object> params) throws SQLException {
		return noticeDAO.writeList(params);
	}
	
//	public List<NoticeVo> writeList(int offset, int noOfRecords) throws SQLException {
//		return noticeDAO.writeList(offset, noOfRecords);
//	}
	
	public int writeGetCount() throws Exception {
		return noticeDAO.writeGetCount();
	}
	
	public int writeGetCount(HashMap<String, Object> params) throws Exception {
		return noticeDAO.writeGetCount(params);
	}
	
	// ���ε� ó��
	public void noticeImgUpload(ImageVo bean) throws SQLException {
		noticeDAO.noticeImgUpload(bean);
	}

	public List<ImageVo> noticeSubImage(int index) throws SQLException {
		return noticeDAO.noticeSubImage(index);
	}
	
	public UserVo user_selectOne(String id) throws SQLException {
		return noticeDAO.user_selcetOne(id);
	}
	
}
