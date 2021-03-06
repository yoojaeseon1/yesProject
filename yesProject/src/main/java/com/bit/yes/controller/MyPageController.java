package com.bit.yes.controller;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.apache.ibatis.session.SqlSession;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.bit.yes.model.LoginDAO;
import com.bit.yes.model.entity.BranchVo;
import com.bit.yes.model.entity.ImageVo;
import com.bit.yes.model.entity.ReserveListVo;
import com.bit.yes.model.entity.ReviewVo;
import com.bit.yes.model.entity.UserVo;
import com.bit.yes.model.paging.PageMaker;
import com.bit.yes.model.paging.SearchCriteria;
import com.bit.yes.service.LoginService;
import com.bit.yes.service.ReserveListService;
import com.bit.yes.service.ReviewService;

@Controller
public class MyPageController {

	@Autowired
	SqlSession sqlSession;
	@Autowired
	ReserveListService reserveService;
	@Autowired
	ReviewService reviewService;
	@Autowired
	LoginService loginService;

	private final Logger logger = LoggerFactory.getLogger(MyPageController.class);

//	public void setService(ReserveListService reserveService) {
//		this.reserveService = reserveService;
//	}

	// -------------내정보------------------
	@RequestMapping("/myInfo.yes")
	public String myInfo(HttpSession session, Model model) throws Exception {
		UserVo user = (UserVo) session.getAttribute("member");
		UserVo bean = new UserVo();
		
		bean.setId(user.getId());
		
//		UserVo bean = sqlSession.getMapper(LoginDAO.class).selectUserByID(user.getId());
		
		UserVo selectedBean = loginService.selectUserInfo(bean);
		logger.info("myInfo-user : " + selectedBean);
		model.addAttribute("user", selectedBean);
		return "mypage/myInfo";
	}

	@RequestMapping(value = "mypageUpdate", method = RequestMethod.POST)
	public String update(HttpSession session, @ModelAttribute UserVo bean, Model model) throws Exception {
		
		if (bean.getRegistNum() == null) {
			bean.setRegistNum("0");
		}
		
		
		int result = sqlSession.getMapper(LoginDAO.class).updateInfo(bean);
		UserVo user = sqlSession.getMapper(LoginDAO.class).selectUserInfo(bean);
		
		if (result > 0) {
			model.addAttribute("user", user);
			System.out.println(user);
			return "mypage/myInfo";
		} else {
			System.out.println("실패");
			// 실패 경로 아마 틀릴거임!
			return "redirect:../myInfo.yes";
		}

	}

	// -----------회원 탈퇴-----------
	@ResponseBody
	@RequestMapping(value = "/deleteUser", method = RequestMethod.POST, produces = "application/text; charset=utf8")
	public String deleteUser(String id, HttpSession session) throws Exception {
		System.out.println(id);
		int result = sqlSession.getMapper(LoginDAO.class).deleteOne(id);
		if (result > 0) {
			session.invalidate();
			return "성공";
		} else {
			return "회원가입실패";
		}
	}

	// ------------(고객)예약 현황리스트 불러오기-----------
	@RequestMapping(value="/myReservation", method=RequestMethod.GET)
	public String reservation(HttpSession session, Model model, HttpServletRequest req, @ModelAttribute("cri") SearchCriteria cri) throws Exception {
//		String id = ((UserVo) session.getAttribute("member")).getId();
//		reserveService.listPage(model, id);
//		logger.info("into myReservation");
//		logger.info("cri : " + cri);
		
		cri.setWriter(((UserVo) session.getAttribute("member")).getId());
		
		List<ReserveListVo> reservations = reserveService.listPage(cri);
		
		PageMaker pageMaker = new PageMaker();
		
		pageMaker.setCri(cri);
		
		pageMaker.setTotalCount(reserveService.selectTotalReservation(cri));
		
//		logger.info("pageMaker : " + pageMaker);
//		
//		for(ReserveListVo reservation : reservations) {
//			logger.info("reservation : " + reservation);
//		}
		
		
		model.addAttribute("reservations", reservations);
		model.addAttribute("pageMaker", pageMaker);
//		model.addAttribute("reviews", reserveService.listPage(id));
		return "mypage/myReserve";
	}

	// -----------(고객)작성글 보기-----------------------

