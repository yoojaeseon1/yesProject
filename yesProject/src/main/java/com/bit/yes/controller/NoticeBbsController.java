package com.bit.yes.controller;

import java.io.File;
import java.io.IOException;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.UUID;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import com.bit.yes.model.entity.UserVo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;

import com.bit.yes.model.entity.ImageVo;
import com.bit.yes.model.entity.NoticeVo;
import com.bit.yes.model.paging.Paging;
import com.bit.yes.service.NoticeService;

@Controller
public class NoticeBbsController {
	@Autowired
	NoticeService noticeService;

	public void setService(NoticeService service) {
		this.noticeService = service;
	}
	// ȭ�� ���
	@RequestMapping("/yesnotice/")
	public String list(Model model, HttpServletRequest req,HttpSession httpSession) throws Exception {
		HashMap<String, Object> params = new HashMap<String, Object>();
		int currentPageNo = 1;
		int maxPost = 10;

		// �α��� ���� ��� ������ ���� id��
		// id���� ���ؼ� registNum���� �̾Ƽ� ��������,�����,����������� ����� ���� ����
		// admin�� �������̿�����
		String id = ((UserVo)httpSession.getAttribute("member")).getId();

		if(req.getParameter("pages") != null)
			currentPageNo = Integer.parseInt(req.getParameter("pages"));

		Paging paging = new Paging(currentPageNo, maxPost);

		int offset = (paging.getCurrentPageNo() -1) * paging.getMaxPost();

		params.put("offset", offset);
		params.put("noOfRecords", paging.getMaxPost());

		ArrayList<NoticeVo> page = new ArrayList<NoticeVo>();
		page = (ArrayList<NoticeVo>) noticeService.writeList(params);
		paging.setNumberOfRecords(noticeService.writeGetCount());

		paging.makePaging();

		if(id == "admin") {
			model.addAttribute("id",id);
			model.addAttribute("page", page);
			model.addAttribute("paging",paging);

		}else{
			String registNum = noticeService.user_selectOne(id).getRegistNum();
			model.addAttribute("registNum",registNum);
			model.addAttribute("id",id);
			model.addAttribute("page", page);
			model.addAttribute("paging",paging);
		}

		return "./notice/yesnotice";
	}

	@RequestMapping("/yesnotice/yesnoticeInsert")
	public String insertpage(Model model, HttpSession httpSession) throws SQLException {

		// �α��� ���� ��� ������ ���� id��
		// id���� ���ؼ� registNum���� �̾Ƽ� ��������,�����,����������� ����� ���� ����
		// admin�� �������̿�����
		String id = ((UserVo)httpSession.getAttribute("member")).getId();

		String registNum = noticeService.user_selectOne(id).getRegistNum();
		model.addAttribute("registNum",registNum);
		model.addAttribute("id",id);
		return "./notice/yesnoticeInsert";
	}

	@RequestMapping(value="/yesnotice/yesnoticeUpdate/{idx}",method=RequestMethod.GET)
	public String updatepage(@PathVariable int idx,Model model, HttpSession httpSession) throws SQLException {

		// �α��� ���� ��� ������ ���� id��
		// id���� ���ؼ� registNum���� �̾Ƽ� ��������,�����,����������� ����� ���� ����
		// admin�� �������̿�����
		String id = ((UserVo)httpSession.getAttribute("member")).getId();

		String registNum = noticeService.user_selectOne(id).getRegistNum();
		model.addAttribute("registNum",registNum);
		model.addAttribute("id",id);
		model.addAttribute("bean", noticeService.selectPage(idx));
		return "./notice/yesnoticeUpdate";
	}

	// ���� �󼼺��� ���� ����
	// ����
	@RequestMapping(value="/yesnotice/yesnoticeInsert",method=RequestMethod.POST)
	public String insert(NoticeVo noticevo,MultipartHttpServletRequest mtfrequest, Model model) throws SQLException {

		noticeService.addPage(noticevo);

		String genId, fileName, path;
		ImageVo imageBean = new ImageVo();
		java.util.List<MultipartFile> subFiles = mtfrequest.getFiles("subImages");
		genId = UUID.randomUUID().toString();
		String attach_path = "resources/notice_imgs/";
		String root_path=mtfrequest.getSession().getServletContext().getRealPath("/");
		path = root_path + attach_path;

		try {
			for(MultipartFile subFile : subFiles) {
				String originalFileName = subFile.getOriginalFilename();
				if(originalFileName == "") {
					fileName = "0" + originalFileName;
					System.out.println(fileName);
					imageBean.setImageName(fileName);
					noticeService.noticeImgUpload(imageBean);
				}else {
					fileName = genId + originalFileName;
					genId = UUID.randomUUID().toString();
					imageBean.setImageName(fileName);
					subFile.transferTo(new File(path + fileName));
					noticeService.noticeImgUpload(imageBean);
				}

			}
		}catch (IOException e) {
			e.printStackTrace();
		}

		return "redirect:/yesnotice/";
	}


