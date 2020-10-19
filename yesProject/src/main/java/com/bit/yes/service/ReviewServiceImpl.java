package com.bit.yes.service;

import java.io.File;
import java.net.URLEncoder;
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
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.ui.Model;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;

import com.bit.yes.model.ReviewDAO;
import com.bit.yes.model.entity.CommentVo;
import com.bit.yes.model.entity.ImageVo;
import com.bit.yes.model.entity.LikeVo;
import com.bit.yes.model.entity.ReviewVo;
import com.bit.yes.model.entity.UserVo;
import com.bit.yes.model.paging.Criteria;
import com.bit.yes.model.paging.PageMaker;
import com.bit.yes.model.paging.SearchCriteria;

@Service
public class ReviewServiceImpl implements ReviewService {

	private final Logger logger = LoggerFactory.getLogger(ReviewServiceImpl.class);

	@Autowired
	private ReviewDAO reviewDAO;

	@Transactional
	@Override
	public String insertReview(ReviewVo review, int reserveIndex, String branchID, MultipartHttpServletRequest mtfRequest,
			HttpServletRequest httpRequest) throws Exception {

		int rating = Integer.parseInt(httpRequest.getParameter("rating"));
		review.setRating(rating);

		review.setContent(review.getContent().replace("\n", "<br>"));
		review.setBranchID(branchID);

		Map<String, Object> reserveStateMap = new HashMap<>();

		reserveStateMap.put("reserveIndex", reserveIndex);
		reserveStateMap.put("useState", "R");

		ImageVo imageBean = new ImageVo();
		imageBean.setReviewIndex(review.getReviewIndex());
		String rootPath = mtfRequest.getSession().getServletContext().getRealPath("/");
		String attachPath = "resources\\review_imgs\\";

		String savedPath = rootPath + attachPath;

		MultipartFile mainImage = mtfRequest.getFile("mainImage");
		List<MultipartFile> images = mtfRequest.getFiles("subImages");
		images.add(mainImage);

		reviewDAO.insertReview(review);

		String generatedID = UUID.randomUUID().toString();

		MultipartFile image = images.get(images.size() - 1);
		String imageName;

		// upload main image
		if (!images.get(images.size() - 1).getOriginalFilename().equals("")) {
			image = images.get(images.size() - 1);
			generatedID = UUID.randomUUID().toString();
			
			imageName = "m_" + generatedID + images.get(images.size() - 1).getOriginalFilename();
			image.transferTo(new File(savedPath + imageName));
			
			imageBean.setImageName(imageName);
			reviewDAO.insertReviewImage(imageBean);
		}
		
		// upload sub images

		if (!images.get(0).getOriginalFilename().equals("")) {
			for (int imagesI = 0; imagesI < images.size() - 1; imagesI++) {
				image = images.get(imagesI);

				generatedID = UUID.randomUUID().toString();
				imageName = generatedID + image.getOriginalFilename();
				imageBean.setImageName(imageName);
				reviewDAO.insertReviewImage(imageBean);
				image.transferTo(new File(savedPath + imageName));
			}
		}
		
		reviewDAO.updateUseState(reserveStateMap);

		return "redirect:/reviewList";

	}

	@Override
	public ResponseEntity<String> insertReviewComment(HttpSession session, CommentVo bean) throws Exception {

		ResponseEntity<String> entity = null;

		try {
			UserVo user = (UserVo) session.getAttribute("member");

			if (user == null)
				entity = new ResponseEntity<>("3", HttpStatus.OK);
			else {
				bean.setClientID(user.getId());
				reviewDAO.insertReviewComment(bean);

				entity = new ResponseEntity<>("1", HttpStatus.OK);
			}

		} catch (Exception e) {
			logger.info("comment key is duplicated");
			e.printStackTrace();
			entity = new ResponseEntity<>(HttpStatus.BAD_REQUEST);
		}

		return entity;
	}


