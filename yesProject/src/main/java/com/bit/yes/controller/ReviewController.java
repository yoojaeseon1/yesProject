package com.bit.yes.controller;

import java.io.File;
import java.io.IOException;
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
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;

import com.bit.yes.model.entity.CommentVo;
import com.bit.yes.model.entity.ImageVo;
import com.bit.yes.model.entity.LikeVo;
import com.bit.yes.model.entity.ReviewVo;
import com.bit.yes.model.entity.UserVo;
import com.bit.yes.model.paging.Paging;
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
	public String selectReviewList(Model listModel, Model imageModel, HttpServletRequest request) throws Exception {

		HttpSession session = request.getSession();
		UserVo user = (UserVo) session.getAttribute("member");
		Map<String, Object> params = new HashMap<String, Object>();
		int currentPageNo = 1;
		int maxPost = 10;

		String category = request.getParameter("category");
		String keyword = request.getParameter("keyword");

		if (category == null && keyword == null) {
			System.out.println("category and keyword is null");
		}

		if (request.getParameter("pages") != null) {
			System.out.println("pages is null");
			currentPageNo = Integer.parseInt(request.getParameter("pages"));
		}

		Paging paging = new Paging(currentPageNo, maxPost);

		int offset = (paging.getCurrentPageNo() - 1) * paging.getMaxPost();

		List<ReviewVo> page = new ArrayList<ReviewVo>();
//		List<ReviewVo> page;

		params.put("offset", offset);
		params.put("noOfRecords", paging.getMaxPost());
//		System.out.println("offset : " + offset);
//		System.out.println("noOfRecords : " + paging.getMaxPost());

		page = (List<ReviewVo>) service.writeList(params);
//		System.out.println("numContents : " + page.size());

		paging.setNumberOfRecords(service.writeGetCount());

		paging.makePaging();
//		List<ImageVo> images = service.listPageImage();
		List<ImageVo> images = new ArrayList<>();
//		
//		System.out.println("thumbnail");
		for (int pageI = 0; pageI < page.size(); pageI++) {
			String thumbnailName = service.selectThumbnail(page.get(pageI).getReviewIndex());
			ImageVo thumbnailVo = new ImageVo();
			thumbnailVo.setReviewIndex(pageI);
//			System.out.println(thumbnailName);
			if (thumbnailName == null) {
				thumbnailVo.setImageName("noImage.gif");
			} else
				thumbnailVo.setImageName(thumbnailName);
			images.add(thumbnailVo);
		}

//		for(int imagesI = 0; imagesI < images.size(); imagesI++) {
//			
//			System.out.println(images.get(imagesI).getReviewIndex());
//			System.out.println(images.get(imagesI).getImageName());
//			System.out.println("----------");
//		}

		listModel.addAttribute("page", page);
		listModel.addAttribute("paging", paging);
		listModel.addAttribute("member", user);
		listModel.addAttribute("imageList", images);

		return "review/review_list";

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
			System.out.println("pages is null");
			currentPageNo = Integer.parseInt(request.getParameter("pages"));
		}

		if (category == null && keyword == null) {
			category = (String) session.getAttribute("category");
			keyword = (String) session.getAttribute("keyword");
		} else {
			session.setAttribute("category", category);
			session.setAttribute("keyword", keyword);
		}

		System.out.println("current page(post) : " + currentPageNo);
		Paging paging = new Paging(currentPageNo, maxPost);

		int offset = (paging.getCurrentPageNo() - 1) * paging.getMaxPost();

		List<ReviewVo> page = new ArrayList<ReviewVo>();

		params.put("offset", offset);
		params.put("noOfRecords", paging.getMaxPost());
		params.put("keyword", keyword);
		params.put("category", category);
		page = (List<ReviewVo>) service.writeList(params);

		int count = service.writeGetCount(params);

		paging.setNumberOfRecords(count);

		paging.makePaging();

		listModel.addAttribute("page", page);
		listModel.addAttribute("paging", paging);
		service.listPageImage(imageModel, params);

		return "review/review_list";
	}

	@RequestMapping(value = "/review_edit/{index}", method = RequestMethod.GET)
	public String updateReviewForm(@PathVariable int index, Model model) throws SQLException {

		model.addAttribute("bean", service.selectPage(index));

		return "review/review_edit";

	}

	@RequestMapping(value = "/review_edit/{index}", method = RequestMethod.POST)
	public String updateReview(@PathVariable int index, ReviewVo bean, Model model) throws SQLException {

		bean.setReviewIndex(index);
		service.editOne(bean);
		return "redirect:../review/review_list";
	}

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
//		System.out.println("mainFileName : " + originalFileName);
//		System.out.println("There is no mainFile : " + (originalFileName.equals("")));
//		System.out.println("There is no mainFile : " + (originalFileName.equals(" ")));
		genId = UUID.randomUUID().toString();

		String rootPath = mtfRequest.getSession().getServletContext().getRealPath("/");
		String attachPath = "resources\\review_imgs\\";

		path = rootPath + attachPath;
