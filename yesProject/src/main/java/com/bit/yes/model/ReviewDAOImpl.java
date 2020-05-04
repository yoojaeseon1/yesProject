package com.bit.yes.model;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.ibatis.session.SqlSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import com.bit.yes.model.entity.CommentVo;
import com.bit.yes.model.entity.ImageVo;
import com.bit.yes.model.entity.LikeVo;
import com.bit.yes.model.entity.ReviewVo;
import com.bit.yes.model.paging.Criteria;
import com.bit.yes.model.paging.SearchCriteria;

@Repository
public class ReviewDAOImpl implements ReviewDAO {

	@Autowired
	SqlSession sqlSession;
	int noOfRecords;


	@Override
	public ImageVo selectReviewMainImgs(int index) throws SQLException {

		return sqlSession.selectOne("review.selectReviewMainImgs", index);
	}

	@Override
	public List<ImageVo> selectReviewSubImgs(int index) throws SQLException {
		return sqlSession.selectList("review.selectReviewSubImgs", index);
	}

	@Override
	public ReviewVo selectOneReview(int index) throws SQLException {

		return sqlSession.selectOne("review.selectOneReview", index);
	}

	@Override
	public int deleteReview(int index) throws SQLException {

		return sqlSession.delete("review.deleteReview", index);
	}
	public int deleteReviewImage(int index) throws SQLException {
		return sqlSession.delete("review.deleteReveiwImage", index);
	}

	@Override
	public int deleteReviewComment(CommentVo bean) throws SQLException {

		return sqlSession.delete("review.deleteReviewComment", bean);
	}
	
	@Override
	public int deleteReviewComment(int reviewIndex) throws Exception {
		
		return sqlSession.delete("review.reviewDeleteComment", reviewIndex);
	}

	
	
	@Override
	@Transactional
//		public int createReview(ReviewVo bean, Map<String, Object> reserveStateMap) throws SQLException {
	public int insertReview(ReviewVo bean) throws Exception {
		
		return sqlSession.insert("review.insertReview", bean);

	}
	
	@Override
	public int updateUseState(Map<String, Object> reserveStateMap) throws Exception {
		
		return sqlSession.update("reserve.updateUseState", reserveStateMap);
	}
	
	

	public int insertReviewComment(CommentVo bean) throws SQLException {

		return sqlSession.insert("review.insertReviewComment", bean);
	}

	@Override
	public List<CommentVo> selectListComment(int review_idx) throws SQLException {

		return sqlSession.selectList("review.selectListComment", review_idx);
	}

	@Override
	public int insertReviewImage(ImageVo bean) throws SQLException {

		return sqlSession.insert("review.insertReviewImage", bean);
	}

	@Override
	public int updateReview(ReviewVo bean) throws SQLException {

		return sqlSession.update("review.updateReview", bean);
	}

	// ��ü ����Ʈ ����¡

//	@Override
//	public List<ReviewVo> writeList(HashMap<String, Object> params) throws SQLException {
//		
//		System.out.println("into DAO : writeList");
//		List<ReviewVo> writeList = new ArrayList<ReviewVo>();
//		
//		writeList = sqlSession.selectList("review.writeList", params);
//		
//		System.out.println("writeList : " + writeList.size());
//		this.noOfRecords = sqlSession.selectOne("review.writeGetCount");
//		
//		return writeList;
//	}

	@Override
	public int writeGetCount() throws SQLException {

		return sqlSession.selectOne("review.writeGetCount");

	}

//	@Override
//	public int writeGetCount(HashMap<String, Object> params) throws SQLException {
//		
//		return sqlSession.selectOne("review.writeGetCount", params);
//		
//	}

	@Override
	public int updateReviewComment(CommentVo commentVo) {
		return sqlSession.update("review.updateReviewComment", commentVo);
	}

	@Override
	public double selectRating(String branchId) {
		List list;
		list = sqlSession.selectList("review.selectRating", branchId);
		System.out.println("list : " + list);
		int scoreAvg = 0;
		if (list.size() == 0)
			return 6;
		else {
			for (int i = 0; i < list.size(); i++) {
				int cnt = 0;
				cnt = (int) list.get(i);
				scoreAvg = scoreAvg + cnt;
			}
			return scoreAvg / list.size();
		}
	}

	@Override
	public int reviewClickLike(LikeVo bean) throws SQLException {

		return sqlSession.insert("review.insertReviewLike", bean);
	}

//	public int reviewChangeLike(LikeVo bean) throws SQLException {
//		
//		return sqlSession.update("review.reviewChangeLike", bean);
//	}
	public int updateReviewLike(Map<String, Object> params) throws SQLException {

		return sqlSession.update("review.updateReviewLike", params);
	}

