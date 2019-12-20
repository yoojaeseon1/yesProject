package com.bit.yes.controller;

import java.io.File;
import java.io.IOException;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.UUID;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;

import com.bit.yes.model.entity.C_CsVo;
import com.bit.yes.model.entity.ImageVo;
import com.bit.yes.model.entity.UserVo;
import com.bit.yes.model.entity.branch_addressVo;
import com.bit.yes.model.paging.Paging;
import com.bit.yes.service.C_CsService;

@Controller
public class C_CsController {

	@Autowired
	C_CsService csService;

	public void setService(C_CsService service) {
		this.csService = service;
	}

	// ?λ©? μΆλ ₯
		@RequestMapping("/yesC_cs/")
		public String list(Model model, HttpServletRequest req, HttpSession httpSession) throws Exception {
			int currentPageNo = 1;
			int maxPost = 10;

			HashMap<String, Object> params = new HashMap<String, Object>();

			// λ‘κ·Έ?Έ ?? κ²½μ° ?€?΄?€? ?Έ? idκ°?
			// idκ°μ ?΅?΄? registNumκ°μ λ½μ? κ³΅μ??¬?­,κ³ κ°??΄,κ°?λ§Ήμ ??΄μ€? μΆλ ₯?  κ²μ κ²°μ 
			// admin?΄ κ΄?λ¦¬μ?΄?¬?Ό?¨
			String clientID = null;

			if((UserVo)httpSession.getAttribute("member") != null) clientID = ((UserVo)httpSession.getAttribute("member")).getId();
			else return "redirect:/";

			if(req.getParameter("pages") != null)
				currentPageNo = Integer.parseInt(req.getParameter("pages"));

			Paging paging = new Paging(currentPageNo, maxPost);

			int offset = (paging.getCurrentPageNo() -1) * paging.getMaxPost();

			ArrayList<C_CsVo> page = new ArrayList<C_CsVo>();
			params.put("offset", offset);
			params.put("noOfRecords", paging.getMaxPost());
			params.put("clientID", clientID);

			page = (ArrayList<C_CsVo>) csService.writeList(params);
			paging.setNumberOfRecords(csService.writeGetCount(params));

			paging.makePaging();
			System.out.println("clientID : "+clientID);
			String registNum = csService.user_selectOne(clientID).getRegistNum();
			model.addAttribute("registNum", registNum);
			model.addAttribute("page", page);
			model.addAttribute("paging",paging);

			return "./clientCounsel/yesC_cs";

		}

		@RequestMapping(value="/C_Cs_search")
		public String C_CsSearchList(Model model, HttpServletRequest request, HttpSession httpSession) throws Exception {

			HttpSession session = request.getSession();
			HashMap<String, Object> params = new HashMap<String, Object>();
			System.out.println("list(post)");
			// λ‘κ·Έ?Έ ?? κ²½μ° ?€?΄?€? ?Έ? idκ°?
			// idκ°μ ?΅?΄? registNumκ°μ λ½μ? κ³΅μ??¬?­,κ³ κ°??΄,κ°?λ§Ήμ ??΄μ€? μΆλ ₯?  κ²μ κ²°μ 
			// admin?΄ κ΄?λ¦¬μ?΄?¬?Ό?¨
			String clientID = ((UserVo)httpSession.getAttribute("member")).getId();

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

			Paging paging = new Paging(currentPageNo, maxPost);
			int offset = (paging.getCurrentPageNo() -1) * paging.getMaxPost();
			ArrayList<C_CsVo> page = new ArrayList<C_CsVo>();

			params.put("offset", offset);
			params.put("noOfRecords", paging.getMaxPost());
			params.put("keyword", keyword);
			params.put("category", category);
			params.put("clientID", clientID);

			page = (ArrayList<C_CsVo>) csService.writeList(params);
			paging.setNumberOfRecords(csService.writeGetCount(params));

			paging.makePaging();
			model.addAttribute("id",clientID);
			model.addAttribute("page", page);
			model.addAttribute("paging",paging);

			return "./clientCounsel/yesC_cs";
		}

