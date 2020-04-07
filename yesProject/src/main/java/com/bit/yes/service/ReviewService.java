package com.bit.yes.service;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.ui.Model;

import com.bit.yes.model.ReviewDao;
import com.bit.yes.model.entity.CommentVo;
import com.bit.yes.model.entity.ImageVo;
import com.bit.yes.model.entity.LikeVo;
import com.bit.yes.model.entity.ReviewVo;
import com.bit.yes.model.paging.Criteria;
import com.bit.yes.model.paging.SearchCriteria;

@Service
public class ReviewService {
	
	@Autowired
	ReviewDao reviewDao;
	
	public void listPage(Model model) throws SQLException {
		model.addAttribute("alist", reviewDao.reviewList());
	}
	
	public List<ImageVo> listPageImage() throws SQLException {
		
//		List<ImageVo> images = reviewDao.reviewListImage();
		
//		for(int imagesI = 0; imagesI < images.size(); imagesI++) {
//			System.out.println(images.get(imagesI));
//		}
		
//		model.addAttribute("imageList", reviewDao.reviewListImage());
		return reviewDao.reviewListImage();
		
	}
	
	public void listPageImage(Model model, Map<String, Object> params) throws SQLException {
		model.addAttribute("imageList", reviewDao.reviewListImage(params));
	}
	
//	public void reviewMainImage(Model model, int index) throws SQLException {
//		model.addAttribute("MainImage", reviewDao.reviewMainImage(index));
//	}
//	
//	public void reviewSubImage(Model model, int index) throws SQLException {
//		model.addAttribute("subImageList", reviewDao.reviewSubImage(index));
//	}

	
	public void reviewWrite(ReviewVo bean) throws SQLException {
		reviewDao.reviewWrite(bean);
	}
	
	public void reviewAddComment(CommentVo bean) throws SQLException {
		reviewDao.reviewAddComment(bean);
	}
	
	public List<CommentVo> reviewCommentList(int review_idx) throws SQLException {
		return (ArrayList<CommentVo>) reviewDao.reiviewCommentList(review_idx); 
	}
	
	public void reviewImgUpload(ImageVo bean) throws SQLException {
		
		reviewDao.reviewImgUpload(bean);
		
	}
	
	
	public ReviewVo selectPage(int index) throws SQLException {
		
		return reviewDao.reviewSelect(index);
	}
	
	public ImageVo reviewMainImage(int index) throws SQLException {
		return reviewDao.reviewMainImage(index);
	}
	
	public List<ImageVo> reviewSubImage(int index) throws SQLException {
		return reviewDao.reviewSubImage(index);
	}
	
	public ReviewVo reviewEditPage(int index) throws SQLException {
		
		return reviewDao.reviewSelect(index);
		
	}
	
	public int deleteOne(int index) throws SQLException {
		
		return reviewDao.reviewDelete(index);
	}
	
	public int deleteFile(int index) throws SQLException {
		return reviewDao.reviewDeleteFile(index);
	}
	
	public int deleteComment(CommentVo bean) throws SQLException {
		
		return reviewDao.reviewDeleteComment(bean);
	}
	
	public void editOne(ReviewVo bean) throws SQLException {
		
		reviewDao.reviewEdit(bean);
		
	}
	
	public void reviewClickLike(LikeVo bean) throws SQLException {
		
		reviewDao.reviewClickLike(bean);
		
	}
	
	public int reviewCountLike(LikeVo bean) throws SQLException {
		return reviewDao.reviewCountLike(bean);
	}
	
	public LikeVo reviewCheckLike(LikeVo bean) throws SQLException {
		return reviewDao.reviewCheckLike(bean);
	}
	
//	public void reviewChangeLike(LikeVo bean) throws SQLException {
//		reviewDao.reviewChangeLike(bean);
//	}
	
	public void reviewChangeLike(HashMap<String, Object> params) throws SQLException {
		reviewDao.reviewChangeLike(params);
	}
	
//	public LikeVo reviewIsExistLike(LikeVo bean) throws SQLException {
//		return reviewDao.reviewIsExistLike(bean);
//	}
	
	public void reviewNewLike(LikeVo bean) throws SQLException {
		reviewDao.reviewNewLike(bean);
	}
	
	public int reviewDeleteLike(LikeVo bean) throws SQLException {
		return reviewDao.reviewDeleteLike(bean);
	}
	
	public CommentVo selectOneComment(int commentIndex) throws SQLException {
		return reviewDao.selectOneComment(commentIndex);
	}
	
	public String selectThumbnail(int reviewIndex) throws SQLException{
		
		return reviewDao.selectThumbnail(reviewIndex);
		
	}
	
	
//	--------------------paging
	
	// ��ü ����Ʈ
/*	public List<ReviewVo> writeList(int offset, int noOfRecords) throws SQLException {
		return reviewDao.writeList(offset, noOfRecords);
	}*/
	public List<ReviewVo> listReview(Map<String, Object> params) throws SQLException {
		
		System.out.println("into reviewService");
		
		return reviewDao.listReview(params);
	}
	
	// �˻� ����Ʈ
//	public List<ReviewVo> writeList(int offset, int noOfRecords, String category, String keyword) throws SQLException {
//		return reviewDao.writeList(offset, noOfRecords, category, keyword);
//	}
	
	public int writeGetCount() throws Exception {
		return reviewDao.writeGetCount();
	}
	public int writeGetCount(Map<String, Object> params) throws Exception {
		return reviewDao.writeGetCount(params);
	}


	public int editComment(CommentVo commentVo) {
		return reviewDao.reviewEditComment(commentVo);
	}

	public double loadReviewScoreAvg(String branchId) {
		return reviewDao.loadReviewScoreAvg(branchId);
	}
	
	
	
	// new paging----------------
	
	public List<ReviewVo> listReviewSearch(SearchCriteria cri) throws Exception{
		return reviewDao.listReviewSearch(cri);
	}
	
	public int listReviewSearchCount(SearchCriteria cri) throws Exception {
		return reviewDao.listReviewSearchCount(cri);
	}
	
	public List<ReviewVo> listReviewSearchCri(SearchCriteria cri) throws Exception{
		return reviewDao.listReviewSearch(cri);
	}
	
	
	
	public List<ReviewVo> listReviewCriteria(Criteria cri) throws Exception{
		return reviewDao.listReviewCriteria(cri);
	}
	
	public int countReviewPaging() throws Exception {
		return reviewDao.listCountCriteria();
	}
	
	public List<CommentVo> listCommentCriteria(Criteria cri) throws Exception {
		return reviewDao.listCommentCriteria(cri);
	}
	
	public int countCommentPaging(int reviewIndex) throws Exception {
		return reviewDao.countCommentPaging(reviewIndex);
	}
	
}