	@Override
	public int selectReviewLikeCount(LikeVo bean) throws SQLException {

		return sqlSession.selectOne("review.selectReviewLikeCount", bean);
	}

	@Override
	public LikeVo selectReviewLike(LikeVo bean) throws SQLException {
		return sqlSession.selectOne("review.selectReviewLike", bean);
	}

//	public LikeVo reviewIsExistLike(LikeVo bean) throws SQLException {
//		
//		return sqlSession.selectOne("review.reviewIsExistLike", bean);
//		
//	}

	@Override
	public int reviewNewLike(LikeVo bean) throws SQLException {

		return sqlSession.insert("review.insertReviewLike", bean);
	}

	@Override
	public int deleteReviewLike(LikeVo bean) throws SQLException {

		return sqlSession.delete("review.deleteReviewLike", bean);
	}

	@Override
//	public List<ReviewVo> writeList(Map<String, Object> params) throws SQLException {
	public List<ReviewVo> listReview(Map<String, Object> params) throws SQLException {
//		System.out.println("into DAO : writeList");
		List<ReviewVo> reviews = new ArrayList<ReviewVo>();

		reviews = sqlSession.selectList("review.listReview", params);

//		System.out.println("writeList : " + writeList.size());
		this.noOfRecords = sqlSession.selectOne("review.writeGetCount");

		return reviews;
	}

	@Override
	public int writeGetCount(Map<String, Object> params) throws SQLException {

		return sqlSession.selectOne("review.writeGetCount", params);
	}

	@Override
	public CommentVo selectOneComment(int commentIndex) throws SQLException {

		return sqlSession.selectOne("review.selectOneComment", commentIndex);
	}

	@Override
	public String selectThumbnail(int reviewIndex) throws SQLException {

		return sqlSession.selectOne("review.selectThumbnail", reviewIndex);
	}

	
	
	
	
	
//	new paging------------------
	

	@Override
	public List<ReviewVo> selectReviewSearch(SearchCriteria cri) throws Exception {
		
		return sqlSession.selectList("review.selectReviewSearch", cri);
	}

	@Override
	public int selectReviewSearchCount(SearchCriteria cri) throws Exception {
		
		return sqlSession.selectOne("review.selectReviewSearchCount", cri);
	}

	
	@Override
	public List<ReviewVo> selectBranchReview(SearchCriteria cri) throws Exception {
		
		return sqlSession.selectList("review.selectBranchReview", cri);
	}

	@Override
	public int selectBranchReviewCount(SearchCriteria cri) throws Exception {
		
		return sqlSession.selectOne("review.selectBranchReviewCount", cri);
	}
	
	
	
	@Override
	public List<CommentVo> selectCommentCriteria(Criteria cri) throws Exception {
		
		return sqlSession.selectList("review.selectCommentCriteria", cri);
	}

	@Override
	public int selectCommentPagingCount(int reviewIndex) throws Exception {
		
		return sqlSession.selectOne("review.selectCommentPagingCount", reviewIndex);
	}
	
	

	@Override
	public List<ReviewVo> selectReviewCriteria(Criteria cri) throws Exception {
		
		return sqlSession.selectList("review.selectReviewCriteria", cri);
	}

	@Override
	public int selectCriteriaCount() throws Exception {
		
		return sqlSession.selectOne("review.selectCriteriaCount");
		
	}

	@Override
	public int deleteReviewComment(Map<String, Integer> indexMap) throws Exception {
		// TODO Auto-generated method stub
		return 0;
	}






	
	
	
	
	
	
	
	
	
	
	
	// �˻� ����Ʈ ����¡
	/*
	 * public List<ReviewVo> writeList(int offset, int noOfRecords, String category,
	 * String keyword) throws SQLException {
	 * 
	 * List<ReviewVo> writeList = new ArrayList<ReviewVo>();
	 * 
	 * HashMap<String, Object> params = new HashMap<String, Object>();
	 * 
	 * params.put("offset", offset); params.put("noOfRecords", noOfRecords);
	 * params.put("category", category); params.put("keyword", keyword);
	 * 
	 * writeList = sqlSession.selectList("writeList", params); this.noOfRecords =
	 * sqlSession.selectOne("writeGetCount");
	 * 
	 * return writeList; }
	 */

	/*
	 * @Override public int writeGetCount() throws SQLException {
	 * 
	 * return sqlSession.selectOne("writeGetCount"); }
	 * 
	 * public int writeGetCount(HashMap params) throws SQLException {
	 * 
	 * HashMap<String, Object> params = new HashMap<String, Object>();
	 * 
	 * params.put("category", category); params.put("keyword", keyword);
	 * 
	 * return sqlSession.selectOne("writeGetCount", params); }
	 */
}