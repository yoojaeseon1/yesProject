package com.bit.yes.controller;

import java.io.File;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;

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
import com.bit.yes.model.paging.Paging;
import com.bit.yes.model.paging.SearchCriteria;
import com.bit.yes.service.ReviewService;

@Controller
public class ReviewController {

	@Autowired
	private ReviewService service;

//	private Date today = new Date();
//	private SimpleDateFormat sdf = new SimpleDateFormat("");
	private int detailIndex;

	private final Logger logger = LoggerFactory.getLogger(ReviewController.class);

	@RequestMapping(value = "/reviewList", method = RequestMethod.GET)
	public String listReviewPage(@ModelAttribute("cri") SearchCriteria cri, Model model) throws Exception {

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

		pageMaker.setTotalCount(service.listReviewSearchCount(cri));

		model.addAttribute("reviews", reviews);
		model.addAttribute("images", images);
		model.addAttribute("pageMaker", pageMaker);

		return "review/reviewList";
	}

	@RequestMapping(value = "/reviewList/readReviewPage", method = RequestMethod.GET)
	public String readReviewPage(@RequestParam("reviewIndex") int reviewIndex,
			@ModelAttribute("cri") SearchCriteria cri, Model model, HttpServletRequest request) throws Exception {

		logger.info("into readReviewPage");

		detailIndex = reviewIndex;

		logger.info("detailIndex(listPage) : " + detailIndex);

		ImageVo mainImage = service.reviewMainImage(reviewIndex);
		List<ImageVo> subImages = service.reviewSubImage(reviewIndex);

		if (mainImage == null)
			model.addAttribute("numImages", subImages.size());
		else
			model.addAttribute("numImages", subImages.size() + 1);

		LikeVo likeBean = new LikeVo();

		likeBean.setReviewIndex(reviewIndex);
//		System.out.println("reviewIndex : " + index);
		int numLike = service.reviewCountLike(likeBean);
//		System.out.println("numLike : " + numLike);
		model.addAttribute("numLike", numLike);
		model.addAttribute("bean", service.selectPage(reviewIndex));
		model.addAttribute("mainImage", mainImage);
		model.addAttribute("subImages", subImages);
//		model.addAttribute(service.selectPage(reviewIndex));

		return "review/reviewDetail";
	}

	@RequestMapping(value = "/reviewEdit", method = RequestMethod.GET, produces = "text/plain; charset=utf-8")
	public String updateReviewForm(@RequestParam("reviewIndex") int reviewIndex,
			@ModelAttribute("cri") SearchCriteria cri, Model model) throws SQLException {

		logger.info("updateReview(GET)");
//		
//		logger.info("reviewIndex : " + reviewIndex);
//		
//		logger.info("cri : " + cri );

		ReviewVo review = service.selectPage(reviewIndex);
		
//		logger.info("content : " + review.getContent());
		
//		String content = review.getContent();
		
		String replacedContent = review.getContent().replace("<br>", "\n");
		
//		logger.info("replaced content : " + replacedContent);
		
		review.setContent(replacedContent);
		
		model.addAttribute("cri", cri);
		model.addAttribute("review", review);

		return "review/reviewEdit";

	}

	@RequestMapping(value = "/reviewEdit", method = RequestMethod.POST, produces = "text/plain; charset=utf-8")
	public String updateReview(@RequestParam("reviewIndex") int reviewIndex, @ModelAttribute("cri") SearchCriteria cri,
			ReviewVo bean) throws SQLException, UnsupportedEncodingException {

		logger.info("updateReview(POST)");

//		logger.info("reviewIndex : " + reviewIndex);

		bean.setReviewIndex(reviewIndex);
		logger.info("bean : " + bean);
		
		String content = bean.getContent();
		for(int contentI = 0; contentI < content.length(); contentI++) {
			
			if(content.charAt(contentI) == '\n')
				logger.info("nextLine : " + (int)content.charAt(contentI-2));
		}
		
		bean.setContent(bean.getContent().replace("\n", "<br>"));
		
		service.editOne(bean);
		logger.info("cri : " + cri);
		logger.info("page : " + cri.getPage());
		
		

		String keyword = URLEncoder.encode(cri.getKeyword(), "UTF-8");

		StringBuilder redirectedPage = new StringBuilder();
		redirectedPage.append("redirect:/reviewList?page=" + cri.getPage());
		redirectedPage.append("&perPageNum=" + cri.getPerPageNum());
		redirectedPage.append("&searchType=" + cri.getSearchType());
		redirectedPage.append("&keyword=" + keyword);

//		return "review/review_list";
//		return "redirect:/review_list";
		return redirectedPage.toString();
	}

//	@RequestMapping(value = "/review_search", method = RequestMethod.GET)