//		System.out.println("path : " + path);
//		System.out.println("fileName : " + fileName);

		try {
//			if (originalFileName.equals("")) {
//				fileName = "noImage.gif";
//			} else
			if (!originalFileName.equals("")) {
//				System.out.println("There is thumbnail");
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

	@RequestMapping(value = "/review_list/{index}", method = RequestMethod.POST)
	public String deleteReview(@PathVariable int index) throws SQLException {

		service.deleteOne(index);
		service.deleteFile(index);

		return "redirect:/review_list";
	}

	@ResponseBody
	@RequestMapping(value = "/review_list/addComment", method = RequestMethod.POST)
	public String createReviewComment(HttpServletRequest request, @ModelAttribute("commentVo") CommentVo commentVo,
			HttpSession session) throws SQLException {
		UserVo user = (UserVo) session.getAttribute("member");
		int reviewIndex = Integer.parseInt(request.getParameter("reviewIndex"));
//		System.out.println("createReviewComment - reviewIndex : " + reviewIndex);
		commentVo.setWriter(user.getId());
		service.reviewAddComment(commentVo);

		return "success";
	}

//	public String deleteReviewComment(@ModelAttribute("commentVo") CommentVo commentVo, HttpSession session)
//	public String deleteReviewComment(@RequestBody int reviewIndex, HttpSession session)
	@ResponseBody
	@RequestMapping(value = "/review_list/deleteComment", method = RequestMethod.POST)
	public String deleteReviewComment(@RequestBody CommentVo commentVo, HttpSession session) throws SQLException {
		UserVo user = (UserVo) session.getAttribute("member");
		commentVo.setWriter(user.getId());

		if (service.deleteComment(commentVo) == 1)
			return "success";
		else // return 0
			return "fail";

	}

//	@RequestMapping(value = "/review_list/commentList", produces = "application/json; charset=utf-8")
	@ResponseBody
	@RequestMapping(value = "/review_list/commentList", method = RequestMethod.GET)
	public ResponseEntity<String> listReviewComment(@ModelAttribute("commentVo") CommentVo commentVo,
			HttpServletRequest request) throws SQLException {

		HttpHeaders responseHeaders = new HttpHeaders();
		List<Map<String, Object>> commentList = new ArrayList<Map<String, Object>>();
		Map<String, Object> temp = new HashMap<String, Object>();

		List<CommentVo> selectList = service.reviewCommentList(commentVo.getReviewIndex());

		if (selectList.size() > 0) {

			temp = new HashMap<String, Object>();

			temp.put("comment_idx", null);

			commentList.add(temp);

			for (CommentVo bean : selectList) {

				temp = new HashMap<String, Object>();

//				temp.put("comment_idx", bean.getCommentIndex());
				temp.put("commentIndex", bean.getCommentIndex());
				temp.put("comment", bean.getComment());
				temp.put("writer", bean.getWriter());

				commentList.add(temp);

			}

		}

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

		if (isExist == null) {
			System.out.println("not exist!!!");
			service.reviewNewLike(likeVo);

		} else {
			System.out.println("exist!!!");
			service.reviewDeleteLike(likeVo);
		}

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

		if (checkBean == null) {
//			System.out.println("bean is null!!");
			bean.setChecked(false);
		} else {
//			System.out.println("bean is not null!!");
			bean.setChecked(checkBean.isChecked());
		}

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
	@RequestMapping(value = "/review_list/editComment", method = RequestMethod.POST)
	public String updateReviewComment(@ModelAttribute("commentVo") CommentVo commentVo, HttpSession session)
			throws SQLException {

		commentVo.setWriter(session.getId());

		service.editComment(commentVo);

		return "success";
	}

	@ResponseBody
	@RequestMapping(value = "/loadReviewScoreAvg", method = RequestMethod.POST)
	public double loadReviewScoreAvg(@RequestBody String branchId) {
		return service.loadReviewScoreAvg(branchId.substring(0, branchId.length() - 1));
	}
}