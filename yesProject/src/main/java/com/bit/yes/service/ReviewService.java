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
	
	
	public void listPage(Model model) throws Exception;
	
	public List<ImageVo> listPageImage() throws Exception;
	
	public void listPageImage(Model model, Map<String, Object> params) throws Exception;
	
	public int createReview(ReviewVo bean, Map<String, Object> reserveStateMap, List<MultipartFile> images, String savedPath) throws Exception;
	
	public void reviewAddComment(CommentVo bean) throws Exception;
	
	public List<CommentVo> reviewCommentList(int review_idx) throws Exception;
	
	public void reviewImgUpload(ImageVo bean) throws Exception;
	
	public ReviewVo selectPage(int index) throws Exception;
	
	public ImageVo reviewMainImage(int index) throws Exception;
	public List<ImageVo> reviewSubImage(int index) throws Exception;
	public ReviewVo reviewEditPage(int index) throws Exception;
	public int deleteReview(int reviewIndex, CommentVo comment) throws Exception;
	
	public int deleteReview(int reviewIndex, Map<String, Integer> indexMap) throws Exception;
	public int deleteReview(int reviewIndex) throws Exception;
	
	public int deleteOne(int index) throws Exception;
	public int deleteImages(int index) throws Exception;
	public int deleteComment(CommentVo bean) throws Exception;
	public void editOnlyText(ReviewVo bean) throws Exception;
	public int editIncludeFile(ReviewVo bean, List<MultipartFile> images, String savedPath) throws Exception;
	
	
	public void reviewClickLike(LikeVo bean) throws Exception;
	
	public int reviewCountLike(LikeVo bean) throws Exception;
	public LikeVo reviewCheckLike(LikeVo bean) throws Exception;
	public void reviewChangeLike(HashMap<String, Object> params) throws Exception;
	public void reviewNewLike(LikeVo bean) throws Exception;
	public int reviewDeleteLike(LikeVo bean) throws Exception;
	public CommentVo selectOneComment(int commentIndex) throws Exception;
	public String selectThumbnail(int reviewIndex) throws Exception;
	public List<ReviewVo> listReview(Map<String, Object> params) throws Exception;
	
	public int writeGetCount() throws Exception;
	public int writeGetCount(Map<String, Object> params) throws Exception;
	public int editComment(CommentVo commentVo) throws Exception;
	public double loadReviewScoreAvg(String branchId) throws Exception;
	
	// new paging----------------
	
		public List<ReviewVo> listReviewSearch(SearchCriteria cri) throws Exception;
		public int listReviewSearchCount(SearchCriteria cri) throws Exception;
		public List<ReviewVo> listBranchReview(SearchCriteria cri) throws Exception;
		public int countBranchReview(SearchCriteria cri) throws Exception;
		public List<ReviewVo> listReviewSearchCri(SearchCriteria cri) throws Exception;
		public List<ReviewVo> listReviewCriteria(Criteria cri) throws Exception;
		
		public int countReviewPaging() throws Exception;
		public List<CommentVo> listCommentCriteria(Criteria cri) throws Exception;
		public int countCommentPaging(int reviewIndex) throws Exception;
		
		
		
		
	
}