	@Override
	public ResponseEntity<String> selectCommentList(HttpServletRequest request, CommentVo comment) throws Exception {
		
		HttpHeaders responseHeaders = new HttpHeaders();
		List<Map<String, Object>> commentList = new ArrayList<Map<String, Object>>();
		Map<String, Object> temp = new HashMap<String, Object>();

		// start paging-------------

		SearchCriteria cri = new SearchCriteria();

		int page = Integer.parseInt(request.getParameter("page"));

		cri.setPage(page);
		cri.setReviewIndex(comment.getReviewIndex());

		List<CommentVo> selectList = this.selectCommentCriteria(cri);

		PageMaker pageMaker = new PageMaker();

		pageMaker.setCri(cri);
		pageMaker.setTotalCount(this.selectCommentPagingCount(comment.getReviewIndex()));

		// end paging -------------

		if (selectList.size() > 0) {

			for (CommentVo bean : selectList) {

				temp = new HashMap<String, Object>();

				temp.put("commentIndex", bean.getCommentIndex());
				temp.put("comment", bean.getComment());
				temp.put("clientID", bean.getClientID());

				commentList.add(temp);

			}

		}

		Map<String, Object> pageMakerMap = new HashMap<>();

		pageMakerMap.put("pageMaker", pageMaker);

		commentList.add(pageMakerMap);

		JSONArray json = new JSONArray(commentList);

		return new ResponseEntity<String>(json.toString(), responseHeaders, HttpStatus.CREATED);
	}

	@Override
	public ResponseEntity<Map<String, Object>> selectReviewLikeCount(int detailIndex,
			HttpSession session) throws Exception {

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

		likeCount = reviewDAO.selectReviewLikeCount(bean);

		checkBean = reviewDAO.selectReviewLike(bean);

		if (checkBean == null)
			bean.setChecked(false);
		else
			bean.setChecked(checkBean.isChecked());

		Map<String, Object> params = new HashMap<>();

		params.put("likeCount", likeCount);
		params.put("checked", bean.isChecked());

		return new ResponseEntity<>(params, HttpStatus.CREATED);
	}

	@Override
	public void insertReviewImage(ImageVo image) throws Exception {

		reviewDAO.insertReviewImage(image);

	}

	@Override
	public ReviewVo selectOneReview(int reviewIndex) throws Exception {

		return reviewDAO.selectOneReview(reviewIndex);

	}

	@Override
	public String selectOneReview(int reviewIndex, Model model) throws Exception {

		ImageVo mainImage = this.selectReviewMainImgs(reviewIndex);
		List<ImageVo> subImages = this.selectReviewSubImgs(reviewIndex);

		if (mainImage == null)
			model.addAttribute("numImages", subImages.size());
		else
			model.addAttribute("numImages", subImages.size() + 1);

		LikeVo like = new LikeVo();

		like.setReviewIndex(reviewIndex);
		int numLike = reviewDAO.selectReviewLikeCount(like);

		ReviewVo review = reviewDAO.selectOneReview(reviewIndex);

		model.addAttribute("numLike", numLike);
		model.addAttribute("bean", review);
		model.addAttribute("mainImage", mainImage);
		model.addAttribute("subImages", subImages);

		return "review/reviewDetail";
	}

	@Override
	public String selectEditingReview(int reviewIndex, SearchCriteria cri, Model model) throws Exception {

		ReviewVo review = this.selectOneReview(reviewIndex);

		String replacedContent = review.getContent().replace("<br>", "\n");

		review.setContent(replacedContent);

		model.addAttribute("cri", cri);
		model.addAttribute("review", review);

		return "review/reviewEdit";
	}

	@Override
	public ImageVo selectReviewMainImgs(int reviewIndex) throws Exception {
		return reviewDAO.selectReviewMainImgs(reviewIndex);
	}

	public List<ImageVo> selectReviewSubImgs(int reviewIndex) throws Exception {
		return reviewDAO.selectReviewSubImgs(reviewIndex);
	}

	public ReviewVo selectEditPage(int reviewIndex) throws Exception {

		return reviewDAO.selectOneReview(reviewIndex);

	}