		@RequestMapping(value="/yesC_cs/{idx}", method=RequestMethod.GET)
		public String detail(@PathVariable int idx, Model model, HttpSession httpSession) throws SQLException {

			// λ‘κ·Έ?Έ ?? κ²½μ° ?€?΄?€? ?Έ? idκ°?
			// idκ°μ ?΅?΄? registNumκ°μ λ½μ? κ³΅μ??¬?­,κ³ κ°??΄,κ°?λ§Ήμ ??΄μ€? μΆλ ₯?  κ²μ κ²°μ 
			// idκ°μ ?΅?΄? id? ?΄?Ή?? κ²μκΈ?λ§? μΆλ ₯
			// admin?΄ κ΄?λ¦¬μ?΄?¬?Ό?¨
			String clientID = ((UserVo)httpSession.getAttribute("member")).getId();


			String registNum = csService.user_selectOne(clientID).getRegistNum();

			String id = (csService.selectPage(idx)).getBranchID(); // κ°?λ§Ήμ  ??΄? ?»?΄?€κΈ?

			if(id.equals("?΄?Ή ??")) {
				model.addAttribute("id",id);
				model.addAttribute("beans", csService.reserveOne(id)); //branch_info κ°?
				model.addAttribute("bean", csService.selectPage(idx));
				model.addAttribute("subImages", csService.c_counselSubImage(idx));
				model.addAttribute("registNum",registNum);

			}else {
				branch_addressVo address = csService.c_selectAddress(id);
				String road = address.getRoadAddress();
				String jibun = address.getJibunAddress();
				String detailaddress = address.getDetailAddress();
				String zonecode = address.getZoneCode();

				model.addAttribute("registNum",registNum);
				model.addAttribute("road", road );
				model.addAttribute("jibun", jibun);
				model.addAttribute("detailaddress", detailaddress);
				model.addAttribute("zonecode", zonecode);
				model.addAttribute("id",id);
				model.addAttribute("beans", csService.reserveOne(id)); //branch_info κ°?
				model.addAttribute("bean", csService.selectPage(idx));
				model.addAttribute("subImages", csService.c_counselSubImage(idx));

			}
			return "./clientCounsel/yesC_csDetail";
		}

		@RequestMapping("/yesC_cs/yesC_csInsert")
		public String insertpage(String id, UserVo nickName, Model model, HttpSession httpSession) throws SQLException {

			// λ‘κ·Έ?Έ ?? κ²½μ° ?€?΄?€? ?Έ? idκ°?
			// idκ°μ ?΅?΄? registNumκ°μ λ½μ? κ³΅μ??¬?­,κ³ κ°??΄,κ°?λ§Ήμ ??΄μ€? μΆλ ₯?  κ²μ κ²°μ 
			// admin?΄ κ΄?λ¦¬μ?΄?¬?Ό?¨
			id = ((UserVo)httpSession.getAttribute("member")).getId();

			String registNum = csService.user_selectOne(id).getRegistNum();

			nickName=csService.selectNick(id);

			int i=0;
			String branchID = null;
			String ids[] = new String[csService.reserveList(id).size()];
			String ids2[] = new String[csService.reserveList(id).size()];
			String ids3[] = new String[csService.reserveList(id).size()];
			String ids4[] = new String[csService.reserveList(id).size()];
			for(i=0; i<csService.reserveList(id).size(); i++) {
				branchID= csService.reserveList(id).get(i).getId();
				branch_addressVo address = csService.c_selectAddress(branchID);
				ids[i] = address.getRoadAddress();
				ids2[i] = address.getJibunAddress();
				ids3[i] = address.getDetailAddress();
				ids4[i] = address.getZoneCode();
			}

			model.addAttribute("registNum",registNum);
			model.addAttribute("road",ids );
			model.addAttribute("jibun", ids2);
			model.addAttribute("detailaddress", ids3);
			model.addAttribute("zonecode", ids4);
			model.addAttribute("clientID", id);
			model.addAttribute("userInfo",nickName);
			model.addAttribute("bean", csService.reserveList(id));
			return "./clientCounsel/yesC_csInsert";
		}

		@RequestMapping(value="/yesC_cs/yesC_csInsert",method=RequestMethod.POST)
		public String insert(C_CsVo csvo, MultipartHttpServletRequest mtfrequest, Model model) throws SQLException {

			csService.addPage(csvo);

			String genId, fileName, path;
			ImageVo imageBean = new ImageVo();
			java.util.List<MultipartFile> subFiles = mtfrequest.getFiles("subImages");
			genId = UUID.randomUUID().toString();
			String attach_path = "resources/c_counsel_imgs/";
			String root_path=mtfrequest.getSession().getServletContext().getRealPath("/");
			path = root_path + attach_path;

			try {
				for(MultipartFile subFile : subFiles) {
					String originalFileName = subFile.getOriginalFilename();
					if(originalFileName == "") {
						fileName = "0" + originalFileName;
						System.out.println(fileName);
						imageBean.setImageName(fileName);
						csService.c_counselImgUpload(imageBean);
					}else {
						fileName = genId + originalFileName;
						genId = UUID.randomUUID().toString();
						imageBean.setImageName(fileName);
						subFile.transferTo(new File(path + fileName));
						csService.c_counselImgUpload(imageBean);
					}

				}
			}catch (IOException e) {
				e.printStackTrace();
			}
			return "redirect:/yesC_cs/";
		}

}
