package com.bit.yes.model;

import java.util.List;
import java.util.Map;

import com.bit.yes.model.entity.CommentVo;
import com.bit.yes.model.entity.ImageVo;
import com.bit.yes.model.entity.LikeVo;
import com.bit.yes.model.entity.ReviewVo;
import com.bit.yes.model.paging.Criteria;
import com.bit.yes.model.paging.SearchCriteria;

public interface ReviewDAO {
	
	ImageVo selectReviewMainImgs(int index) throws Exception;
	List<ImageVo> selectReviewSubImgs(int index) throws Exception;
	ReviewVo selectOneReview(int index) throws Exception;
	int deleteReview(int index) throws Exception;
	int deleteReviewImage(int index) throws Exception;
	int deleteReviewComment(CommentVo bean) throws Exception;
	int deleteReviewComment(int reviewIndex) throws Exception;
	int insertReview(ReviewVo bean) throws Exception;
	int updateUseState(Map<String, Object> reserveStateMap) throws Exception;
	
	int insertReviewImage(ImageVo bean) throws Exception;
	int updateReview(ReviewVo bean) throws Exception;
	int insertReviewComment(CommentVo bean) throws Exception;
	List<CommentVo> selectListComment(int review_idx) throws Exception;
	int reviewClickLike(LikeVo bean) throws Exception;
	int updateReviewLike(Map<String, Object> params) throws Exception;
//	int reviewChangeLike(LikeVo bean) throws Exception;
	int selectReviewLikeCount(LikeVo bean) throws Exception;
	LikeVo selectReviewLike(LikeVo bean) throws Exception;
//	LikeVo reviewIsExistLike(LikeVo bean) throws Exception;
	int reviewNewLike(LikeVo bean) throws Exception;
	int deleteReviewLike(LikeVo bean) throws Exception;
	CommentVo selectOneComment(int commentIndex) throws Exception;
	String selectThumbnail(int reviewIndex) throws Exception;
	List<ReviewVo> listReview(Map<String, Object> params) throws Exception;
	
	int deleteReviewComment(Map<String, Integer> indexMap) throws Exception;
	
	
//	-------------------------------- paging
	
//	List<ReviewVo> writeList(int offset, int noOfRecords, String category, String keyword) throws Exception;
	
	int writeGetCount() throws Exception;
	int writeGetCount(Map<String, Object> params) throws Exception;

	int updateReviewComment(CommentVo commentVo);

	double selectRating(String branchId);
//	List<ImageVo> reviewListImage(HashMap<String, Object> params) throws Exception;
//	List<ReviewVo> writeList(HashMap<String, Object> params) throws Exception;
//	int writeGetCount(HashMap<String, Object> params) throws Exception;
	
	
	
	
// ---------------------------------new paging
	
	public List<ReviewVo> selectReviewSearch(SearchCriteria cri) throws Exception;
	
	public int selectReviewSearchCount(SearchCriteria cri) throws Exception;
	
	public List<ReviewVo> selectBranchReview(SearchCriteria cri) throws Exception;
	
	public int selectBranchReviewCount(SearchCriteria cri) throws Exception;
	
	public List<ReviewVo> selectReviewCriteria(Criteria cri) throws Exception;
	
	public int selectCriteriaCount() throws Exception;
	
	public List<CommentVo> selectCommentCriteria(Criteria cri) throws Exception;
	
	public int selectCommentPagingCount(int reviewIndex) throws Exception;
	
	
}
