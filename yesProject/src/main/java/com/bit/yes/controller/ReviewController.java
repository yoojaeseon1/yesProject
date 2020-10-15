package com.bit.yes.controller;

import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartHttpServletRequest;

import com.bit.yes.model.entity.CommentVo;
import com.bit.yes.model.entity.LikeVo;
import com.bit.yes.model.entity.ReviewVo;
import com.bit.yes.model.paging.SearchCriteria;
import com.bit.yes.service.ReviewService;

@Controller
public class ReviewController {

	@Autowired
	private ReviewService service;

	private int detailIndex;

	private final Logger logger = LoggerFactory.getLogger(ReviewController.class);

	@RequestMapping(value = "/reviewList", method = RequestMethod.GET)
	public String listReview(@ModelAttribute("cri") SearchCriteria cri, Model model) throws Exception {

		service.listReviewSearchCri(cri, model);

		return "review/reviewList";
	}

	@RequestMapping(value = "/reviewList/readReviewPage", method = RequestMethod.GET)
	public String showReview(@RequestParam("reviewIndex") int reviewIndex, Model model) throws Exception {

		detailIndex = reviewIndex;

		service.selectOneReview(reviewIndex, model);

		return "review/reviewDetail";
	}

	@RequestMapping(value = "/reviewEdit", method = RequestMethod.GET, produces = "text/plain; charset=utf-8")
	public String updateReviewForm(@RequestParam("reviewIndex") int reviewIndex,
			@ModelAttribute("cri") SearchCriteria cri, Model model) throws Exception {

		service.selectEditingReview(reviewIndex, cri, model);

		return "review/reviewEdit";

	}

	@RequestMapping(value = "/reviewEdit", method = RequestMethod.POST)
	public String updateReview(@RequestParam("reviewIndex") int reviewIndex, @ModelAttribute("cri") SearchCriteria cri,
			ReviewVo bean, MultipartHttpServletRequest mtfRequest) throws Exception {

		return service.updateReview(reviewIndex, cri, bean, mtfRequest);

	}

	@RequestMapping(value = "/reviewWrite/{branchID}/{reserveIndex}", method = RequestMethod.GET)
	public String createReviewForm(HttpServletRequest request, Model model) {

		return "review/reviewWrite";

	}

	@RequestMapping(value = "/reviewWrite/{branchID}/{reserveIndex}", method = RequestMethod.POST)
	public String createReview(ReviewVo reviewBean, @PathVariable("reserveIndex") int reserveIndex,
			@PathVariable("branchID") String branchID, MultipartHttpServletRequest mtfRequest,
			HttpServletRequest httpRequest) throws Exception {
		
		

		service.insertReview(reviewBean, reserveIndex, branchID,  mtfRequest,  httpRequest);

		return "redirect:/reviewList";
	}

	@ResponseBody
	@RequestMapping(value = "/reviewList/reviewDelete", method = RequestMethod.DELETE)
	public String deleteReview(HttpSession session, int reviewIndex) throws Exception {

		return service.deleteReview(session, reviewIndex);
	}

	@ResponseBody
	@RequestMapping(value = "/reviewList/addComment", method = RequestMethod.POST)
	public ResponseEntity<String> createReviewComment(HttpServletRequest request,
			@ModelAttribute("commentVo") CommentVo commentVo, HttpSession session) throws Exception {

		return service.insertReviewComment(session, commentVo);

	}

	@ResponseBody
	@RequestMapping(value = "/reviewList/deleteComment", method = RequestMethod.POST)
	public String deleteReviewComment(@RequestBody CommentVo commentVo, HttpSession session) throws Exception {

		logger.info("into deleteReviewComment");
		logger.info("commentVo : " + commentVo);

		if (service.deleteComment(commentVo) == 1)
			return "success";
		else // return 0
			return "fail";

	}

	@ResponseBody
	@RequestMapping(value = "/reviewList/commentList", method = RequestMethod.GET, produces = "application/json; charset=utf-8")
	public ResponseEntity<String> listReviewComment(@ModelAttribute("commentVo") CommentVo commentVo,
			HttpServletRequest request) throws Exception {

		return service.selectCommentList(request, commentVo);

	}

	@ResponseBody
	@RequestMapping(value = "/reviewList/clickLike", method = RequestMethod.POST)
	public String updateReviewLike(LikeVo likeVo, HttpSession session) throws Exception {

		return service.updateReviewLike(session, likeVo);

	}

	@ResponseBody
	@RequestMapping(value = "/reviewList/reviewLike", produces = "application/json; charset=utf-8")
	public ResponseEntity<Map<String, Object>> showReviewLikeCount(int reviewIndex, HttpSession session)
			throws Exception {

		return service.selectReviewLikeCount(reviewIndex, detailIndex, session);

	}

	@ResponseBody
	@RequestMapping(value = "/reviewList/editComment", method = RequestMethod.POST, produces = "text/plain; charset=utf-8")
	public ResponseEntity<String> updateReviewComment(CommentVo commentVo, HttpSession session) throws Exception {

		return service.updateReviewComment(commentVo);

	}

	@ResponseBody
	@RequestMapping(value = "/loadReviewScoreAvg", method = RequestMethod.POST)
	public double loadReviewScoreAvg(@RequestBody String branchId) throws Exception {

		return service.selectRating(branchId.substring(0, branchId.length() - 1));
	}

}