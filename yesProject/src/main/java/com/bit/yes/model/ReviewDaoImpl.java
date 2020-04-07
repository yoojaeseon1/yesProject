package com.bit.yes.model;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.ibatis.session.SqlSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.bit.yes.model.entity.CommentVo;
import com.bit.yes.model.entity.ImageVo;
import com.bit.yes.model.entity.LikeVo;
import com.bit.yes.model.entity.ReviewVo;
import com.bit.yes.model.paging.Criteria;
import com.bit.yes.model.paging.SearchCriteria;

@Repository
public class ReviewDaoImpl implements ReviewDao {

	@Autowired
	SqlSession sqlSession;
	int noOfRecords;

	@Override
	public List<ReviewVo> reviewList() throws SQLException {

		return sqlSession.selectList("review.reviewList");
	}

	@Override
	public List<ImageVo> reviewListImage() throws SQLException {
		return sqlSession.selectList("review.reviewListImage");
	}
	
	@Override
	public List<ImageVo> reviewListImage(Map<String, Object> params) throws SQLException {
		return sqlSession.selectList("review.reviewListImage", params);
	}

	@Override
	public ImageVo reviewMainImage(int index) throws SQLException {

		return sqlSession.selectOne("review.reviewMainImage", index);
	}

	@Override
	public List<ImageVo> reviewSubImage(int index) throws SQLException {
		return sqlSession.selectList("review.reviewSubImage", index);
	}

	@Override
	public ReviewVo reviewSelect(int index) throws SQLException {

		return sqlSession.selectOne("review.reviewSelect", index);
	}

	@Override
	public int reviewDelete(int index) throws SQLException {

		return sqlSession.delete("review.reviewDelete", index);
	}

	@Override
	public int reviewDeleteComment(CommentVo bean) throws SQLException {

		return sqlSession.delete("review.reviewDeleteComment", bean);
	}

	public int reviewDeleteFile(int index) throws SQLException {
		return sqlSession.delete("review.reviewDeleteFile", index);
	}

	@Override
	public int reviewWrite(ReviewVo bean) throws SQLException {

		return sqlSession.insert("review.reviewWrite", bean);

	}

	public int reviewAddComment(CommentVo bean) throws SQLException {

		return sqlSession.insert("review.reviewAddComment", bean);
	}

	@Override
	public List<CommentVo> reiviewCommentList(int review_idx) throws SQLException {

		return sqlSession.selectList("review.reviewCommentList", review_idx);
	}

	@Override
	public int reviewImgUpload(ImageVo bean) throws SQLException {

		return sqlSession.insert("review.reviewImgUpload", bean);
	}

	@Override
	public int reviewEdit(ReviewVo bean) throws SQLException {

		return sqlSession.update("review.reviewEdit", bean);
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
	public int reviewEditComment(CommentVo commentVo) {
		return sqlSession.update("review.reviewEditComment", commentVo);
	}

	@Override
	public double loadReviewScoreAvg(String branchId) {
		List list;
		list = sqlSession.selectList("review.loadReviewScoreAvg", branchId);
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

		return sqlSession.insert("review.reviewLikeClick", bean);
	}

//	public int reviewChangeLike(LikeVo bean) throws SQLException {
//		
//		return sqlSession.update("review.reviewChangeLike", bean);
//	}
	public int reviewChangeLike(Map<String, Object> params) throws SQLException {

		return sqlSession.update("review.reviewChangeLike", params);
	}

	@Override
	public int reviewCountLike(LikeVo bean) throws SQLException {

		return sqlSession.selectOne("review.reviewCountLike", bean);
	}

	@Override
	public LikeVo reviewCheckLike(LikeVo bean) throws SQLException {
		return sqlSession.selectOne("review.reviewCheckLike", bean);
	}

//	public LikeVo reviewIsExistLike(LikeVo bean) throws SQLException {
//		
//		return sqlSession.selectOne("review.reviewIsExistLike", bean);
//		
//	}

	@Override
	public int reviewNewLike(LikeVo bean) throws SQLException {

		return sqlSession.insert("review.reviewNewLike", bean);
	}

	@Override
	public int reviewDeleteLike(LikeVo bean) throws SQLException {

		return sqlSession.delete("review.reviewDeleteLike", bean);
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
	public List<CommentVo> listCommentCriteria(Criteria cri) throws Exception {
		
		return sqlSession.selectList("review.listCommentCriteria", cri);
	}

	@Override
	public int countCommentPaging(int reviewIndex) throws Exception {
		
		return sqlSession.selectOne("review.countCommentPaging", reviewIndex);
	}

	@Override
	public List<ReviewVo> listReviewCriteria(Criteria cri) throws Exception {
		
		return sqlSession.selectList("review.listReviewCriteria", cri);
	}

	@Override
	public int listCountCriteria() throws Exception {
		
		return sqlSession.selectOne("review.listCountCriteria");
		
	}

	@Override
	public List<ReviewVo> listReviewSearch(SearchCriteria cri) throws Exception {
		
		return sqlSession.selectList("review.listReviewSearch", cri);
	}

	@Override
	public int listReviewSearchCount(SearchCriteria cri) throws Exception {
		
		return sqlSession.selectOne("review.listReviewSearchCount", cri);
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