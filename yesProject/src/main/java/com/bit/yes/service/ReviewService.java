package com.bit.yes.service;

import java.sql.SQLException;
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
	
	
	public void listPage(Model model) throws SQLException;
	
	public List<ImageVo> listPageImage() throws SQLException;
	
	public void listPageImage(Model model, Map<String, Object> params) throws SQLException;
	
	public void reviewWrite(ReviewVo bean, Map<String, Object> reserveStateMap) throws SQLException;
	
	public void reviewAddComment(CommentVo bean) throws SQLException;
	
	public List<CommentVo> reviewCommentList(int review_idx) throws SQLException;
	
	public void reviewImgUpload(ImageVo bean) throws SQLException;
	
	public ReviewVo selectPage(int index) throws SQLException;
	
	public ImageVo reviewMainImage(int index) throws SQLException;
	public List<ImageVo> reviewSubImage(int index) throws SQLException;
	public ReviewVo reviewEditPage(int index) throws SQLException;
	public int deleteReview(int reviewIndex, CommentVo comment) throws Exception;
	
	public int deleteReview(int reviewIndex, Map<String, Integer> indexMap) throws Exception;
	public int deleteOne(int index) throws SQLException;
	public int deleteImages(int index) throws SQLException;
	public int deleteComment(CommentVo bean) throws SQLException;
	public void editOnlyText(ReviewVo bean) throws SQLException;
	public int editIncludeFile(ReviewVo bean, List<MultipartFile> images, String savedPath) throws Exception;
	
	
	public void reviewClickLike(LikeVo bean) throws SQLException;
	
	public int reviewCountLike(LikeVo bean) throws SQLException;
	public LikeVo reviewCheckLike(LikeVo bean) throws SQLException;
	public void reviewChangeLike(HashMap<String, Object> params) throws SQLException;
	public void reviewNewLike(LikeVo bean) throws SQLException;
	public int reviewDeleteLike(LikeVo bean) throws SQLException;
	public CommentVo selectOneComment(int commentIndex) throws SQLException;
	public String selectThumbnail(int reviewIndex) throws SQLException;
	public List<ReviewVo> listReview(Map<String, Object> params) throws SQLException;
	
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