	@RequestMapping("/myReviewWrite")
	public String myWrite(@ModelAttribute("cri") SearchCriteria cri, HttpSession session, Model model, HttpServletRequest req) throws Exception {
		String id = ((UserVo) session.getAttribute("member")).getId();
		
//		SearchCriteria cri = new SearchCriteria();
		
		cri.setSearchType("w");
		cri.setKeyword(id);
		
		logger.info("current page : " + cri.getPage());
		
		PageMaker pageMaker = new PageMaker();
		
		pageMaker.setCri(cri);
		pageMaker.setTotalCount(reviewService.selectReviewSearchCount(cri));
		
		List<ReviewVo> myReviews = reviewService.listReviewSearchCri(cri);
		
		for(ReviewVo review : myReviews) {
			review.setBranchID(reserveService.selectBranchName(review.getBranchID()));
		}
		
		
		logger.info("myWrite : " + myReviews);
		model.addAttribute("myReviews",myReviews);
		model.addAttribute("pageMaker", pageMaker);
		
		return "mypage/mywrite";
	}

	// ---------마이페이지 달력-----------
	@ResponseBody
	@RequestMapping(value = "/loadReserve", method = RequestMethod.POST)
	public List<ReserveListVo> loadReserve(HttpSession session, Model model) throws Exception {
		String id = ((UserVo) session.getAttribute("member")).getId();
//		UserVo user = sqlSession.getMapper(LoginDAO.class).checkIDDup(id);
		UserVo bean = new UserVo();
		bean.setId(id);
		UserVo user = loginService.selectUserInfo(bean);
//		UserVo user = loginService.checkIDDup(id);
		
		List<ReserveListVo> reservations = null;
		
		
		if (user.getRegistNum().equals("0"))// 고객
		{
//			list = reserveService.listPage(id);
			reservations = reserveService.listPage(new SearchCriteria());
			model.addAttribute("rlist", reservations);
			return reservations;
		} else { // 사업자
			reservations = reserveService.reserveAll(id);
			model.addAttribute("alist",reservations);
			return reservations;
		}
	}

	// ----------예약한 가게의 정보 불러오기----------
	@ResponseBody
	@RequestMapping(value = "/member_branchInfo", method = RequestMethod.POST)
	public BranchVo reservation2(String id) throws SQLException {
		BranchVo bean = reserveService.selectOne(id);
		return bean;
	}

	// -----------예약 취소하기---------------
	@ResponseBody
	@RequestMapping(value = "/delreserve", method = RequestMethod.POST)
	public String delReserve(String time, HttpSession session) throws SQLException {
		String id = ((UserVo) session.getAttribute("member")).getId();
		ReserveListVo bean = new ReserveListVo();
		bean.setClientID(id);
		bean.setReserveTime(time);
		reserveService.deleteOne(bean);
		return "/myReservation";
	}

	// -------------작성글 삭제하기----------------
	@ResponseBody
	@RequestMapping(value = "/deleteReview", method = RequestMethod.POST)
	public String delReview(String reviewIndex) throws SQLException {
//		System.out.println("글번호" + idx);
		int result = reserveService.deleteReview(reviewIndex);
		if (result > 0)
			return "success";
		else
			return "error";
	}

	// ------------------사업자 mypage-----------------
	@RequestMapping("/branchReserve.yes")
	public String branchReserve(HttpSession session, Model model) throws SQLException {
		UserVo bean = (UserVo) session.getAttribute("member");
		String id = bean.getId();
		// 예약 리스트 불러오기
//		reserveService.reserveAll(id);
      model.addAttribute("alist",reserveService.reserveAll(id));
		return "mypage/branchReserve";
	}

	// -------------------사업자 매장정보-----------------
	@ResponseBody
	@RequestMapping(value = "/branchInfo", method = RequestMethod.POST, produces = "application/json;")
	public List<BranchVo> branchInfo(HttpSession httpSession) {
		String id = ((UserVo) httpSession.getAttribute("member")).getId();
		return reserveService.selectOneBranch(id);

	}

	// ----------------매장관리(테이블 관리)----------------------
	@RequestMapping("/branchManage.yes")
	public String branchManage(HttpSession session, Model model) throws SQLException {
		String id = ((UserVo) session.getAttribute("member")).getId();
		BranchVo bean = reserveService.selectBranch(id);
		model.addAttribute("bean", bean);
		return "mypage/branchManage";
	}