	// both GET and POST method

	@RequestMapping(value = "/review_search")
	public String listSearchedReview(Model listModel, Model imageModel, HttpServletRequest request) throws Exception {

		HttpSession session = request.getSession();
		Map<String, Object> params = new HashMap<String, Object>();
		int currentPageNo = 1;
		int maxPost = 10;

		String category = request.getParameter("category");
		String keyword = request.getParameter("keyword");

		if (request.getParameter("pages") != null) {
			currentPageNo = Integer.parseInt(request.getParameter("pages"));
		}

		if (category == null && keyword == null) {
			category = (String) session.getAttribute("category");
			keyword = (String) session.getAttribute("keyword");
		} else {
			session.setAttribute("category", category);
			session.setAttribute("keyword", keyword);
		}

		Paging paging = new Paging(currentPageNo, maxPost);

		// new paging-------------

		int offset = (paging.getCurrentPageNo() - 1) * paging.getMaxPost();

		List<ReviewVo> page = new ArrayList<ReviewVo>();

		params.put("offset", offset);
		params.put("noOfRecords", paging.getMaxPost());
		params.put("keyword", keyword);
		params.put("category", category);
		page = (List<ReviewVo>) service.listReview(params);

		int count = service.writeGetCount(params);

		paging.setNumberOfRecords(count);

		paging.makePaging();

		listModel.addAttribute("page", page);
		listModel.addAttribute("paging", paging);
		service.listPageImage(imageModel, params);

		return "review/reviewList";

	}

	@RequestMapping(value = "/reviewWrite/{branchID}/{reserveIndex}", method = RequestMethod.GET)
	public String createReviewForm(HttpServletRequest request, Model model) {
		
		return "review/reviewWrite";

	}

	@RequestMapping(value = "/reviewWrite/{branchID}/{reserveIndex}", method = RequestMethod.POST)
	public String createReview(ReviewVo reviewBean, @PathVariable("reserveIndex") int reserveIndex,
			@PathVariable("branchID") String branchID, MultipartHttpServletRequest mtfRequest,
			HttpServletRequest httpRequest, Model model) throws SQLException {
		
		
		logger.info("review_write(POST) :");
		logger.info("branchID : " + branchID);
		int rating = Integer.parseInt(httpRequest.getParameter("rating"));
		reviewBean.setRating(rating);
		System.out.println("title : " + reviewBean.getTitle());
		String content = reviewBean.getContent();
		String replacedContent = "";
		int startIdx = 0;
		for (int i = 0; i < content.length(); i++) {
			if (content.charAt(i) == '\n') {
				replacedContent += content.substring(startIdx, i);
				replacedContent += "<br>";
				startIdx = i + 1;
			}
		}
		replacedContent += content.substring(startIdx, content.length());

		reviewBean.setContent(replacedContent);
		reviewBean.setBranchID(branchID);

		Map<String, Object> reserveStateMap = new HashMap<>();

		reserveStateMap.put("reserveIndex", reserveIndex);
		reserveStateMap.put("useState", "R");

		String genId, fileName, path;
		ImageVo imageBean = new ImageVo();
		MultipartFile mainFile = mtfRequest.getFile("mainImage");
		List<MultipartFile> subFiles = mtfRequest.getFiles("subImages");
		String originalFileName = mainFile.getOriginalFilename();
		service.reviewWrite(reviewBean, reserveStateMap);
		genId = UUID.randomUUID().toString();

		String rootPath = mtfRequest.getSession().getServletContext().getRealPath("/");
		String attachPath = "resources\\review_imgs\\";

		logger.info("rootPath : " + rootPath);

		path = rootPath + attachPath;

		logger.info("mainFile : " + originalFileName);
		try {
//			if (originalFileName.equals("")) {
//				fileName = "noImage.gif";
//			} else 
			if (!originalFileName.equals("")) {
				fileName = "m_" + genId + originalFileName;
				imageBean.setImageName(fileName);
				mainFile.transferTo(new File(path + fileName));
				service.reviewImgUpload(imageBean);
			}
			if (subFiles.size() > 0 && !subFiles.get(0).getOriginalFilename().equals("")) {
//				logger.info("subFile is null : " + (subFiles.get(0).getOriginalFilename().equals("")));
//				logger.info("subFile.size() : " + subFiles.size());
				for (MultipartFile subFile : subFiles) {
					originalFileName = subFile.getOriginalFilename();
					logger.info("subFile : " + originalFileName);
					genId = UUID.randomUUID().toString();
					fileName = genId + originalFileName;
					imageBean.setImageName(fileName);
					subFile.transferTo(new File(path + fileName));
					service.reviewImgUpload(imageBean);
				}
			}

		} catch (IllegalStateException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}

		return "redirect:/reviewList";
	}

