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
import com.bit.yes.model.paging.Criteria;
import com.bit.yes.model.paging.PageMaker;
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

	@RequestMapping(value = "/review_list", method = RequestMethod.GET)
	public String listReviewPage(@ModelAttribute("cri") SearchCriteria cri, Model model) throws Exception {

		logger.info("listReviewPage : " + cri);

//		model.addAttribute("list", service.listReviewCriteria(cri));

		model.addAttribute("list", service.listReviewSearchCri(cri));

		PageMaker pageMaker = new PageMaker();

		pageMaker.setCri(cri);

//		pageMaker.setTotalCount(service.countReviewPaging());

		pageMaker.setTotalCount(service.listReviewSearchCount(cri));

		model.addAttribute("pageMaker", pageMaker);

		return "review/review_list";
	}

	@RequestMapping(value = "/review_list/readReviewPage", method = RequestMethod.GET)
	public String readReviewPage(@RequestParam("reviewIndex") int reviewIndex, @ModelAttribute("cri") SearchCriteria cri,
			Model model) throws Exception {
		
		logger.info("into readReviewPage");
		
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

		return "review/review_detail";
	}
	
	@RequestMapping(value = "/review_edit", method = RequestMethod.GET, produces = "text/plain; charset=utf-8")
	public String updateReviewForm(@RequestParam("reviewIndex") int reviewIndex, @ModelAttribute("cri") SearchCriteria cri, Model model) throws SQLException {

		
		logger.info("updateReview(GET)");
		
		logger.info("reviewIndex : " + reviewIndex);
		
		logger.info("cri : " + cri );
		
		model.addAttribute("cri", cri);
		model.addAttribute("bean", service.selectPage(reviewIndex));

		return "review/review_edit";

	}

	@RequestMapping(value = "/review_edit", method = RequestMethod.POST, produces = "text/plain; charset=utf-8")
	public String updateReview(@RequestParam("reviewIndex") int reviewIndex, @ModelAttribute("cri") SearchCriteria cri, ReviewVo bean) throws SQLException, UnsupportedEncodingException {

		logger.info("updateReview(POST)");
		
//		logger.info("reviewIndex : " + reviewIndex);

		bean.setReviewIndex(reviewIndex);
		logger.info("bean" + bean);
		service.editOne(bean);
		logger.info("cri : " + cri);
		logger.info("page : " + cri.getPage());
		
		String keyword = URLEncoder.encode(cri.getKeyword(), "UTF-8");
		
		StringBuilder redirectedPage = new StringBuilder();
		redirectedPage.append("redirect:/review_list?page="+cri.getPage());
		redirectedPage.append("&perPageNum=" + cri.getPerPageNum());
		redirectedPage.append("&searchType=" + cri.getSearchType());
		redirectedPage.append("&keyword=" + keyword);
		
//		return "review/review_list";
//		return "redirect:/review_list";
		return redirectedPage.toString();
	}

	/*
	 * @RequestMapping(value = "/review_list", method = RequestMethod.GET) public
	 * String listAllReview(Model listModel, Model imageModel, HttpServletRequest
	 * request) throws Exception {
	 * 
	 * HttpSession session = request.getSession(); UserVo user = (UserVo)
	 * session.getAttribute("member"); Map<String, Object> params = new
	 * HashMap<String, Object>(); int currentPageNo = 1; int maxPost = 10;
	 * 
	 * String category = request.getParameter("category"); String keyword =
	 * request.getParameter("keyword");
	 * 
	 * if (category == null && keyword == null) {
	 * System.out.println("category and keyword is null"); }
	 * 
	 * if (request.getParameter("pages") != null) {
	 * System.out.println("pages is null"); currentPageNo =
	 * Integer.parseInt(request.getParameter("pages")); }
	 * 
	 * 
	 * logger.info("page : " + currentPageNo); Paging paging = new
	 * Paging(currentPageNo, maxPost);
	 * 
	 * int offset = (paging.getCurrentPageNo() - 1) * paging.getMaxPost();
	 * 
	 * List<ReviewVo> page = new ArrayList<ReviewVo>();
	 * 
	 * params.put("offset", offset); params.put("noOfRecords", paging.getMaxPost());
	 * 
	 * page = (List<ReviewVo>) service.listReview(params);
	 * 
	 * // List<ImageVo> images = service.listPageImage(); List<ImageVo> images = new
	 * ArrayList<>(); // for (int pageI = 0; pageI < page.size(); pageI++) { String
	 * thumbnailName = service.selectThumbnail(page.get(pageI).getReviewIndex());
	 * ImageVo thumbnailVo = new ImageVo(); thumbnailVo.setReviewIndex(pageI); if
	 * (thumbnailName == null) { thumbnailVo.setImageName("noImage.gif"); } else
	 * thumbnailVo.setImageName(thumbnailName); images.add(thumbnailVo); }
	 * 
	 * // for(int imagesI = 0; imagesI < images.size(); imagesI++) { // //
	 * System.out.println(images.get(imagesI).getReviewIndex()); //
	 * System.out.println(images.get(imagesI).getImageName()); //
	 * System.out.println("----------"); // }
	 * 
	 * listModel.addAttribute("page", page); // listModel.addAttribute("paging",
	 * paging); listModel.addAttribute("member", user);
	 * listModel.addAttribute("imageList", images);
	 * 
	 * return "review/review_list";
	 * 
	 * }
	 */

