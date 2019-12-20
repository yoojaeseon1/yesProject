package com.bit.yes.controller;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.apache.ibatis.session.SqlSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.bit.yes.model.UserDao;
import com.bit.yes.model.entity.BranchVo;
import com.bit.yes.model.entity.ReserveListVo;
import com.bit.yes.model.entity.ReviewVo;
import com.bit.yes.model.entity.UserVo;
import com.bit.yes.model.paging.Paging;
import com.bit.yes.service.ReserveListService;
import com.bit.yes.service.ReviewService;


@Controller
public class MyPageController {

	@Autowired
	SqlSession sqlSession;
	@Autowired
	ReserveListService service;
	@Autowired
	ReviewService service2;
	
	
	
	public void setService(ReserveListService service) {
		this.service = service;
	}
	
	
	//-------------?΄? λ³?------------------
	@RequestMapping("/myInfo.yes")
	public String myInfo(HttpSession session,Model model) throws SQLException {
		UserVo user=(UserVo) session.getAttribute("member");
		UserVo bean=sqlSession.getMapper(UserDao.class).login(user.getId());
		model.addAttribute("user", bean);
		return "mypage/myInfo";
	}
	
	@RequestMapping(value="mypageUpdate",method=RequestMethod.POST)
	public String update(HttpSession session,@ModelAttribute UserVo bean,Model model) throws SQLException {
		if(bean.getRegistNum()==null)
		{
			bean.setRegistNum("0");
		}
		int result=sqlSession.getMapper(UserDao.class).updateInfo(bean);
		UserVo user=sqlSession.getMapper(UserDao.class).login(bean.getId());
		if(result>0)
		{
			model.addAttribute("user", user);
			System.out.println(user);
			return "mypage/myInfo";
		}
		else
		{
			System.out.println("?€?¨"); 
			//?€?¨ κ²½λ‘ ?λ§? ??λ¦΄κ±°?!
			return "redirect:../myInfo.yes";
		}
	
	}
	//-----------?? ??΄-----------
	@ResponseBody
	@RequestMapping(value="/deleteUser",method=RequestMethod.POST,produces="application/text; charset=utf8")
	public String deleteUser (String id,HttpSession session) throws SQLException {
		System.out.println(id);
		int result=sqlSession.getMapper(UserDao.class).deleteOne(id);
		if(result>0) {
			session.invalidate();
			return "?±κ³?";
			}
		else {
			return "??κ°???€?¨";
		}
	}
	//------------(κ³ κ°)??½ ??©λ¦¬μ€?Έ λΆλ¬?€κΈ?-----------
	@RequestMapping("/reservation.yes")
	public String reservation(HttpSession session,Model model,HttpServletRequest req) throws SQLException {
		String id=((UserVo)session.getAttribute("member")).getId();
		service.listPage(model,id);
		return "mypage/myReserve";
	}
	
	//-----------(κ³ κ°)??±κΈ? λ³΄κΈ°-----------------------

	@RequestMapping("/myWrite.yes")
	public String myWrite(HttpSession session,Model model,HttpServletRequest req) throws SQLException {
		String id=((UserVo)session.getAttribute("member")).getId();
		List<ReviewVo> list=service.writeList(model,id);
		System.out.println(list);
		return "mypage/mywrite";
	}
	
	//---------λ§μ΄??΄μ§? ?¬? ₯-----------
	@ResponseBody
	@RequestMapping(value="/loadReserve",method=RequestMethod.POST)
	public List<ReserveListVo> loadReserve(HttpSession session,Model model) throws SQLException {
		String id=((UserVo)session.getAttribute("member")).getId();
		UserVo user=sqlSession.getMapper(UserDao.class).login(id);
		List<ReserveListVo> list;
		if(user.getRegistNum().equals("0"))//κ³ κ°
		{
			list=service.listPage(model, id);
			return list;
		}
		else { //?¬??
			list=service.reserveAll(model,id);
			return list;
		}
	}


