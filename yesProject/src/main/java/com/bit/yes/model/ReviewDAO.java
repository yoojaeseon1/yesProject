package com.bit.yes.model;

import java.sql.SQLException;
import java.util.List;
import java.util.Map;

import com.bit.yes.model.entity.CommentVo;
import com.bit.yes.model.entity.ImageVo;
import com.bit.yes.model.entity.LikeVo;
import com.bit.yes.model.entity.ReviewVo;
import com.bit.yes.model.paging.Criteria;
import com.bit.yes.model.paging.SearchCriteria;

public interface ReviewDAO {
	
	ImageVo selectReviewMainImgs(int index) throws SQLException;
	List<ImageVo> selectReviewSubImgs(int index) throws SQLException;
	ReviewVo selectOneReview(int index) throws SQLException;
	int deleteReview(int index) throws SQLException;
	int deleteReviewImage(int index) throws SQLException;
	int deleteReviewComment(CommentVo bean) throws SQLException;
	int deleteReviewComment(int reviewIndex) throws Exception;
	int insertReview(ReviewVo bean) throws Exception;
	int updateUseState(Map<String, Object> reserveStateMap) throws Exception;
	
	int insertReviewImage(ImageVo bean) throws SQLException;
	int updateReview(ReviewVo bean) throws SQLException;
	int insertReviewComment(CommentVo bean) throws SQLException;
	List<CommentVo> selectListComment(int review_idx) throws SQLException;
	int reviewClickLike(LikeVo bean) throws SQLException;
	int updateReviewLike(Map<String, Object> params) throws SQLException;
//	int reviewChangeLike(LikeVo bean) throws SQLException;
	int selectReviewLikeCount(LikeVo bean) throws SQLException;
	LikeVo selectReviewLike(LikeVo bean) throws SQLException;
//	LikeVo reviewIsExistLike(LikeVo bean) throws SQLException;
	int reviewNewLike(LikeVo bean) throws SQLException;
	int deleteReviewLike(LikeVo bean) throws SQLException;
	CommentVo selectOneComment(int commentIndex) throws SQLException;
	String selectThumbnail(int reviewIndex) throws SQLException;
	List<ReviewVo> listReview(Map<String, Object> params) throws SQLException;
	
	int deleteReviewComment(Map<String, Integer> indexMap) throws Exception;
	
	
//	-------------------------------- paging
	
//	List<ReviewVo> writeList(int offset, int noOfRecords, String category, String keyword) throws SQLException;
	
	int writeGetCount() throws SQLException;
	int writeGetCount(Map<String, Object> params) throws SQLException;

	int updateReviewComment(CommentVo commentVo);

	double selectRating(String branchId);
//	List<ImageVo> reviewListImage(HashMap<String, Object> params) throws SQLException;
//	List<ReviewVo> writeList(HashMap<String, Object> params) throws SQLException;
//	int writeGetCount(HashMap<String, Object> params) throws SQLException;
	
	
	
	
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