	@Transactional
	@Override
	public String deleteReview(HttpSession session, int reviewIndex) throws Exception {

		UserVo loginedUser = (UserVo) session.getAttribute("member");
		String writingUser = this.selectOneReview(reviewIndex).getClientID();

		if (loginedUser == null) {
			return "no login";
		} else if (!loginedUser.getId().equals(writingUser)) {
			return "no writing";
		} else {
			CommentVo comment = new CommentVo();
			comment.setReviewIndex(reviewIndex);

			reviewDAO.deleteReview(reviewIndex);
			reviewDAO.deleteReviewImage(reviewIndex);
			reviewDAO.deleteReviewComment(comment);

			return "success";
		}

	}

	@Transactional
	@Override
	public int deleteReview(int reviewIndex, Map<String, Integer> indexMap) throws Exception {

		reviewDAO.deleteReview(reviewIndex);
		reviewDAO.deleteReviewImage(reviewIndex);

		return reviewDAO.deleteReviewComment(indexMap);
	}

	@Transactional
	@Override
	public int deleteReview(int reviewIndex) throws Exception {

		reviewDAO.deleteReview(reviewIndex);
		reviewDAO.deleteReviewImage(reviewIndex);

		return reviewDAO.deleteReviewComment(reviewIndex);
	}

	@Override
	public int deleteOne(int reviewIndex) throws Exception {

		return reviewDAO.deleteReview(reviewIndex);
	}

	@Override
	public int deleteImages(int reviewIndex) throws Exception {
		return reviewDAO.deleteReviewImage(reviewIndex);
	}

	@Override
	public String deleteReviewComment(CommentVo comment) throws Exception {

		if (reviewDAO.deleteReviewComment(comment) == 1)
			return "success";
		else
			return "fail";
		
	}

	@Override
	public void updateReviewOnlyText(ReviewVo review) throws Exception {

		reviewDAO.updateReview(review);

	}

	@Override
	public String updateReview(int reviewIndex, SearchCriteria cri, ReviewVo review,
			MultipartHttpServletRequest mtfRequest) throws Exception {

		String keyword = URLEncoder.encode(cri.getKeyword(), "UTF-8");
		StringBuilder redirectedPage = new StringBuilder();
		redirectedPage.append("redirect:/reviewList?page=" + cri.getPage());
		redirectedPage.append("&perPageNum=" + cri.getPerPageNum());
		redirectedPage.append("&searchType=" + cri.getSearchType());
		redirectedPage.append("&keyword=" + keyword);

		MultipartFile mainFile = mtfRequest.getFile("mainImage");
		String originalFilename = mainFile.getOriginalFilename();
		review.setReviewIndex(reviewIndex);
		review.setContent(review.getContent().replace("\n", "<br>"));

		if (originalFilename.equals("")) {
			reviewDAO.updateReview(review);
			return redirectedPage.toString();
		} else {

			List<MultipartFile> images = mtfRequest.getFiles("subImages");
			images.add(mainFile);

			String rootPath = mtfRequest.getSession().getServletContext().getRealPath("/");
			String attachPath = "resources\\review_imgs\\";

			String savedPath = rootPath + attachPath;

			this.updateReviewIncludeFile(review, images, savedPath);
		}
		return redirectedPage.toString();
	}

	@Transactional
	@Override
	public int updateReviewIncludeFile(ReviewVo review, List<MultipartFile> images, String savedPath) throws Exception {

		ImageVo imageBean = new ImageVo();
		imageBean.setReviewIndex(review.getReviewIndex());

		reviewDAO.deleteReviewImage(review.getReviewIndex());
		reviewDAO.updateReview(review);

		String generatedID = UUID.randomUUID().toString();

		MultipartFile mainImage = images.get(images.size() - 1);

		String imageName = "m_" + generatedID + images.get(images.size() - 1).getOriginalFilename();
		imageBean.setImageName(imageName);
		reviewDAO.insertReviewImage(imageBean);
		mainImage.transferTo(new File(savedPath + imageName));

		if (!images.get(0).getOriginalFilename().equals("")) {
			for (int imagesI = 0; imagesI < images.size() - 1; imagesI++) {
				MultipartFile subImage = images.get(imagesI);
				generatedID = UUID.randomUUID().toString();
				imageName = generatedID + subImage.getOriginalFilename();
				imageBean.setImageName(imageName);
				reviewDAO.insertReviewImage(imageBean);
				subImage.transferTo(new File(savedPath + imageName));
			}
		}

		return 0;
	}

