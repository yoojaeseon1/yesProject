package com.bit.yes.service;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.ui.Model;

import com.bit.yes.model.AdminDAO;
import com.bit.yes.model.entity.BranchAddressVo;
import com.bit.yes.model.entity.BranchInfoVo;
import com.bit.yes.model.entity.UserVo;
import com.bit.yes.model.paging.Paging;

@Service
public class AdminServiceImpl implements AdminService {

	@Autowired
	AdminDAO adminDAO;
	
	public String updatebranchAcceptState(String id) throws SQLException {
		
		adminDAO.updateRegistNum(id);
		adminDAO.updateAcceptState(id);
		
		return "redirect:/admin/management";
	};
	
	public String showBranchInfo(String userID, Model model) throws SQLException{
		
		model.addAttribute("bean", adminDAO.selcetOneUser(userID));
		model.addAttribute("branch_infoVo", adminDAO.selectOneBranch(userID));
		model.addAttribute("branchaddress", adminDAO.selectOneBranchAddress(userID));
		
		return "./admin/managedetail";
	}
	
	public String listBeforeAcceptedBranch(Model model, HttpServletRequest req) throws SQLException{
		
		int currentPageNo = 1;
		int maxPost = 10;

		if (req.getParameter("pages") != null)
			currentPageNo = Integer.parseInt(req.getParameter("pages"));

		Paging paging = new Paging(currentPageNo, maxPost);

		int offset = (paging.getCurrentPageNo() - 1) * paging.getMaxPost();

		List<BranchInfoVo> page = new ArrayList<BranchInfoVo>();
		page = (List<BranchInfoVo>) adminDAO.management_writeList(offset, paging.getMaxPost());
		paging.setNumberOfRecords(adminDAO.management_writeGetCount());

		paging.makePaging();

		String branchID = null;
		String ids[] = new String[page.size()];
		String ids2[] = new String[page.size()];
		String ids3[] = new String[page.size()];
		String ids4[] = new String[page.size()];
		
		for (int pageI = 0; pageI < page.size(); pageI++) {
			branchID = page.get(pageI).getId();
			BranchAddressVo address = adminDAO.management_address(branchID);
			if (address.getRoadAddress() != null) {
				ids[pageI] = address.getRoadAddress();
				ids2[pageI] = address.getJibunAddress();
				ids3[pageI] = address.getDetailAddress();
				ids4[pageI] = address.getZoneCode();
			}
		}

		model.addAttribute("road", ids);
		model.addAttribute("jibun", ids2);
		model.addAttribute("detailaddress", ids3);
		model.addAttribute("zonecode", ids4);
		model.addAttribute("page", page);
		model.addAttribute("paging", paging);
		
		return "./admin/management";
	}
	
	
	public UserVo user_selectPage(String id) throws SQLException {
		return adminDAO.selcetOneUser(id);
	}
	
	public BranchInfoVo user_branch_selectOne(String id) throws SQLException {
		return adminDAO.selectOneBranch(id);
	}
	
	public BranchAddressVo user_branch_selectOne_address(String id) throws SQLException {
		return adminDAO.selectOneBranchAddress(id);
	}
	
	
	// ����¡ ó��
	public List<UserVo> allwriteList(Map<String, Object> params) throws SQLException {
		return adminDAO.allwriteList(params);
	}
	
	public int allwriteGetCount() throws Exception {
		return adminDAO.allwriteGetCount();
	}
	public int allwriteGetCount(Map<String, Object> params) throws Exception {
		return adminDAO.allwriteGetCount(params);
	}
	// ����¡ ó��
//	int useroffset, int usernoOfRecords
	public List<UserVo> userwriteList(Map<String, Object> params) throws SQLException {
		return adminDAO.userwriteList(params);
	}
	
	public int userwriteGetCount() throws Exception {
		return adminDAO.userwriteGetCount();
	}
	
	public int userwriteGetCount(Map<String, Object> params) throws SQLException {
		return adminDAO.userwriteGetCount(params);
	}
	// ����¡ ó��
//	int branchoffset, int branchnoOfRecords
	public List<UserVo> branchwriteList(Map<String, Object> params) throws SQLException {
		return adminDAO.branchwriteList(params);
	}
	
	public int branchwriteGetCount() throws Exception {
		return adminDAO.branchwriteGetCount();
	}
	
	public int branchwriteGetCount(Map<String, Object> params) throws SQLException {
		return adminDAO.branchwriteGetCount(params);
	}
	
	// ����¡ ó��
	public List<BranchInfoVo> management_writeList(int offset, int noOfRecords) throws SQLException {
		return adminDAO.management_writeList(offset, noOfRecords);
	}
	
	public int management_writeGetCount() throws Exception {
		return adminDAO.management_writeGetCount();
	}
	
	public BranchAddressVo management_address(String id) throws SQLException{
		return adminDAO.management_address(id);
	}
	
	// ����¡ ó��
		public List<BranchInfoVo> managementdel_writeList(int offset, int noOfRecords) throws SQLException {
			return adminDAO.managementdel_writeList(offset, noOfRecords);
		}
		
		public int managementdel_writeGetCount() throws Exception {
			return adminDAO.managementdel_writeGetCount();
		}
	//
	public void manage_update(String id) throws SQLException {
		adminDAO.updateAcceptState(id);
	}
	public void manage_registNum(String id) throws SQLException {
		adminDAO.updateRegistNum(id);
	}
	public void manage_delete(String id) throws SQLException {
		adminDAO.manage_delete(id);
	}
	
	public void manage_delregistNum(String id) throws SQLException {
		adminDAO.manage_delregistNum(id);
	}
	public void manage_deldelete(String id) throws SQLException {
		adminDAO.manage_deldelete(id);
	}
	
	
}
