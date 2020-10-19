package com.bit.yes.service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.springframework.http.ResponseEntity;
import org.springframework.ui.Model;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;

import com.bit.yes.model.entity.CommentVo;
import com.bit.yes.model.entity.ImageVo;
import com.bit.yes.model.entity.LikeVo;
import com.bit.yes.model.entity.ReviewVo;
import com.bit.yes.model.paging.Criteria;
import com.bit.yes.model.paging.SearchCriteria;

public interface ReviewService {

	public String insertReview(ReviewVo review, int reserveIndex, String branchID, MultipartHttpServletRequest mtfRequest,
			HttpServletRequest httpRequest) throws Exception;

	public ResponseEntity<String> insertReviewComment(HttpSession session, CommentVo comment) throws Exception;

//	public List<CommentVo> selectListComment(int reviewIndex) throws Exception;

	public ResponseEntity<String> selectCommentList(HttpServletRequest request, CommentVo comment) throws Exception;

	public ResponseEntity<Map<String, Object>> selectReviewLikeCount(int detailIndex,
			HttpSession session) throws Exception;

	public void insertReviewImage(ImageVo image) throws Exception;

	public String selectOneReview(int reviewIndex, Model model) throws Exception;

	public ReviewVo selectOneReview(int reviewIndex) throws Exception;

	public String selectEditingReview(int reviewIndex, SearchCriteria cri, Model model) throws Exception;

	public ImageVo selectReviewMainImgs(int reviewIndex) throws Exception;

	public List<ImageVo> selectReviewSubImgs(int reviewIndex) throws Exception;

	public ReviewVo selectEditPage(int reviewIndex) throws Exception;

	public String deleteReview(HttpSession session, int reviewIndex) throws Exception;

	public int deleteReview(int reviewIndex, Map<String, Integer> indexMap) throws Exception;

	public int deleteReview(int reviewIndex) throws Exception;

	public int deleteOne(int reviewIndex) throws Exception;

	public int deleteImages(int reviewIndex) throws Exception;

	public String deleteReviewComment(CommentVo comment) throws Exception;

	public void updateReviewOnlyText(ReviewVo review) throws Exception;

	public int updateReviewIncludeFile(ReviewVo review, List<MultipartFile> images, String savedPath) throws Exception;

	public String updateReview(int reviewIndex, SearchCriteria cri, ReviewVo review,
			MultipartHttpServletRequest mtfRequest) throws Exception;

	public void reviewClickLike(LikeVo like) throws Exception;

	public void updateReviewLike(HashMap<String, Object> params) throws Exception;

	public CommentVo selectOneComment(int commentIndex) throws Exception;

	public String selectThumbnail(int reviewIndex) throws Exception;

	public String updateReviewLike(HttpSession session, LikeVo likeVo) throws Exception;

	public ResponseEntity<String> updateReviewComment(CommentVo comment) throws Exception;

	public double selectRating(String branchId) throws Exception;

	// new paging----------------

	public List<ReviewVo> selectReviewSearch(SearchCriteria cri) throws Exception;

	public int selectReviewSearchCount(SearchCriteria cri) throws Exception;

	public List<ReviewVo> selectBranchReview(SearchCriteria cri) throws Exception;

	public int selectBranchReviewCount(SearchCriteria cri) throws Exception;

	public String listReviewSearchCri(SearchCriteria cri, Model model) throws Exception;

	public List<ReviewVo> listReviewSearchCri(SearchCriteria cri) throws Exception;

//	public List<ReviewVo> selectReviewCriteria(Criteria cri) throws Exception;

	public int selectCriteriaCount() throws Exception;

	public List<CommentVo> selectCommentCriteria(Criteria cri) throws Exception;

	public int selectCommentPagingCount(int reviewIndex) throws Exception;

}
