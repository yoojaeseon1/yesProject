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
	
	
	//-------------?��?��蹂?------------------
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
			System.out.println("?��?��"); 
			//?��?�� 寃쎈� ?��留? ??由닿굅?��!
			return "redirect:../myInfo.yes";
		}
	
	}
	//-----------?��?�� ?��?��-----------
	@ResponseBody
	@RequestMapping(value="/deleteUser",method=RequestMethod.POST,produces="application/text; charset=utf8")
	public String deleteUser (String id,HttpSession session) throws SQLException {
		System.out.println(id);
		int result=sqlSession.getMapper(UserDao.class).deleteOne(id);
		if(result>0) {
			session.invalidate();
			return "?�깃�?";
			}
		else {
			return "?��?��媛??��?��?��";
		}
	}
	//------------(怨�媛�)?��?�� ?��?�⑸━��?�� 遺���?�ㅺ�?-----------
	@RequestMapping("/reservation.yes")
	public String reservation(HttpSession session,Model model,HttpServletRequest req) throws SQLException {
		String id=((UserVo)session.getAttribute("member")).getId();
		service.listPage(model,id);
		return "mypage/myReserve";
	}
	
	//-----------(怨�媛�)?��?�깃�? 蹂닿린-----------------------

	@RequestMapping("/myWrite.yes")
	public String myWrite(HttpSession session,Model model,HttpServletRequest req) throws SQLException {
		String id=((UserVo)session.getAttribute("member")).getId();
		List<ReviewVo> list=service.writeList(model,id);
		System.out.println(list);
		return "mypage/mywrite";
	}
	
	//---------留���?��?�댁�? ?��?��-----------
	@ResponseBody
	@RequestMapping(value="/loadReserve",method=RequestMethod.POST)
	public List<ReserveListVo> loadReserve(HttpSession session,Model model) throws SQLException {
		String id=((UserVo)session.getAttribute("member")).getId();
		UserVo user=sqlSession.getMapper(UserDao.class).login(id);
		List<ReserveListVo> list;
		if(user.getRegistNum().equals("0"))//怨�媛�
		{
			list=service.listPage(model, id);
			return list;
		}
		else { //?��?��?��
			list=service.reserveAll(model,id);
			return list;
		}
	}


	//----------?��?��?�� 媛?寃��� ?��蹂? 遺���?�ㅺ�?----------
	@ResponseBody
	@RequestMapping(value="/member_branchInfo",method=RequestMethod.POST)
	public BranchVo reservation2(String id) throws SQLException {
		BranchVo bean=service.selectOne(id);
		return bean;
	}
	
	//-----------?��?�� 痍⑥��?��湲?---------------
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
	
	//-------------?��?�깃�? ?��?��?��湲?----------------
	@ResponseBody
	@RequestMapping(value="/delreview",method=RequestMethod.POST)
	public String delReview(String idx) throws SQLException {
		System.out.println("湲?踰���"+idx);
		int result=service.deleteReview(idx);
		if(result>0)
			return "success";
		else
			return "error";
	}
	
	
	
	//------------------?��?��?�� mypage-----------------
	@RequestMapping("/branchReserve.yes")
	public String branchReserve(HttpSession session,Model model) throws SQLException{
		UserVo bean=(UserVo) session.getAttribute("member");
		String id=bean.getId();
		//?��?�� 由ъ��?�� 遺���?�ㅺ�?
		
		List<ReserveListVo> reserveList = new ArrayList<>();
		service.reserveAll(model,id);
		
		return "mypage/branchReserve";
	}
	
	
	
	//-------------------?��?��?�� 留ㅼ��?��蹂?-----------------
	@ResponseBody
	@RequestMapping(value = "/branchInfo", method = RequestMethod.POST, produces = "application/json;")
	public List<BranchVo> branchInfo(HttpSession httpSession) {
		String id=((UserVo) httpSession.getAttribute("member")).getId();
		return service.selectOneBranch(id);

	}
	//----------------留ㅼ�κ�?由?(?��?�대�? 愿?由?)----------------------
	@RequestMapping("/branchManage.yes")
	public String branchManage(HttpSession session,Model model) throws SQLException{
		String id=((UserVo) session.getAttribute("member")).getId();
		BranchVo bean=service.selectBranch(id);
		model.addAttribute("bean",bean);
		return "mypage/branchManage";
	}
	
	
	// --------?��?��媛? state?��?��(醫���愿?由?)-----------
	@ResponseBody
	@RequestMapping(value="/manageTable",method=RequestMethod.POST)
	public int manageTable(String state,String entry,String entryR,String end,HttpSession session) throws SQLException{
		String id=((UserVo)session.getAttribute("member")).getId();
		BranchVo bean=service.selectBranch(id);
		bean.setTableState(Integer.parseInt(state));
		service.updateState(bean);
		int count=0;
		count=service.loadTicket(id);//??湲고��?�� ?��?�� 紐�紐�?�몄�?..
		System.out.println("??湲곕�?��"+count);
		if(count>0)
		{
			System.out.println("?��?��?��?�λ���"+entry);
			//?��?�� ?��?�� 踰��� ???��?��湲?--- ???�� ok??
			if(Integer.parseInt(entry)>0)
			{
			bean.setWaitingNum(Integer.parseInt(entry));
			service.updateWaiting(bean);
			if(entryR!=null) {
				System.out.println(entryR);
				System.out.println("?��?�� ?��?�λ���:"+entry);
				//ticketing?��?�� ?��?��?��湲?---(?��?��?��?�λ���)
				service.deleteTicket(Integer.parseInt(entry)); //?��?�� ok
				//?��?�� ?��?�� 踰���?�� ticket踰��몃�? ?��?��?��!
				count=service.loadTicket(id);//??湲고��?�� ?��?�� 紐�紐�?�몄�?..
			}
			}
			
		}
		
		if(end!=null) {
			//?��?��醫�猷�
			bean.setWaitingNum(Integer.parseInt(entry));
			service.updateWaiting(bean);
			service.end(id);
		}
		return count;
	}
	
	
	//---------------?��?��媛? ??湲곗��?��count----------------
	// --------?��?��媛? state?��?��(醫���愿?由?)-----------
	@ResponseBody
	@RequestMapping(value="/count",method=RequestMethod.POST,produces="application/text; charset=utf8")
	public String count(HttpSession session,String registNum) {
		String id="";
		if(session.getAttribute("member")!=null) {
		
			id=((UserVo)session.getAttribute("member")).getId();
			
			if(id!=null) {
				int count=0;
				if(!(registNum.equals("0"))) { //?��?��?��
					count=service.loadTicket(id);//??湲고��?�� ?��?�� 紐�紐�?�몄�?..
					return "?��?��"+count+"紐?";
				}
				else{ //怨�媛�?�� 寃쎌��...?��?��?�� ??湲곕�?��
					count=service.getNum(id);
					
					if(count>0) {
					int state=service.getState(id);
					return "怨�媛�"+count+"踰?/"+state+"踰?";
					
					}
					return "??湲? 以��� 媛?寃��? ?��?��?��?��";
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
//			model.addAttribute("reserveMsg","濡�洹�?��?�� ?��?��?��?��?��.");
		}


	}
	
	//---------------(媛?留뱀��)?��?��?��?�� 蹂?寃쏀��湲?-----------
	
	@ResponseBody
	@RequestMapping(value="/useState_change",method=RequestMethod.POST,produces="application/text; charset=utf8")
	public String useState_change(String day,String use) throws SQLException{
		System.out.println(use);
		ReserveListVo bean=new ReserveListVo();
		bean.setUseState(use);
		bean.setReserveTime(day);
		int result=service.updateUseState(bean);
		System.out.println(result);
		return "?��?��?��?�� 蹂?寃쎈��?��?��?��?��";
	}
	
	//-------------(媛?留뱀��) 由щ럭 寃���?��---------------
	
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
