package com.bit.yes.controller;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.bit.yes.model.entity.BranchAddressVo;
import com.bit.yes.model.entity.BranchInfoVo;
import com.bit.yes.model.entity.UserVo;
import com.bit.yes.model.paging.Paging;
import com.bit.yes.service.AdminServiceImpl;

@Controller
public class AdminController {

	@Autowired
	AdminServiceImpl adminService;


	
	@RequestMapping(value = "/admin/managedetail/acceptBranch/{id}", method = RequestMethod.POST)
	public String updateBranchAcceptState(String id) throws SQLException {

		return adminService.updatebranchAcceptState(id);
	}
	
	@RequestMapping(value = "/admin/managedetail", method = RequestMethod.POST)
	public String showBranchInfo(String userID, Model model) throws Exception {


		return adminService.showBranchInfo(userID, model);
	}
	
	
	
	
	
	
	
	
	
	
	@RequestMapping("/admin/management")
	public String listBeforeAcceptedBranch(Model model, HttpServletRequest request) throws Exception {
		

		return adminService.listBeforeAcceptedBranch(model, request);
	}
	
	@RequestMapping("/admin/")
	public String adminpage() {
		return "./admin/admin";
	}
	

	@RequestMapping(value = "/admin/userAllList")
	public String selectAlluserpage(Model model, HttpServletRequest req) throws Exception {

		Map<String, Object> params = new HashMap<String, Object>();
		int currentPageNo = 1;
		int maxPost = 10;
		

		if (req.getParameter("allPages") != null)
			currentPageNo = Integer.parseInt(req.getParameter("allPages"));

		Paging allpaging = new Paging(currentPageNo, maxPost);

		int alloffset = (allpaging.getCurrentPageNo() - 1) * allpaging.getMaxPost();

		params.put("alloffset", alloffset);
		params.put("allnoOfRecords", allpaging.getMaxPost());

		List<UserVo> allpage = new ArrayList<UserVo>();
		allpage = (ArrayList<UserVo>) adminService.allwriteList(params);
		allpaging.setNumberOfRecords(adminService.allwriteGetCount(params));

		allpaging.makePaging();

		model.addAttribute("page", allpage);
		model.addAttribute("paging", allpaging);

		return "./admin/userAllList";
	}

	@RequestMapping(value = "/admin/user_all_Search")
	public String userAllSearchpage(Model model, HttpServletRequest req) throws Exception {

		Map<String, Object> params = new HashMap<String, Object>();
		HttpSession session = req.getSession();
		int currentPageNo = 1;
		int maxPost = 10;

		String category = req.getParameter("category");
		String keyword = req.getParameter("keyword");


		if (req.getParameter("allPages") != null)
			currentPageNo = Integer.parseInt(req.getParameter("allPages"));

		if (category == null && keyword == null) {
			category = (String) session.getAttribute("category");
			keyword = (String) session.getAttribute("keyword");
		} else {
			session.setAttribute("category", category);
			session.setAttribute("keyword", keyword);
		}

		Paging allpaging = new Paging(currentPageNo, maxPost);

		int alloffset = (allpaging.getCurrentPageNo() - 1) * allpaging.getMaxPost();

		params.put("alloffset", alloffset);
		params.put("allnoOfRecords", allpaging.getMaxPost());
		params.put("category", category);
		params.put("keyword", keyword);

		List<UserVo> allpage = new ArrayList<UserVo>();
		allpage = (ArrayList<UserVo>) adminService.allwriteList(params);
		allpaging.setNumberOfRecords(adminService.allwriteGetCount(params));

		allpaging.makePaging();

		model.addAttribute("page", allpage);
		model.addAttribute("paging", allpaging);

		return "./admin/userAllList";
	}