	// �󼼺���
	@RequestMapping(value="/yesnotice/{idx}",method=RequestMethod.GET )
	public String detail(@PathVariable int idx,Model model, HttpSession httpSession) throws SQLException {

		// �α��� ���� ��� ������ ���� id��
		// id���� ���ؼ� registNum���� �̾Ƽ� ��������,�����,����������� ����� ���� ����
		// admin�� �������̿�����
		String id = ((UserVo)httpSession.getAttribute("member")).getId();

		String registNum = noticeService.user_selectOne(id).getRegistNum();
		model.addAttribute("registNum",registNum);
		model.addAttribute("id", id);
		model.addAttribute("bean", noticeService.selectPage(idx));
		model.addAttribute("subImages", noticeService.noticeSubImage(idx));

		return "./notice/yesnoticeDetail";
	}
	// ����
	@RequestMapping(value="/yesnotice/{idx}",method=RequestMethod.DELETE)
	public String delete(@PathVariable int idx) throws SQLException {
		noticeService.deletePage(idx);
		return "redirect:/yesnotice/";
	}
	// ����
	@RequestMapping(value="/yesnotice/yesnoticeUpdate/{idx}",method=RequestMethod.POST )
	public String edit(@ModelAttribute NoticeVo bean,@PathVariable int idx,MultipartHttpServletRequest mtfrequest) throws SQLException {
		bean.setIndex(idx);
		noticeService.updatedeletePage(idx);
		noticeService.updatePage(bean);

		String genId, fileName, path;
		ImageVo imageBean = new ImageVo();
		java.util.List<MultipartFile> subFiles = mtfrequest.getFiles("subImages");

		genId = UUID.randomUUID().toString();

		String attach_path = "resources/notice_imgs/";

		String root_path=mtfrequest.getSession().getServletContext().getRealPath("/");

		path = root_path + attach_path;

		try {
			for(MultipartFile subFile : subFiles) {
				String originalFileName = subFile.getOriginalFilename();
				if(originalFileName == "") {
					fileName = "0" + originalFileName;
					imageBean.setImageName(fileName);
					imageBean.setReviewIndex(idx);
					noticeService.updateimgPage(imageBean);
				}else {
					fileName = genId + originalFileName;
					genId = UUID.randomUUID().toString();
					imageBean.setImageName(fileName);
					imageBean.setReviewIndex(idx);
					subFile.transferTo(new File(path + fileName));
					noticeService.updateimgPage(imageBean);
				}

			}
		}catch (IOException e) {
			e.printStackTrace();
		}

		return "redirect:/yesnotice/";
	}

	// �˻�
	@RequestMapping(value="/notice_search")
	public String noticeSearchList(Model model, HttpServletRequest request, HttpSession httpSession) throws Exception {

		System.out.println("searchList(post)");

		HttpSession session = request.getSession();
		HashMap<String, Object> params = new HashMap<String, Object>();
		System.out.println("list(post)");
		// �α��� ���� ��� ������ ���� id��
		// id���� ���ؼ� registNum���� �̾Ƽ� ��������,�����,����������� ����� ���� ����
		// admin�� �������̿�����
		String id = ((UserVo)httpSession.getAttribute("member")).getId();

		int currentPageNo = 1;
		int maxPost = 10;

		String category = request.getParameter("category");
		String keyword = request.getParameter("keyword");


		if(request.getParameter("pages") != null) {
			System.out.println("pages is null");
			currentPageNo = Integer.parseInt(request.getParameter("pages"));
		}

		if(category == null && keyword == null) {
			category = (String) session.getAttribute("category");
			keyword = (String) session.getAttribute("keyword");
		} else {
			/*req.setAttribute("category", category);
			req.setAttribute("keyword", keyword);*/
			session.setAttribute("category", category);
			session.setAttribute("keyword", keyword);
		}

		System.out.println("current page(post) : " + currentPageNo);
		Paging paging = new Paging(currentPageNo, maxPost);
		System.out.println("ó������¡"+paging);
		int offset = (paging.getCurrentPageNo() -1) * paging.getMaxPost();
		params.put("offset", offset);
		params.put("noOfRecords", paging.getMaxPost());

		ArrayList<NoticeVo> page = new ArrayList<NoticeVo>();
		params.put("keyword", keyword);
		params.put("category", category);
		page = (ArrayList<NoticeVo>) noticeService.writeList(params);
		paging.setNumberOfRecords(noticeService.writeGetCount(params));

		paging.makePaging();
		if(id == "admin") {
			model.addAttribute("id",id);
			model.addAttribute("page", page);
			model.addAttribute("paging",paging);

		}else{
			String registNum = noticeService.user_selectOne(id).getRegistNum();
			model.addAttribute("registNum",registNum);
			model.addAttribute("id",id);
			model.addAttribute("page", page);
			model.addAttribute("paging",paging);
		}

		return "./notice/yesnotice";
	}

}