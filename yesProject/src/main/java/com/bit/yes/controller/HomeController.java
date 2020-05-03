package com.bit.yes.controller;

import java.sql.Date;
import java.sql.SQLException;
import java.text.DateFormat;
import java.util.List;
import java.util.Locale;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.bit.yes.model.entity.BranchVo;
import com.bit.yes.model.entity.ReserveListVo;
import com.bit.yes.model.entity.UserVo;
import com.bit.yes.model.paging.PageMaker;
import com.bit.yes.model.paging.SearchCriteria;
import com.bit.yes.service.BranchService;
import com.bit.yes.service.LoginService;
import com.bit.yes.service.ReserveListService;
import com.bit.yes.service.ReviewServiceImpl;

/**
 * Handles requests for the application home page.
 */
@Controller
public class HomeController {

	private static final Logger logger = LoggerFactory.getLogger(HomeController.class);

	@Autowired
	private BranchService branchService;

	@Autowired
	private ReviewServiceImpl reviewService;

	@Autowired
	private ReserveListService reserveListService;

	@Autowired
	private LoginService loginService;

	/**
	 * Simply selects the home view to render by returning its name.
	 * 
	 * @throws SQLException
	 */
	@RequestMapping(value = "/", method = RequestMethod.GET)
	public String home(HttpServletRequest request, Locale locale, Model model, HttpSession session) throws Exception {
//		public String home(Locale locale, Model model, @RequestParam("access_token") String accessToken) throws Exception {

		logger.info("Welcome home! The client locale is {}.", locale);

//		Date date = new Date();
		DateFormat dateFormat = DateFormat.getDateTimeInstance(DateFormat.LONG, DateFormat.LONG, locale);

//		String formattedDate = dateFormat.format(date);

//		model.addAttribute("serverTime", formattedDate);

		List<BranchVo> articleList = branchService.selectAll();
		model.addAttribute("alist", articleList);

		UserVo user = (UserVo) session.getAttribute("member");

		if (user != null)
			logger.info("id : " + user.getId());
		
		
		
		// reservation search test
		
		SearchCriteria cri = new SearchCriteria();
		
		cri.setPerPageNum(10);
		cri.setWriter("user00");
		cri.setPage(1);
//		cri.setSearchType("r");
//		cri.setKeyword("혼가츠");
		Date beginDate = Date.valueOf("2020-04-25");
		Date endDate = Date.valueOf("2020-04-30");
//		beginDate.valueOf("2020-04-25");
		cri.setSearchType("d");
		cri.setBeginDate(beginDate);
		cri.setEndDate(endDate);
		
		
		PageMaker pageMaker = new PageMaker();
		
		pageMaker.setCri(cri);
		
		pageMaker.setTotalCount(reserveListService.selectTotalReservation(cri));
		
		List<ReserveListVo> reservations = reserveListService.listPage(cri);
		
//		logger.info("reservations : "+ reservations);
		for(ReserveListVo reservation : reservations) {
			logger.info("reserve : " + reservation);
		}
		
		logger.info("totalCount : " + pageMaker.getTotalCount());
		

//		String accessToken = request.getParameter("access_token");
//		String tokenType = request.getParameter("token_type");
//		
//		logger.info("accessToken : " + accessToken);
//		logger.info("tokenType : " + tokenType);

//		String wotjsdlfkrh = "http://localhost:8080/#access_token=AAAAOQVkTmP_v1BgIPqWldBqo3uaM9AghHJxmZY6pe_cyTW7jTvMGO_WW9fE9oV1bjxPyvJ756ThfonmEtohYEGpSV4&state=b027aaab-c462-4983-a92d-c008dd106c62&token_type=bearer&expires_in=3600";
//		String wotjs8054 = "http://localhost:8080/#access_token=AAAAOQVkTmP_v1BgIPqWldBqo3uaM9AghHJxmZY6pe_cyTW7jTvMGO_WW9fE9oV1bjxPyvJ756ThfonmEtohYEGpSV4&state=b027aaab-c462-4983-a92d-c008dd106c62&token_type=bearer&expires_in=3600";
//		
//		logger.info("URL compare : " + wotjsdlfkrh.equals(wotjs8054));

		// deleteImage test

//		reviewService.deleteImages(278);

		// find ID test

//		Map<String, String> params = new HashMap<>();
//		
//		params.put("name", "유재선");
//		params.put("email", "you8054@naver.com");
//		params.put("birthDate", "1986-06-26");
//		
//		String id = loginService.findID(params);
//		
//		logger.info("findID : " + id);

		// login test

//		UserVo bean = new UserVo();
//		
//		bean.setId("user0001");
//		bean.setPassword("12321");
//		
//		java.sql.Date birthDate = new java.sql.Date(123456);
//		
//		bean.setBirthDate(birthDate);
//		bean.setacceptState("true");
//		bean.setEmail("dfafa@asdfas.com");
//		bean.setName("이름");
//		bean.setNickName("닉네임");
//		bean.setPhoneNum("010-1234-5678");
//		bean.setPwQuestion("질문");
//		bean.setRegistNum("245789456");
//		
//		
//		loginService.insertOne(bean);

//		SearchCriteria cri = new SearchCriteria();
//		
//		cri.setPage(1);
//		cri.setBranchID("user44");
//		
//		logger.info("cri : " + cri);
//		
//		List<ReviewVo> branchReviews = reviewService.listBranchReview(cri);
//		int totalCount = reviewService.countBranchReview(cri);
//		
//		logger.info("totalCount : " + totalCount);
//		
//		for(ReviewVo review : branchReviews) {
//			
//			logger.info("branchReview : " + review);
//			
//		}

		// reserve useState test

		// paging test

//		Criteria cri = new Criteria();
//		
//		cri.setPage(1);
//		
//		cri.setPerPageNum(20);
//		
//		cri.setReviewIndex(71);
//		
//		List<CommentVo> list = reviewService.listCommentCriteria(cri);
//		
//		for(CommentVo commentVo : list) {
//			
//			logger.info(commentVo.getCommentIndex() + " : " + commentVo.getComment());
//			
//			
//		}

		// paging test end

		// search paging test

//		SearchCriteria cri = new SearchCriteria();
//		cri.setPage(1);
//		cri.setSearchType("tcw");
//		cri.setKeyword("ddd");
//		
//		logger.info("==================");
//		
//		List<ReviewVo> list = reviewService.listReviewSearch(cri);
//		
//		for(ReviewVo vo : list) {
//			logger.info(vo.getReviewIndex() + " : " + vo.getTitle());
//		}
//		
//		logger.info("==================");
//		
//		logger.info("COUNT : " + reviewService.listReviewSearchCount(cri));

//		logger.info(articleList.toString());

//		System.out.println("before articleList.size()");
//		System.out.println("articleList.size() : " + articleList.size());
//		System.out.println("after articleList.size()");

//		for(int ai = 0; ai < articleList.size(); ai++) {
//			System.out.println("articleList : " + articleList.get(ai));
//		}

//		logger.info("articleList : ", articleList);

//		System.out.println("articleList's size : " + articleList.size());
		return "main";
	}
}