	//----------??½? κ°?κ²μ ? λ³? λΆλ¬?€κΈ?----------
	@ResponseBody
	@RequestMapping(value="/member_branchInfo",method=RequestMethod.POST)
	public BranchVo reservation2(String id) throws SQLException {
		BranchVo bean=service.selectOne(id);
		return bean;
	}
	
	//-----------??½ μ·¨μ?κΈ?---------------
	@ResponseBody
	@RequestMapping(value="/delreserve",method=RequestMethod.POST)
	public String delReserve(String time,HttpSession session) throws SQLException{
		String id=((UserVo)session.getAttribute("member")).getId();
		ReserveListVo bean=new ReserveListVo();
		bean.setClientID(id);
		bean.setReserveTime(time);
		service.deleteOne(bean);
		return "/reservation.yes";
	}
	
	//-------------??±κΈ? ?­? ?κΈ?----------------
	@ResponseBody
	@RequestMapping(value="/delreview",method=RequestMethod.POST)
	public String delReview(String idx) throws SQLException {
		System.out.println("κΈ?λ²νΈ"+idx);
		int result=service.deleteReview(idx);
		if(result>0)
			return "success";
		else
			return "error";
	}
	
	
	
	//------------------?¬?? mypage-----------------
	@RequestMapping("/branchReserve.yes")
	public String branchReserve(HttpSession session,Model model) throws SQLException{
		UserVo bean=(UserVo) session.getAttribute("member");
		String id=bean.getId();
		//??½ λ¦¬μ€?Έ λΆλ¬?€κΈ?
		service.reserveAll(model,id);
		return "mypage/branchReserve";
	}
	
	
	
	//-------------------?¬?? λ§€μ₯? λ³?-----------------
	@ResponseBody
	@RequestMapping(value = "/branchInfo", method = RequestMethod.POST, produces = "application/json;")
	public List<BranchVo> branchInfo(HttpSession httpSession) {
		String id=((UserVo) httpSession.getAttribute("member")).getId();
		return service.selectOneBranch(id);

	}
	//----------------λ§€μ₯κ΄?λ¦?(??΄λΈ? κ΄?λ¦?)----------------------
	@RequestMapping("/branchManage.yes")
	public String branchManage(HttpSession session,Model model) throws SQLException{
		String id=((UserVo) session.getAttribute("member")).getId();
		BranchVo bean=service.selectBranch(id);
		model.addAttribute("bean",bean);
		return "mypage/branchManage";
	}
	
	
	// --------?€?κ°? state? ?‘(μ’μκ΄?λ¦?)-----------
	@ResponseBody
	@RequestMapping(value="/manageTable",method=RequestMethod.POST)
	public int manageTable(String state,String entry,String entryR,String end,HttpSession session) throws SQLException{
		String id=((UserVo)session.getAttribute("member")).getId();
		BranchVo bean=service.selectBranch(id);
		bean.setTableState(Integer.parseInt(state));
		service.updateState(bean);
		int count=0;
		count=service.loadTicket(id);//??κΈ°ν? ?¬? λͺλͺ?Έμ§?..
		System.out.println("??κΈ°λ²?Έ"+count);
		if(count>0)
		{
			System.out.println("??¬??₯λ²νΈ"+entry);
			//??¬ ??₯ λ²νΈ ???₯?κΈ?--- ???₯ ok??
			if(Integer.parseInt(entry)>0)
			{
			bean.setWaitingNum(Integer.parseInt(entry));
			service.updateWaiting(bean);
			if(entryR!=null) {
				System.out.println(entryR);
				System.out.println("??¬ ??₯λ²νΈ:"+entry);
				//ticketing?? ?­? ?κΈ?---(??¬??₯λ²νΈ)
				service.deleteTicket(Integer.parseInt(entry)); //?­?  ok
				//??¬ ??₯ λ²νΈ? ticketλ²νΈλ₯? ?­? ?¨!
				count=service.loadTicket(id);//??κΈ°ν? ?¬? λͺλͺ?Έμ§?..
			}
			}
			
		}
		
		if(end!=null) {
			//??μ’λ£
			bean.setWaitingNum(Integer.parseInt(entry));
			service.updateWaiting(bean);
			service.end(id);
		}
		return count;
	}
	
	
	//---------------?€?κ°? ??κΈ°μΈ?count----------------
	// --------?€?κ°? state? ?‘(μ’μκ΄?λ¦?)-----------
	@ResponseBody
	@RequestMapping(value="/count",method=RequestMethod.POST,produces="application/text; charset=utf8")
	public String count(HttpSession session,String registNum) {
		String id="";
		if(session.getAttribute("member")!=null) {
		
			id=((UserVo)session.getAttribute("member")).getId();
			
			if(id!=null) {
				int count=0;
				if(!(registNum.equals("0"))) { //?¬??
					count=service.loadTicket(id);//??κΈ°ν? ?¬? λͺλͺ?Έμ§?..
					return "?¬?"+count+"λͺ?";
				}
				else{ //κ³ κ°?Ό κ²½μ°...?? ? ??κΈ°λ²?Έ
					count=service.getNum(id);
					
					if(count>0) {
					int state=service.getState(id);
					return "κ³ κ°"+count+"λ²?/"+state+"λ²?";
					
					}
					return "??κΈ? μ€μΈ κ°?κ²κ? ??΅??€";
					}
				}
			
		}
		
		return null;
		
	}

