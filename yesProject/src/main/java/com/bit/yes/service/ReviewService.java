package com.bit.yes.service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.ui.Model;
import org.springframework.web.multipart.MultipartFile;

import com.bit.yes.model.entity.CommentVo;
import com.bit.yes.model.entity.ImageVo;
import com.bit.yes.model.entity.LikeVo;
import com.bit.yes.model.entity.ReviewVo;
import com.bit.yes.model.paging.Criteria;
import com.bit.yes.model.paging.SearchCriteria;

public interface ReviewService {
	
	
	public int insertReview(ReviewVo bean, Map<String, Object> reserveStateMap, List<MultipartFile> images, String savedPath) throws Exception;
	
	public void insertReviewComment(CommentVo bean) throws Exception;
	
	public List<CommentVo> selectListComment(int reviewIndex) throws Exception;
	
	public void insertReviewImage(ImageVo bean) throws Exception;
	
	public ReviewVo selectOneReview(int reviewIndex) throws Exception;
	
	public ImageVo selectReviewMainImgs(int reviewIndex) throws Exception;
	public List<ImageVo> selectReviewSubImgs(int reviewIndex) throws Exception;
	public ReviewVo selectEditPage(int reviewIndex) throws Exception;
	public int deleteReview(int reviewIndex, CommentVo comment) throws Exception;
	
	public int deleteReview(int reviewIndex, Map<String, Integer> indexMap) throws Exception;
	public int deleteReview(int reviewIndex) throws Exception;
	
	public int deleteOne(int reviewIndex) throws Exception;
	public int deleteImages(int reviewIndex) throws Exception;
	public int deleteComment(CommentVo bean) throws Exception;
	public void updateReviewOnlyText(ReviewVo bean) throws Exception;
	public int updateReviewIncludeFile(ReviewVo bean, List<MultipartFile> images, String savedPath) throws Exception;
	
	
	public void reviewClickLike(LikeVo bean) throws Exception;
	
	public int selectReviewLikeCount(LikeVo bean) throws Exception;
	public LikeVo selectReviewLike(LikeVo bean) throws Exception;
	public void updateReviewLike(HashMap<String, Object> params) throws Exception;
	public void reviewNewLike(LikeVo bean) throws Exception;
	public int deleteReviewLike(LikeVo bean) throws Exception;
	public CommentVo selectOneComment(int commentIndex) throws Exception;
	public String selectThumbnail(int reviewIndex) throws Exception;
	
	public int updateReviewComment(CommentVo commentVo) throws Exception;
	public double selectRating(String branchId) throws Exception;
	
	// new paging----------------
	
		public List<ReviewVo> selectReviewSearch(SearchCriteria cri) throws Exception;
		public int selectReviewSearchCount(SearchCriteria cri) throws Exception;
		public List<ReviewVo> selectBranchReview(SearchCriteria cri) throws Exception;
		public int selectBranchReviewCount(SearchCriteria cri) throws Exception;
		public List<ReviewVo> listReviewSearchCri(SearchCriteria cri) throws Exception;
		public List<ReviewVo> selectReviewCriteria(Criteria cri) throws Exception;
		
		public int selectCriteriaCount() throws Exception;
		public List<CommentVo> selectCommentCriteria(Criteria cri) throws Exception;
		public int selectCommentPagingCount(int reviewIndex) throws Exception;
		
		
		
		
		
	
}