//	@RequestMapping(value = "/review_search", method = RequestMethod.GET)

	// both GET and POST method

	/*
	 * @RequestMapping(value = "/review_search") public String
	 * listSearchedReview(Model listModel, Model imageModel, HttpServletRequest
	 * request) throws Exception {
	 * 
	 * HttpSession session = request.getSession(); Map<String, Object> params = new
	 * HashMap<String, Object>(); int currentPageNo = 1; int maxPost = 10;
	 * 
	 * String category = request.getParameter("category"); String keyword =
	 * request.getParameter("keyword");
	 * 
	 * if (request.getParameter("pages") != null) { currentPageNo =
	 * Integer.parseInt(request.getParameter("pages")); }
	 * 
	 * if (category == null && keyword == null) { category = (String)
	 * session.getAttribute("category"); keyword = (String)
	 * session.getAttribute("keyword"); } else { session.setAttribute("category",
	 * category); session.setAttribute("keyword", keyword); }
	 * 
	 * Paging paging = new Paging(currentPageNo, maxPost);
	 * 
	 * // new paging-------------
	 * 
	 * 
	 * 
	 * 
	 * 
	 * int offset = (paging.getCurrentPageNo() - 1) * paging.getMaxPost();
	 * 
	 * List<ReviewVo> page = new ArrayList<ReviewVo>();
	 * 
	 * params.put("offset", offset); params.put("noOfRecords", paging.getMaxPost());
	 * params.put("keyword", keyword); params.put("category", category); page =
	 * (List<ReviewVo>) service.listReview(params);
	 * 
	 * int count = service.writeGetCount(params);
	 * 
	 * paging.setNumberOfRecords(count);
	 * 
	 * paging.makePaging();
	 * 
	 * listModel.addAttribute("page", page); listModel.addAttribute("paging",
	 * paging); service.listPageImage(imageModel, params);
	 * 
	 * return "review/review_list"; }
	 */



	@RequestMapping(value = "/review_write", method = RequestMethod.GET)
	public String createReviewForm(HttpServletRequest req, Model model) {
		String branchID = req.getParameter("branchID");
		logger.info("review_write(GET)");
		model.addAttribute("branchID", branchID);
		return "review/review_write";

	}

	@RequestMapping(value = "/review_write", method = RequestMethod.POST)
	public String createReview(ReviewVo reviewBean, MultipartHttpServletRequest mtfRequest,
			HttpServletRequest httpRequest) throws SQLException {
		logger.info("review_write(POST) :");
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

		service.reviewWrite(reviewBean);
		String genId, fileName, path;
		ImageVo imageBean = new ImageVo();
		MultipartFile mainFile = mtfRequest.getFile("mainImage");
		List<MultipartFile> subFiles = mtfRequest.getFiles("subImages");
		String originalFileName = mainFile.getOriginalFilename();
		genId = UUID.randomUUID().toString();

		String rootPath = mtfRequest.getSession().getServletContext().getRealPath("/");
		String attachPath = "resources\\review_imgs\\";

		path = rootPath + attachPath;

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
			if (subFiles.size() > 0) {
				for (MultipartFile subFile : subFiles) {
					originalFileName = subFile.getOriginalFilename();
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

		return "redirect:/review_list";
	}

	@RequestMapping(value = "/review_list/{index}", method = RequestMethod.GET)
//	public String showReviewDetail(@PathVariable int index, Model detailModel, Model mainModel, Model subModel)
	public String showReviewDetail(@PathVariable int index, Model detailModel) throws SQLException {

		detailIndex = index;

		ImageVo mainImage = service.reviewMainImage(index);
		List<ImageVo> subImages = service.reviewSubImage(index);
		if (mainImage == null)
			detailModel.addAttribute("numImages", subImages.size());
		else
			detailModel.addAttribute("numImages", subImages.size() + 1);

//		for(int subImagesI = 0; subImagesI < subImages.size(); subImagesI++) {
//			System.out.println("subImageName : " + subImages.get(subImagesI).getImageName());
//		}

		LikeVo likeBean = new LikeVo();

		likeBean.setReviewIndex(index);
//		System.out.println("reviewIndex : " + index);
		int numLike = service.reviewCountLike(likeBean);
//		System.out.println("numLike : " + numLike);
		detailModel.addAttribute("numLike", numLike);
		detailModel.addAttribute("bean", service.selectPage(index));
		detailModel.addAttribute("mainImage", mainImage);
		detailModel.addAttribute("subImages", subImages);

//		mainModel.addAttribute("mainImage", service.reviewMainImage(index));
//		subModel.addAttribute("subImages", service.reviewSubImage(index));
		return "review/review_detail";
	}

	@ResponseBody
	@RequestMapping(value = "/review_list/reviewDelete", method = RequestMethod.POST)
	public String deleteReview(HttpSession session, int reviewIndex) throws SQLException {

		UserVo loginedUser = (UserVo) session.getAttribute("member");
		String writingUser = service.selectPage(reviewIndex).getClientID();

		if (loginedUser == null) {
			return "1";
		} else if (!loginedUser.getId().equals(writingUser)) {
			return "2";
		} else {
			service.deleteOne(reviewIndex);
			service.deleteFile(reviewIndex);
			return "success";
		}
	}

	@ResponseBody
	@RequestMapping(value = "/review_list/addComment", method = RequestMethod.POST)
	public String createReviewComment(HttpServletRequest request, @ModelAttribute("commentVo") CommentVo commentVo,
			HttpSession session) throws SQLException {
		UserVo user = (UserVo) session.getAttribute("member");
		System.out.println("reviewIndex : " + commentVo.getReviewIndex());
		if (user == null)
			return "3";

		int reviewIndex = Integer.parseInt(request.getParameter("reviewIndex"));
		commentVo.setClientID(user.getId());
		service.reviewAddComment(commentVo);

		return "1";
	}

//	public String deleteReviewComment(@ModelAttribute("commentVo") CommentVo commentVo, HttpSession session)
//	public String deleteReviewComment(@RequestBody int reviewIndex, HttpSession session)
	@ResponseBody
	@RequestMapping(value = "/review_list/deleteComment", method = RequestMethod.POST)
	public String deleteReviewComment(@RequestBody CommentVo commentVo, HttpSession session) throws SQLException {
		UserVo user = (UserVo) session.getAttribute("member");
		commentVo.setClientID(user.getId());

		if (service.deleteComment(commentVo) == 1)
			return "success";
		else // return 0
			return "fail";

	}

	@ResponseBody
	@RequestMapping(value = "/review_list/commentList", method = RequestMethod.GET, produces = "application/json; charset=utf-8")
	public ResponseEntity<String> listReviewComment(@ModelAttribute("commentVo") CommentVo commentVo, Model model,
			HttpServletRequest request) throws Exception {

		HttpHeaders responseHeaders = new HttpHeaders();
		List<Map<String, Object>> commentList = new ArrayList<Map<String, Object>>();
		Map<String, Object> temp = new HashMap<String, Object>();

//		logger.info("page : " + request.getParameter("page"));

		// start paging-------------

		Criteria cri = new Criteria();

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
	@RequestMapping(value = "/review_list/clickLike", method = RequestMethod.POST)
//	public String updateReviewLike(@ModelAttribute("likeInfo") LikeVo likeVo, HttpSession session,
	public String updateReviewLike(LikeVo likeVo, HttpSession session, HttpServletRequest request) throws SQLException {

//		HttpSession session = request.getSession();
		System.out.println("like : " + likeVo.toString());

		UserVo user = (UserVo) session.getAttribute("member");
//		Map<String, Object> params = new HashMap<String, Object>();

		if (user == null)
			return "fail";

		LikeVo isExist;

//		params.put("checked", likeVo.isChecked());
//		params.put("bean", likeVo);

		likeVo.setWriter(user.getId());

		isExist = service.reviewCheckLike(likeVo);

		if (isExist == null)
			service.reviewNewLike(likeVo);
		else
			service.reviewDeleteLike(likeVo);

		return "success";
	}

	@ResponseBody
	@RequestMapping(value = "/review_list/reviewLike", produces = "application/json; charset=utf-8")
	public ResponseEntity<String> showReviewLikeCount(HttpSession session, Model likeModel) throws SQLException {

		UserVo user = (UserVo) session.getAttribute("member");
		LikeVo bean = new LikeVo();
		LikeVo checkBean = new LikeVo();
		String id;
		int likeCount;

		if (user != null) {
			id = user.getId();
			bean.setWriter(id);
		}
//		likeModel.addAttribute("userID", id);

		bean.setReviewIndex(detailIndex);

		likeCount = service.reviewCountLike(bean);

		checkBean = service.reviewCheckLike(bean);

		System.out.println("likeCount : " + likeCount);
		System.out.println("checkBean : " + checkBean);

		if (checkBean == null)
			bean.setChecked(false);
		else
			bean.setChecked(checkBean.isChecked());

		HttpHeaders responseHeaders = new HttpHeaders();
		List<Map<String, Object>> likeList = new ArrayList<>();
		Map<String, Object> temp = new HashMap<>();

		temp.put("likeCount", likeCount);
		temp.put("checked", bean.isChecked());

		likeList.add((Map<String, Object>) temp);

		JSONArray json = new JSONArray(likeList);

		return new ResponseEntity<String>(json.toString(), responseHeaders, HttpStatus.CREATED);
	}

	@ResponseBody
	@RequestMapping(value = "/review_list/editComment", method = RequestMethod.POST, produces = "application/json; charset=utf-8")
	public String updateReviewComment(CommentVo commentVo, HttpSession session) throws SQLException {

//		commentVo.setClientID(session.getId());

		service.editComment(commentVo);

		return "1";
	}

	@ResponseBody
	@RequestMapping(value = "/loadReviewScoreAvg", method = RequestMethod.POST)
	public double loadReviewScoreAvg(@RequestBody String branchId) {
		return service.loadReviewScoreAvg(branchId.substring(0, branchId.length() - 1));
	}

	// new paing---------------

	@RequestMapping(value = "/review_list/{reviewIndex}/listCri")
	public ResponseEntity<String> listAll(@PathVariable("reviewIndex") int reviewIndex, Criteria cri, Model model)
			throws Exception {

		logger.info("show list Page with Criteria...............");

//		model.addAttribute("list", service.listCommentCriteria(cri));

		CommentVo commentVo = new CommentVo();

		cri.setReviewIndex(reviewIndex);

		HttpHeaders responseHeaders = new HttpHeaders();
		List<Map<String, Object>> commentList = new ArrayList<Map<String, Object>>();
		Map<String, Object> temp = new HashMap<String, Object>();

//		List<CommentVo> selectList = service.reviewCommentList(commentVo.getReviewIndex());
		List<CommentVo> selectList = service.listCommentCriteria(cri);

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

		JSONArray json = new JSONArray(commentList);

		return new ResponseEntity<String>(json.toString(), responseHeaders, HttpStatus.CREATED);

	}

}