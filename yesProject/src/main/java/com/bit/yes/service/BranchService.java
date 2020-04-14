package com.bit.yes.service;

import com.bit.yes.model.BranchDAO;
import com.bit.yes.model.entity.BranchVo;
import com.bit.yes.model.entity.ReviewVo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartHttpServletRequest;

import java.sql.SQLException;
import java.util.List;
import java.util.Map;

@Service
public class BranchService {

	@Autowired
	BranchDAO branchDAO;

	public void insertBranchInfo(Map<String,String> map) {
		branchDAO.insertBranchInfo(map);
	}

	public void insertBranchAddress(Map<String,String> map) {
		branchDAO.insertBranchAddress(map);
	}

	public void insertBranchMenu(Map<String,Object> map) {
		branchDAO.insertBranchMenu(map);
	}

	public void insertImageNames(Map<String,String> imageMap) {
		branchDAO.insertImageNames(imageMap);
	}

	public List<BranchVo> selectAll() throws SQLException {
		return branchDAO.selectAll();
	}

	public List<BranchVo> menuLoad(String id) {
		return branchDAO.menuLoad(id);
	}

	public List<BranchVo> allMenuLoad(String id) {
		return branchDAO.allMenuLoad(id);
	}

	public List<BranchVo> searchResult(Map<String,Object> searchMap) {
		return branchDAO.searchResult(searchMap);
	}

	public String imageUpload(MultipartHttpServletRequest mtfRequest, String id) {
		return branchDAO.imageUpload(mtfRequest, id);
	}

	public List<BranchVo> reserveInfoPreview(String id) {
		return branchDAO.reserveInfoPreview(id);
	}

	public void updateLatLng(Map<String,Object> updateLatLng) {
		branchDAO.updateLatLng(updateLatLng);
	}

	public int waitingList(String id) {
		return branchDAO.waitingList(id);
	}

	public void ticketingStart(String id, String clientId) {
		branchDAO.ticketingStart(id, clientId);
	}

	public int ticketingCheck(String id, String clientId){
		return branchDAO.ticketingCheck(id, clientId);
	}

	public List<ReviewVo> branchReview(String branchId) {
		return branchDAO.branchReview(branchId);
	}

	public List<BranchVo> myAllMenuLoad(String branchId) {
		return branchDAO.myAllMenuLoad(branchId);
	}
}