	@RequestMapping(value = "/reviewList/{reviewIndex}", method = RequestMethod.GET)
//	public String showReviewDetail(@PathVariable int index, Model detailModel, Model mainModel, Model subModel)
	public String showReviewDetail(@PathVariable int reviewIndex, Model detailModel) throws SQLException {

		detailIndex = reviewIndex;

		ImageVo mainImage = service.reviewMainImage(reviewIndex);
		List<ImageVo> subImages = service.reviewSubImage(reviewIndex);
		if (mainImage == null)
			detailModel.addAttribute("numImages", subImages.size());
		else
			detailModel.addAttribute("numImages", subImages.size() + 1);

//		for(int subImagesI = 0; subImagesI < subImages.size(); subImagesI++) {
//			System.out.println("subImageName : " + subImages.get(subImagesI).getImageName());
//		}

		LikeVo likeBean = new LikeVo();

		likeBean.setReviewIndex(reviewIndex);
//		System.out.println("reviewIndex : " + index);
		int numLike = service.reviewCountLike(likeBean);
//		System.out.println("numLike : " + numLike);
		detailModel.addAttribute("numLike", numLike);
		detailModel.addAttribute("bean", service.selectPage(reviewIndex));
		detailModel.addAttribute("mainImage", mainImage);
		detailModel.addAttribute("subImages", subImages);

//		mainModel.addAttribute("mainImage", service.reviewMainImage(index));
//		subModel.addAttribute("subImages", service.reviewSubImage(index));
		return "review/reviewDetail";
	}

	@ResponseBody
	@RequestMapping(value = "/reviewList/reviewDelete", method = RequestMethod.POST)
	public String deleteReview(HttpSession session, int reviewIndex) throws Exception {

		UserVo loginedUser = (UserVo) session.getAttribute("member");
		String writingUser = service.selectPage(reviewIndex).getClientID();

		if (loginedUser == null) {
			return "no login";
		} else if (!loginedUser.getId().equals(writingUser)) {
			return "no writing";
		} else {
			
			Map<String, Integer> reviewIndexMap = new HashMap<>();
			
			reviewIndexMap.put("reviewIndex", reviewIndex);
			
			CommentVo comment = new CommentVo();
			comment.setReviewIndex(reviewIndex);
			service.deleteReview(reviewIndex, reviewIndexMap);
			return "success";
		}
	}