	// --------실시간 state전송(좌석관리)-----------
	@ResponseBody
	@RequestMapping(value = "/manageTable", method = RequestMethod.POST)
	public int manageTable(String state, String entry, String entryR, String end, HttpSession session)
			throws SQLException {
		String id = ((UserVo) session.getAttribute("member")).getId();
		BranchVo bean = reserveService.selectBranch(id);
		bean.setTableState(Integer.parseInt(state));
		reserveService.updateState(bean);
		int count = 0;
		count = reserveService.loadTicket(id);// 대기하는 사람 몇명인지..
		System.out.println("대기번호" + count);
		if (count > 0) {
			System.out.println("현재입장번호" + entry);
			// 현재 입장 번호 저장하기--- 저장 okƒ
			if (Integer.parseInt(entry) > 0) {
				bean.setWaitingNum(Integer.parseInt(entry));
				reserveService.updateWaiting(bean);
				if (entryR != null) {
					System.out.println(entryR);
					System.out.println("현재 입장번호:" + entry);
					// ticketing에서 삭제하기---(현재입장번호)
					reserveService.deleteTicket(Integer.parseInt(entry)); // 삭제 ok
					// 현재 입장 번호의 ticket번호를 삭제함!
					count = reserveService.loadTicket(id);// 대기하는 사람 몇명인지..
				}
			}

		}

		if (end != null) {
			// 영업종료
			bean.setWaitingNum(Integer.parseInt(entry));
			reserveService.updateWaiting(bean);
			reserveService.end(id);
		}
		return count;
	}

	// ---------------실시간 대기인원count----------------
	// --------실시간 state전송(좌석관리)-----------
	@ResponseBody
	@RequestMapping(value = "/count", method = RequestMethod.POST, produces = "application/text; charset=utf8")
	public String count(HttpSession session, String registNum) {
		String id = "";
		if (session.getAttribute("member") != null) {

			id = ((UserVo) session.getAttribute("member")).getId();

			if (id != null) {
				int count = 0;
				if (!(registNum.equals("0"))) { // 사업자
					count = reserveService.loadTicket(id);// 대기하는 사람 몇명인지..
					return "사업" + count + "명";
				} else { // 고객일 경우...자신의 대기번호
					count = reserveService.getNum(id);

					if (count > 0) {
						int state = reserveService.getState(id);
						return "고객" + count + "번/" + state + "번";

					}
					return "대기 중인 가게가 없습니다";
				}
			}

		}

		return null;

	}

	@ResponseBody
	@RequestMapping(value = "insertReserve", method = RequestMethod.POST, produces = "application/text; charset=utf8")
	public String insertReserve(@RequestBody Map<String, Object> map, HttpSession session, Model model) {
		logger.info(map.toString());
		
		
		
		if (session.getAttribute("member") == null)
			return "loginError";
		else {
			String id = ((UserVo) session.getAttribute("member")).getId();
			reserveService.insertReserve(map, id);
			return "success";
//			model.addAttribute("reserveMsg","로그인이 필요합니다.");
		}

	}

	// ---------------(가맹점)이용현황 변경하기-----------

	@ResponseBody
	@RequestMapping(value = "/useState_change", method = RequestMethod.POST, produces = "application/text; charset=utf8")
	public String changeUseState(String useState, int reserveIndex) throws SQLException {
		
		System.out.println(useState);
		logger.info("changeUseState(POST) : ");
		logger.info("reserveIndex : " + reserveIndex);

//		String reserveState = reserveService.selectUseState(bean);
//		String reserveState = reserveService.selectUseState(bean);

		Map<String, Object> reserveMap = new HashMap<>();

		reserveMap.put("reserveIndex", reserveIndex);

		if (useState.equals("Y"))
			reserveMap.put("useState", "N");
		else if (useState.equals("N"))
			reserveMap.put("useState", "Y");

		int result = reserveService.updateUseState(reserveMap);
		System.out.println(result);
		return "success";
	}

	// -------------(가맹점) 리뷰 게시판---------------

	@RequestMapping("/branch_ReviewList.yes")
	public String branchReview(@ModelAttribute("cri") SearchCriteria cri, HttpSession session, HttpServletRequest request, Model model) throws Exception {
		
		logger.info("branch_ReviewList(get)");
		
		String branchID = ((UserVo) session.getAttribute("member")).getId();
		
		cri.setBranchID(branchID);
		
		
		
		List<ReviewVo> branchReviews = reviewService.selectBranchReview(cri);
		
		logger.info("cri : " + cri);
		
		
		PageMaker pageMaker = new PageMaker();
		
		pageMaker.setCri(cri);
		pageMaker.setTotalCount(reviewService.selectBranchReviewCount(cri));
		
		List<ImageVo> images = new ArrayList<>();
		
		
		for(ReviewVo review : branchReviews) {
			logger.info("review : " + review);
			
			ImageVo image = new ImageVo();
			image.setReviewIndex(review.getReviewIndex());
			
			String thumbnailName = reviewService.selectThumbnail(review.getReviewIndex());
			if(thumbnailName == null)
				image.setImageName("noImage.gif");
			else
				image.setImageName(thumbnailName);
			
			images.add(image);
		}
		
		
		
		model.addAttribute("reviews", branchReviews);
		model.addAttribute("pageMaker", pageMaker);
		model.addAttribute("images", images);

		return "mypage/branch_ReviewList";
	}

}