	@RequestMapping("/admin/userList")
	public String selectAlluser(Model model, HttpServletRequest req) throws Exception {

		Map<String, Object> params = new HashMap<String, Object>();
		int currentPageNo = 1;
		int maxPost = 10;

		if (req.getParameter("userPages") != null)
			currentPageNo = Integer.parseInt(req.getParameter("userPages"));

		Paging userpaging = new Paging(currentPageNo, maxPost);

		int useroffset = (userpaging.getCurrentPageNo() - 1) * userpaging.getMaxPost();

		List<UserVo> userpage = new ArrayList<UserVo>();

		params.put("useroffset", useroffset);
		params.put("usernoOfRecords", userpaging.getMaxPost());
		userpage = (ArrayList<UserVo>) adminService.userwriteList(params);
		userpaging.setNumberOfRecords(adminService.userwriteGetCount(params));

		userpaging.makePaging();

		model.addAttribute("page", userpage);
		model.addAttribute("paging", userpaging);

		return "./admin/userList";
	}

	@RequestMapping(value = "/admin/user_search")
	public String selectUserSearch(Model model, HttpServletRequest req) throws Exception {

		HashMap<String, Object> params = new HashMap<String, Object>();
		HttpSession session = req.getSession();
		int currentPageNo = 1;
		int maxPost = 10;

		String category = req.getParameter("category");
		String keyword = req.getParameter("keyword");


		if (req.getParameter("userPages") != null)
			currentPageNo = Integer.parseInt(req.getParameter("userPages"));

		if (category == null && keyword == null) {
			category = (String) session.getAttribute("category");
			keyword = (String) session.getAttribute("keyword");
		} else {
			session.setAttribute("category", category);
			session.setAttribute("keyword", keyword);
		}

		Paging allpaging = new Paging(currentPageNo, maxPost);

		int alloffset = (allpaging.getCurrentPageNo() - 1) * allpaging.getMaxPost();

		params.put("useroffset", alloffset);
		params.put("usernoOfRecords", allpaging.getMaxPost());
		params.put("category", category);
		params.put("keyword", keyword);

		List<UserVo> allpage = new ArrayList<UserVo>();
		allpage = (ArrayList<UserVo>) adminService.userwriteList(params);
		allpaging.setNumberOfRecords(adminService.userwriteGetCount(params));

		allpaging.makePaging();

		model.addAttribute("page", allpage);
		model.addAttribute("paging", allpaging);

		return "./admin/userList";
	}

	@RequestMapping("/admin/branchList")
	public String branchForm(Model model, HttpServletRequest req) throws Exception {

		HashMap<String, Object> params = new HashMap<String, Object>();
		int currentPageNo = 1;
		int maxPost = 10;
		System.out.println(req.getParameter("branchpages"));
		if (req.getParameter("branchPages") != null)
			currentPageNo = Integer.parseInt(req.getParameter("branchPages"));

		Paging branchpaging = new Paging(currentPageNo, maxPost);

		int branchoffset = (branchpaging.getCurrentPageNo() - 1) * branchpaging.getMaxPost();

		params.put("branchoffset", branchoffset);
		params.put("branchnoOfRecords", branchpaging.getMaxPost());
		List<UserVo> branchpage = new ArrayList<UserVo>();
		branchpage = (ArrayList<UserVo>) adminService.branchwriteList(params);
		branchpaging.setNumberOfRecords(adminService.branchwriteGetCount(params));

		branchpaging.makePaging();

		model.addAttribute("page", branchpage);
		model.addAttribute("paging", branchpaging);

		return "./admin/branchList";
	}

	@RequestMapping(value = "/admin/branch_search")
	public String branchSearchForm(Model model, HttpServletRequest req) throws Exception {

		HashMap<String, Object> params = new HashMap<String, Object>();
		HttpSession session = req.getSession();
		int currentPageNo = 1;
		int maxPost = 10;

		String category = req.getParameter("category");
		String keyword = req.getParameter("keyword");

		System.out.println("category : " + category);
		System.out.println("keyword : " + keyword);

		if (req.getParameter("branchPages") != null)
			currentPageNo = Integer.parseInt(req.getParameter("branchPages"));

		if (category == null && keyword == null) {
			category = (String) session.getAttribute("category");
			keyword = (String) session.getAttribute("keyword");
		} else {
			/*
			 * req.setAttribute("category", category); req.setAttribute("keyword", keyword);
			 */
			session.setAttribute("category", category);
			session.setAttribute("keyword", keyword);
		}

		Paging allpaging = new Paging(currentPageNo, maxPost);

		int alloffset = (allpaging.getCurrentPageNo() - 1) * allpaging.getMaxPost();

		params.put("branchoffset", alloffset);
		params.put("branchnoOfRecords", allpaging.getMaxPost());
		params.put("category", category);
		params.put("keyword", keyword);

		List<UserVo> allpage = new ArrayList<UserVo>();
		allpage = (ArrayList<UserVo>) adminService.branchwriteList(params);
		allpaging.setNumberOfRecords(adminService.branchwriteGetCount(params));

		allpaging.makePaging();

		model.addAttribute("page", allpage);
		model.addAttribute("paging", allpaging);

		return "./admin/branchList";
	}