	@Override
	public void reviewClickLike(LikeVo like) throws Exception {

		reviewDAO.reviewClickLike(like);

	}



	@Override
	public void updateReviewLike(HashMap<String, Object> params) throws Exception {
		reviewDAO.updateReviewLike(params);
	}


	@Override
	public CommentVo selectOneComment(int commentIndex) throws Exception {
		return reviewDAO.selectOneComment(commentIndex);
	}

	@Override
	public String selectThumbnail(int reviewIndex) throws Exception {

		return reviewDAO.selectThumbnail(reviewIndex);

	}

//	--------------------paging


	@Override
	public ResponseEntity<String> updateReviewComment(CommentVo comment) {

		ResponseEntity<String> entity = null;

		try {
			reviewDAO.updateReviewComment(comment);
			entity = new ResponseEntity<String>("success", HttpStatus.OK);
		} catch (Exception e) {
			e.printStackTrace();
			entity = new ResponseEntity<String>(e.getMessage(), HttpStatus.BAD_REQUEST);
		}

		return entity;

	}

	@Override
	public String updateReviewLike(HttpSession session, LikeVo likeVo) throws Exception {

		UserVo user = (UserVo) session.getAttribute("member");

		if (user == null)
			return "fail";

		LikeVo isExist;

		likeVo.setClientID(user.getId());

		isExist = reviewDAO.selectReviewLike(likeVo);

		if (isExist == null)
			reviewDAO.reviewNewLike(likeVo);
		else
			reviewDAO.deleteReviewLike(likeVo);

		return "success";
	}

	@Override
	public double selectRating(String branchId) {
		return reviewDAO.selectRating(branchId);
	}

	// new paging----------------

	@Override
	public List<ReviewVo> selectReviewSearch(SearchCriteria cri) throws Exception {
		return reviewDAO.selectReviewSearch(cri);
	}

	@Override
	public int selectReviewSearchCount(SearchCriteria cri) throws Exception {
		return reviewDAO.selectReviewSearchCount(cri);
	}

	@Override
	public List<ReviewVo> selectBranchReview(SearchCriteria cri) throws Exception {
		return reviewDAO.selectBranchReview(cri);
	}

	@Override
	public int selectBranchReviewCount(SearchCriteria cri) throws Exception {
		return reviewDAO.selectBranchReviewCount(cri);
	}

	@Override
	public String listReviewSearchCri(SearchCriteria cri, Model model) throws Exception {

		List<ReviewVo> reviews = reviewDAO.selectReviewSearch(cri);

		List<ImageVo> images = new ArrayList<>();
		for (ReviewVo review : reviews) {

			String thumbnail = this.selectThumbnail(review.getReviewIndex());
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

		pageMaker.setTotalCount(this.selectReviewSearchCount(cri));

		model.addAttribute("reviews", reviews);
		model.addAttribute("images", images);
		model.addAttribute("pageMaker", pageMaker);
		
		reviewDAO.selectReviewSearch(cri);

		return "review/reviewList";
	}

	public List<ReviewVo> listReviewSearchCri(SearchCriteria cri) throws Exception {

		return reviewDAO.selectReviewSearch(cri);

	}
	

	@Override
	public int selectCriteriaCount() throws Exception {
		return reviewDAO.selectCriteriaCount();
	}

	@Override
	public List<CommentVo> selectCommentCriteria(Criteria cri) throws Exception {
		return reviewDAO.selectCommentCriteria(cri);
	}

	@Override
	public int selectCommentPagingCount(int reviewIndex) throws Exception {
		return reviewDAO.selectCommentPagingCount(reviewIndex);
	}

}