	@ResponseBody
	@RequestMapping(value = "insertReserve", method = RequestMethod.POST, produces = "application/text; charset=utf8")
	public String insertReserve(@RequestBody Map<String, Object> map, HttpSession session, Model model){
		System.out.println(map);

		if(session.getAttribute("member") == null) return "loginError";
		else{
			String id=((UserVo) session.getAttribute("member")).getId();
			service.insertReserve(map, id);
			return "success";
//			model.addAttribute("reserveMsg","λ‘κ·Έ?Έ?΄ ???©??€.");
		}


	}
	
	//---------------(κ°?λ§Ήμ )?΄?©??© λ³?κ²½νκΈ?-----------
	
	@ResponseBody
	@RequestMapping(value="/useState_change",method=RequestMethod.POST,produces="application/text; charset=utf8")
	public String useState_change(String day,String use) throws SQLException{
		System.out.println(use);
		ReserveListVo bean=new ReserveListVo();
		bean.setUseState(use);
		bean.setReserveTime(day);
		int result=service.updateUseState(bean);
		System.out.println(result);
		return "?΄?©??© λ³?κ²½λ??΅??€";
	}
	
	//-------------(κ°?λ§Ήμ ) λ¦¬λ·° κ²μ?---------------
	
	@RequestMapping("/branch_ReviewList.yes")
	public String branchReview(HttpSession session,HttpServletRequest request,Model model,Model listModel, Model imageModel) throws Exception{
		System.out.println("branch_ReviewList(get)");
		
		String id=((UserVo) session.getAttribute("member")).getId();
		HashMap<String, Object> params = new HashMap<String, Object>();
		int currentPageNo = 1;
		int maxPost = 10;

		if(request.getParameter("pages") != null) {
			System.out.println("pages is null");
			currentPageNo = Integer.parseInt(request.getParameter("pages"));
		}
		
		
		Paging paging = new Paging(currentPageNo, maxPost);
		
		int offset = (paging.getCurrentPageNo() -1) * paging.getMaxPost();
		
		ArrayList<ReviewVo> page = new ArrayList<ReviewVo>();
		
		params.put("offset", offset);
		params.put("noOfRecords", paging.getMaxPost());
		
		
		
		page = (ArrayList<ReviewVo>) service2.writeList(params);
		
		paging.setNumberOfRecords(service2.writeGetCount());
		
		paging.makePaging();
		
		listModel.addAttribute("page", page);
		listModel.addAttribute("paging",paging);
		
		
		service.selectAll(model,id);
		service2.listPageImage(imageModel);
		
		return "mypage/branch_ReviewList";
	}
	

	
	
}