	@RequestMapping(value = "/admin/AllListDetail", method = RequestMethod.POST)
	public String listAlluserDetailpage(String choice, String userID, Model model) throws Exception {

		String registNum = adminService.user_selectPage(userID).getRegistNum();
		if (registNum == null || registNum == "") {

			model.addAttribute("choice", choice);
			model.addAttribute("bean", adminService.user_selectPage(userID));
		} else if (registNum != null || registNum != "") {

			model.addAttribute("choice", choice);
			model.addAttribute("bean", adminService.user_selectPage(userID));
			model.addAttribute("branch_infoVo", adminService.user_branch_selectOne(userID));
			model.addAttribute("branchaddress", adminService.user_branch_selectOne_address(userID));
		}

		return "./admin/AllListDetail";
	}

	@RequestMapping("/admin/AllListDetail")
	public String AllListpage() {

		return "./admin/AllListDetail";
	}





	@RequestMapping(value = "/admin/managedetail/{id}", method = RequestMethod.DELETE)
	public String deleteBranch(@PathVariable String id) throws SQLException {
		adminService.manage_delete(id);
		return "redirect:/admin/management";
	}



	@RequestMapping("/admin/managementdel")
	public String managedelpage(Model model, HttpServletRequest req) throws Exception {
		int currentPageNo = 1;
		int maxPost = 10;

		if (req.getParameter("pages") != null)
			currentPageNo = Integer.parseInt(req.getParameter("pages"));

		Paging paging = new Paging(currentPageNo, maxPost);

		int offset = (paging.getCurrentPageNo() - 1) * paging.getMaxPost();

		List<BranchInfoVo> page = new ArrayList<BranchInfoVo>();
		page = (ArrayList<BranchInfoVo>) adminService.managementdel_writeList(offset, paging.getMaxPost());
		paging.setNumberOfRecords(adminService.managementdel_writeGetCount());

		paging.makePaging();

		int i = 0;
		String branchID = null;
		String ids[] = new String[page.size()];
		String ids2[] = new String[page.size()];
		String ids3[] = new String[page.size()];
		String ids4[] = new String[page.size()];
		
		
		
		for (i = 0; i < page.size(); i++) {
			branchID = page.get(i).getId();
			BranchAddressVo address = adminService.management_address(branchID);
			ids[i] = address.getRoadAddress();
			ids2[i] = address.getJibunAddress();
			ids3[i] = address.getDetailAddress();
			ids4[i] = address.getZoneCode();
		}

		model.addAttribute("road", ids);
		model.addAttribute("jibun", ids2);
		model.addAttribute("detailaddress", ids3);
		model.addAttribute("zonecode", ids4);
		model.addAttribute("page", page);
		model.addAttribute("paging", paging);

		return "./admin/managementdel";
	}

	@RequestMapping(value = "/admin/managedeldetail", method = RequestMethod.POST)
	public String managedelDetailpage(String userID, Model model) throws Exception {

		model.addAttribute("bean", adminService.user_selectPage(userID));
		model.addAttribute("branch_infoVo", adminService.user_branch_selectOne(userID));
		model.addAttribute("branchaddress", adminService.user_branch_selectOne_address(userID));

		return "./admin/managedeldetail";
	}

	@RequestMapping(value = "/admin/managedeldetail/{id}", method = RequestMethod.PUT)
	public String deldelete(@PathVariable String id) throws SQLException {
		adminService.manage_deldelete(id);
		adminService.manage_delregistNum(id);

		return "redirect:/admin/managementdel";
	}
}