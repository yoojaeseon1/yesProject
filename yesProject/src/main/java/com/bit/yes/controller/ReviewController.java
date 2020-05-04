package com.bit.yes.controller;

import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.json.JSONArray;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
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
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;

import com.bit.yes.model.entity.CommentVo;
import com.bit.yes.model.entity.ImageVo;
import com.bit.yes.model.entity.LikeVo;
import com.bit.yes.model.entity.ReviewVo;
import com.bit.yes.model.entity.UserVo;
import com.bit.yes.model.paging.PageMaker;
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

		List<ReviewVo> reviews = service.listReviewSearchCri(cri);
		List<ImageVo> images = new ArrayList<>();
		for (ReviewVo review : reviews) {

			String thumbnail = service.selectThumbnail(review.getReviewIndex());
			ImageVo image = new ImageVo();
			image.setReviewIndex(review.getReviewIndex());

			if (thumbnail == null)
				image.setImageName("noImage.gif");
			else
				image.setImageName(thumbnail);

			images.add(image);

		}

		PageMaker pageMaker = new PageMaker();

		pageMaker.setCri(cri);

		pageMaker.setTotalCount(service.selectReviewSearchCount(cri));

		model.addAttribute("reviews", reviews);
		model.addAttribute("images", images);
		model.addAttribute("pageMaker", pageMaker);

		return "review/reviewList";
	}

	@RequestMapping(value = "/reviewList/readReviewPage", method = RequestMethod.GET)
	public String showReview(@RequestParam("reviewIndex") int reviewIndex,
			@ModelAttribute("cri") SearchCriteria cri, Model model, HttpServletRequest request) throws Exception {

		logger.info("into showReview(request)");

		detailIndex = reviewIndex;

		logger.info("detailIndex(listPage) : " + detailIndex);

		ImageVo mainImage = service.selectReviewMainImgs(reviewIndex);
		List<ImageVo> subImages = service.selectReviewSubImgs(reviewIndex);

		if (mainImage == null)
			model.addAttribute("numImages", subImages.size());
		else
			model.addAttribute("numImages", subImages.size() + 1);

		LikeVo likeBean = new LikeVo();

		likeBean.setReviewIndex(reviewIndex);
		int numLike = service.selectReviewLikeCount(likeBean);
		model.addAttribute("numLike", numLike);
		model.addAttribute("bean", service.selectOneReview(reviewIndex));
		model.addAttribute("mainImage", mainImage);
		model.addAttribute("subImages", subImages);

		return "review/reviewDetail";
	}

	@RequestMapping(value = "/reviewEdit", method = RequestMethod.GET, produces = "text/plain; charset=utf-8")
	public String updateReviewForm(@RequestParam("reviewIndex") int reviewIndex,
			@ModelAttribute("cri") SearchCriteria cri, Model model) throws Exception {

		logger.info("updateReview(GET)");

		ReviewVo review = service.selectOneReview(reviewIndex);

		
		String replacedContent = review.getContent().replace("<br>", "\n");
		
		
		review.setContent(replacedContent);
		
		model.addAttribute("cri", cri);
		model.addAttribute("review", review);

		return "review/reviewEdit";

	}

	@RequestMapping(value = "/reviewEdit", method = RequestMethod.POST)
	public String updateReview(@RequestParam("reviewIndex") int reviewIndex, @ModelAttribute("cri") SearchCriteria cri,
			ReviewVo bean, MultipartHttpServletRequest mtfRequest) throws Exception{

		logger.info("updateReview(PUT)");
		String keyword = URLEncoder.encode(cri.getKeyword(), "UTF-8");
		StringBuilder redirectedPage = new StringBuilder();
		redirectedPage.append("redirect:/reviewList?page=" + cri.getPage());
		redirectedPage.append("&perPageNum=" + cri.getPerPageNum());
		redirectedPage.append("&searchType=" + cri.getSearchType());
		redirectedPage.append("&keyword=" + keyword);

		
		MultipartFile mainFile = mtfRequest.getFile("mainImage");
		String originalFilename = mainFile.getOriginalFilename();
		bean.setReviewIndex(reviewIndex);
		bean.setContent(bean.getContent().replace("\n", "<br>"));
		
		
		if(originalFilename.equals("")) {
			service.updateReviewOnlyText(bean);
			return redirectedPage.toString();
		} else {
			
			List<MultipartFile> images = mtfRequest.getFiles("subImages");
			images.add(mainFile);
			
			String rootPath = mtfRequest.getSession().getServletContext().getRealPath("/");
			String attachPath = "resources\\review_imgs\\";
			
			String savedPath = rootPath + attachPath;
			
			service.updateReviewIncludeFile(bean, images, savedPath);
		}
		return redirectedPage.toString();
	}


	@RequestMapping(value = "/reviewWrite/{branchID}/{reserveIndex}", method = RequestMethod.GET)
	public String createReviewForm(HttpServletRequest request, Model model) {
		
		return "review/reviewWrite";

	}

	@RequestMapping(value = "/reviewWrite/{branchID}/{reserveIndex}", method = RequestMethod.POST)
	public String createReview(ReviewVo reviewBean, @PathVariable("reserveIndex") int reserveIndex,
			@PathVariable("branchID") String branchID, MultipartHttpServletRequest mtfRequest,
			HttpServletRequest httpRequest, Model model) throws Exception {
		
		
		logger.info("review_write(POST) :");
		logger.info("branchID : " + branchID);
		int rating = Integer.parseInt(httpRequest.getParameter("rating"));
		reviewBean.setRating(rating);
		

		reviewBean.setContent(reviewBean.getContent().replace("\n", "<br>"));
		reviewBean.setBranchID(branchID);

		Map<String, Object> reserveStateMap = new HashMap<>();

		reserveStateMap.put("reserveIndex", reserveIndex);
		reserveStateMap.put("useState", "R");

		ImageVo imageBean = new ImageVo();
		imageBean.setReviewIndex(reviewBean.getReviewIndex());
		String rootPath = mtfRequest.getSession().getServletContext().getRealPath("/");
		String attachPath = "resources\\review_imgs\\";
		
		
		String savedPath = rootPath + attachPath;
		
		MultipartFile mainImage = mtfRequest.getFile("mainImage");
		List<MultipartFile> images = mtfRequest.getFiles("subImages");
		images.add(mainImage);
		service.insertReview(reviewBean, reserveStateMap, images, savedPath);

		return "redirect:/reviewList";
	}

	@ResponseBody
	@RequestMapping(value = "/reviewList/reviewDelete", method = RequestMethod.DELETE)
	public String deleteReview(HttpSession session, int reviewIndex) throws Exception {
		
		logger.info("into deleteReview");
		UserVo loginedUser = (UserVo) session.getAttribute("member");
		String writingUser = service.selectOneReview(reviewIndex).getClientID();

		if (loginedUser == null) {
			return "no login";
		} else if (!loginedUser.getId().equals(writingUser)) {
			return "no writing";
		} else {
			
			CommentVo comment = new CommentVo();
			comment.setReviewIndex(reviewIndex);
			service.deleteReview(reviewIndex, comment);
			return "success";
		}
	}

	@ResponseBody
	@RequestMapping(value = "/reviewList/addComment", method = RequestMethod.POST)
	public ResponseEntity<String> createReviewComment(HttpServletRequest request, @ModelAttribute("commentVo") CommentVo commentVo,
			HttpSession session) throws Exception {
//			public String createReviewComment(HttpServletRequest request, @ModelAttribute("commentVo") CommentVo commentVo,
		
		ResponseEntity<String> entity = null;
		
//		commentVo.setCommentIndex(200);
		
		try {
			UserVo user = (UserVo) session.getAttribute("member");
			System.out.println("reviewIndex : " + commentVo.getReviewIndex());
			
			if (user == null) 
				entity = new ResponseEntity<>("3", HttpStatus.OK);
			else {
				commentVo.setClientID(user.getId());
				service.insertReviewComment(commentVo);
				
				entity = new ResponseEntity<>("1", HttpStatus.OK);
			}
			
			
		} catch(Exception e) {
			logger.info("comment key is duplicated");
			e.printStackTrace();
			entity = new ResponseEntity<>(HttpStatus.BAD_REQUEST);
		}
		
		return entity;


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
	public ResponseEntity<String> listReviewComment(@ModelAttribute("commentVo") CommentVo commentVo, Model model,
			HttpServletRequest request) throws Exception {

		HttpHeaders responseHeaders = new HttpHeaders();
		List<Map<String, Object>> commentList = new ArrayList<Map<String, Object>>();
		Map<String, Object> temp = new HashMap<String, Object>();

//		logger.info("page : " + request.getParameter("page"));

		// start paging-------------

		SearchCriteria cri = new SearchCriteria();

		int page = Integer.parseInt(request.getParameter("page"));

		cri.setPage(page); // 유동적으로 처리 해야됨
		cri.setReviewIndex(commentVo.getReviewIndex());

//		List<CommentVo> selectList = service.reviewCommentList(commentVo.getReviewIndex());
		List<CommentVo> selectList = service.selectCommentCriteria(cri);

		PageMaker pageMaker = new PageMaker();

		pageMaker.setCri(cri);
		pageMaker.setTotalCount(service.selectCommentPagingCount(commentVo.getReviewIndex()));

//		model.addAttribute("pageMaker", pageMaker);

		// end paging -------------

		if (selectList.size() > 0) {

			temp = new HashMap<String, Object>();

			temp.put("comment_idx", null);

			commentList.add(temp);

			for (CommentVo bean : selectList) {

				temp = new HashMap<String, Object>();

				temp.put("commentIndex", bean.getCommentIndex());
				temp.put("comment", bean.getComment());
				temp.put("clientID", bean.getClientID());

				commentList.add(temp);

			}

		}

		logger.info("prev : " + pageMaker.isPrev());
		logger.info("next : " + pageMaker.isNext());

		Map<String, Object> pageMakerMap = new HashMap<>();

		pageMakerMap.put("pageMaker", pageMaker);

		commentList.add(pageMakerMap);

		JSONArray json = new JSONArray(commentList);

		return new ResponseEntity<String>(json.toString(), responseHeaders, HttpStatus.CREATED);
	}

	@ResponseBody
	@RequestMapping(value = "/reviewList/clickLike", method = RequestMethod.POST)
	public String updateReviewLike(LikeVo likeVo, HttpSession session, HttpServletRequest request) throws Exception {

		UserVo user = (UserVo) session.getAttribute("member");

		if (user == null)
			return "fail";

		LikeVo isExist;

		likeVo.setClientID(user.getId());

		isExist = service.selectReviewLike(likeVo);


		if (isExist == null)
			service.reviewNewLike(likeVo);
		else
			service.deleteReviewLike(likeVo);

		return "success";
	}

	@ResponseBody
	@RequestMapping(value = "/reviewList/reviewLike", produces = "application/json; charset=utf-8")
	public ResponseEntity<Map<String,Object>> showReviewLikeCount(int reviewIndex, HttpSession session, Model likeModel)
//	public ResponseEntity<String> showReviewLikeCount(int reviewIndex, HttpSession session, Model likeModel)
			throws Exception {

		UserVo user = (UserVo) session.getAttribute("member");
		LikeVo bean = new LikeVo();
		LikeVo checkBean = new LikeVo();
		String id;
		int likeCount;

		if (user != null) {
			id = user.getId();
			bean.setClientID(id);
		}
		
		bean.setReviewIndex(detailIndex);

		likeCount = service.selectReviewLikeCount(bean);

		checkBean = service.selectReviewLike(bean);

		System.out.println("likeCount : " + likeCount);
		System.out.println("checkBean : " + checkBean);

		if (checkBean == null)
			bean.setChecked(false);
		else
			bean.setChecked(checkBean.isChecked());

		Map<String, Object> params = new HashMap<>();

		params.put("likeCount", likeCount);
		params.put("checked", bean.isChecked());


		return new ResponseEntity<>(params, HttpStatus.CREATED);
	}

	@ResponseBody
	@RequestMapping(value = "/reviewList/editComment", method = RequestMethod.POST, produces = "text/plain; charset=utf-8")
	public ResponseEntity<String> updateReviewComment(CommentVo commentVo, HttpSession session) throws Exception {

		
		ResponseEntity<String> entity = null;
		
		
		logger.info("commentVo : " + commentVo);
		
		try {
			logger.info("comment is updated");
			service.updateReviewComment(commentVo);
			entity = new ResponseEntity<String>("success", HttpStatus.OK);
		} catch(Exception e) {
			logger.info("comment is not updated");
			e.printStackTrace();
			entity = new ResponseEntity<String>(e.getMessage(), HttpStatus.BAD_REQUEST);
		}
		
		return entity;
	}

	@ResponseBody
	@RequestMapping(value = "/loadReviewScoreAvg", method = RequestMethod.POST)
	public double loadReviewScoreAvg(@RequestBody String branchId) throws Exception {
		return service.selectRating(branchId.substring(0, branchId.length() - 1));
	}
	
}