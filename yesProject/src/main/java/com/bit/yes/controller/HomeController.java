package com.bit.yes.controller;

import java.sql.SQLException;
import java.text.DateFormat;
import java.util.Date;
import java.util.List;
import java.util.Locale;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.bit.yes.model.entity.BranchVo;
import com.bit.yes.service.BranchService;
import com.bit.yes.service.ReserveListService;
import com.bit.yes.service.ReviewService;

/**
 * Handles requests for the application home page.
 */
@Controller
public class HomeController {
	
	private static final Logger logger = LoggerFactory.getLogger(HomeController.class);
	
	@Autowired
	private BranchService branchService;
	
	@Autowired
	private ReviewService reviewService;
	
	@Autowired
	private ReserveListService reserveListService;
	
	
	/**
	 * Simply selects the home view to render by returning its name.
	 * @throws SQLException 
	 */
	@RequestMapping(value = "/", method = RequestMethod.GET)
	public String home(Locale locale, Model model) throws Exception {
		
		logger.info("Welcome home! The client locale is {}.", locale);
		
		
		Date date = new Date();
		DateFormat dateFormat = DateFormat.getDateTimeInstance(DateFormat.LONG, DateFormat.LONG, locale);
		
		String formattedDate = dateFormat.format(date);
		
		model.addAttribute("serverTime", formattedDate );
		
		List<BranchVo> articleList = branchService.selectAll();
		model.addAttribute("alist", articleList);
		
		
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