	@ResponseBody
	@RequestMapping(value = "/reviewList/addComment", method = RequestMethod.POST)
	public String createReviewComment(HttpServletRequest request, @ModelAttribute("commentVo") CommentVo commentVo,
			HttpSession session) throws SQLException {
		UserVo user = (UserVo) session.getAttribute("member");
		System.out.println("reviewIndex : " + commentVo.getReviewIndex());
		if (user == null)
			return "3";

//		int reviewIndex = Integer.parseInt(request.getParameter("reviewIndex"));
		commentVo.setClientID(user.getId());
		service.reviewAddComment(commentVo);

		return "1";
	}

//	public String deleteReviewComment(@ModelAttribute("commentVo") CommentVo commentVo, HttpSession session)
//	public String deleteReviewComment(@RequestBody int reviewIndex, HttpSession session)
	@ResponseBody
	@RequestMapping(value = "/reviewList/deleteComment", method = RequestMethod.POST)
	public String deleteReviewComment(@RequestBody CommentVo commentVo, HttpSession session) throws SQLException {
		
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
		List<CommentVo> selectList = service.listCommentCriteria(cri);

		PageMaker pageMaker = new PageMaker();

		pageMaker.setCri(cri);
		pageMaker.setTotalCount(service.countCommentPaging(commentVo.getReviewIndex()));

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
	public String updateReviewLike(LikeVo likeVo, HttpSession session, HttpServletRequest request) throws SQLException {

		UserVo user = (UserVo) session.getAttribute("member");

		if (user == null)
			return "fail";

		LikeVo isExist;

		likeVo.setClientID(user.getId());

		isExist = service.reviewCheckLike(likeVo);


		if (isExist == null)
			service.reviewNewLike(likeVo);
		else
			service.reviewDeleteLike(likeVo);

		return "success";
	}

	@ResponseBody
	@RequestMapping(value = "/reviewList/reviewLike", produces = "application/json; charset=utf-8")
	public ResponseEntity<Map<String,Object>> showReviewLikeCount(int reviewIndex, HttpSession session, Model likeModel)
//	public ResponseEntity<String> showReviewLikeCount(int reviewIndex, HttpSession session, Model likeModel)
			throws SQLException {

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

		likeCount = service.reviewCountLike(bean);

		checkBean = service.reviewCheckLike(bean);

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
	@RequestMapping(value = "/reviewList/editComment", method = RequestMethod.POST, produces = "application/json; charset=utf-8")
	public String updateReviewComment(CommentVo commentVo, HttpSession session) throws Exception {

//		commentVo.setClientID(session.getId());

		service.editComment(commentVo);

		return "success";
	}

	@ResponseBody
	@RequestMapping(value = "/loadReviewScoreAvg", method = RequestMethod.POST)
	public double loadReviewScoreAvg(@RequestBody String branchId) throws Exception {
		return service.loadReviewScoreAvg(branchId.substring(0, branchId.length() - 1));
	}

//	// new paing---------------
//
//	@RequestMapping(value = "/review_list/{reviewIndex}/listCri")
//	public ResponseEntity<String> listAll(@PathVariable("reviewIndex") int reviewIndex, Criteria cri, Model model)
//			throws Exception {
//
//		logger.info("show list Page with Criteria...............");
//
////		model.addAttribute("list", service.listCommentCriteria(cri));
//
//		CommentVo commentVo = new CommentVo();
//
//		cri.setReviewIndex(reviewIndex);
//
//		HttpHeaders responseHeaders = new HttpHeaders();
//		List<Map<String, Object>> commentList = new ArrayList<Map<String, Object>>();
//		Map<String, Object> temp = new HashMap<String, Object>();
//
////		List<CommentVo> selectList = service.reviewCommentList(commentVo.getReviewIndex());
//		List<CommentVo> selectList = service.listCommentCriteria(cri);
//
//		if (selectList.size() > 0) {
//
//			temp = new HashMap<String, Object>();
//
//			temp.put("comment_idx", null);
//
//			commentList.add(temp);
//
//			for (CommentVo bean : selectList) {
//
//				temp = new HashMap<String, Object>();
//
//				temp.put("commentIndex", bean.getCommentIndex());
//				temp.put("comment", bean.getComment());
//				temp.put("clientID", bean.getClientID());
//
//				commentList.add(temp);
//
//			}
//
//		}
//
//		JSONArray json = new JSONArray(commentList);
//
//		return new ResponseEntity<String>(json.toString(), responseHeaders, HttpStatus.CREATED);
//
//	}

}