package com.bit.yes.service;

import java.io.File;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.ui.Model;
import org.springframework.web.multipart.MultipartFile;

import com.bit.yes.model.ReviewDAO;
import com.bit.yes.model.entity.CommentVo;
import com.bit.yes.model.entity.ImageVo;
import com.bit.yes.model.entity.LikeVo;
import com.bit.yes.model.entity.ReviewVo;
import com.bit.yes.model.paging.Criteria;
import com.bit.yes.model.paging.SearchCriteria;

@Service
public class ReviewServiceImpl implements ReviewService {

	@Autowired
	ReviewDAO reviewDAO;

	Logger logger = LoggerFactory.getLogger(ReviewServiceImpl.class);


//	public void reviewMainImage(Model model, int index) throws Exception {
//		model.addAttribute("MainImage", reviewDAO.reviewMainImage(index));
//	}
//	
//	public void reviewSubImage(Model model, int index) throws Exception {
//		model.addAttribute("subImageList", reviewDAO.reviewSubImage(index));
//	}

	@Transactional
	@Override
	public int insertReview(ReviewVo bean, Map<String, Object> reserveStateMap, List<MultipartFile> images,
			String savedPath) throws Exception {

		logger.info("into createReview");

		reviewDAO.insertReview(bean);
		ImageVo imageBean = new ImageVo();

		String generatedID = UUID.randomUUID().toString();

		MultipartFile image = images.get(images.size() - 1);
		String imageName;
		
		// upload sub images
		
		if (!images.get(0).getOriginalFilename().equals("")) {
			for (int imagesI = 0; imagesI < images.size() - 1; imagesI++) {
				image = images.get(imagesI);

				generatedID = UUID.randomUUID().toString();
				imageName = generatedID + image.getOriginalFilename();
				imageBean.setImageName(imageName);
				logger.info("subImageName : " + imageName);
				reviewDAO.insertReviewImage(imageBean);
				image.transferTo(new File(savedPath + imageName));
			}
		}
		
		// upload main image
		if (!images.get(images.size() - 1).getOriginalFilename().equals("")) {
			image = images.get(images.size() - 1);
			generatedID = UUID.randomUUID().toString();

			imageName = "m_" + generatedID + images.get(images.size() - 1).getOriginalFilename();
			logger.info("mainImageName : " + imageName);
			image.transferTo(new File(savedPath + imageName));

			imageBean.setImageName(imageName);
			reviewDAO.insertReviewImage(imageBean);
		}
		return reviewDAO.updateUseState(reserveStateMap);

	}

	@Override
	public void insertReviewComment(CommentVo bean) throws Exception {
		reviewDAO.insertReviewComment(bean);
	}

	@Override
	public List<CommentVo> selectListComment(int reviewIndex) throws Exception {
		return (List<CommentVo>) reviewDAO.selectListComment(reviewIndex);
	}

	@Override
	public void insertReviewImage(ImageVo bean) throws Exception {

		reviewDAO.insertReviewImage(bean);

	}

	@Override
	public ReviewVo selectOneReview(int reviewIndex) throws Exception {

		return reviewDAO.selectOneReview(reviewIndex);
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
	public int deleteReview(int reviewIndex, CommentVo comment) throws Exception {

		reviewDAO.deleteReview(reviewIndex);
		reviewDAO.deleteReviewImage(reviewIndex);

		return reviewDAO.deleteReviewComment(comment);
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
	public int deleteComment(CommentVo bean) throws Exception {

		return reviewDAO.deleteReviewComment(bean);
	}

	@Override
	public void updateReviewOnlyText(ReviewVo bean) throws Exception {

		reviewDAO.updateReview(bean);

	}

	@Transactional
	@Override
	public int updateReviewIncludeFile(ReviewVo bean, List<MultipartFile> images, String savedPath) throws Exception {

		logger.info("editIncludeFile : " + bean.getReviewIndex());
		ImageVo imageBean = new ImageVo();
		imageBean.setReviewIndex(bean.getReviewIndex());

		reviewDAO.deleteReviewImage(bean.getReviewIndex());
		reviewDAO.updateReview(bean);

		String generatedID = UUID.randomUUID().toString();

		MultipartFile mainImage = images.get(images.size() - 1);

		String imageName = "m_" + generatedID + images.get(images.size() - 1).getOriginalFilename();
		imageBean.setImageName(imageName);
		reviewDAO.insertReviewImage(imageBean);
		logger.info("filePath : " + (savedPath + imageName));
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
	public void reviewClickLike(LikeVo bean) throws Exception {

		reviewDAO.reviewClickLike(bean);

	}

	@Override
	public int selectReviewLikeCount(LikeVo bean) throws Exception {
		return reviewDAO.selectReviewLikeCount(bean);
	}

	@Override
	public LikeVo selectReviewLike(LikeVo bean) throws Exception {
		return reviewDAO.selectReviewLike(bean);
	}

//	public void reviewChangeLike(LikeVo bean) throws Exception {
//		reviewDAO.reviewChangeLike(bean);
//	}

	@Override
	public void updateReviewLike(HashMap<String, Object> params) throws Exception {
		reviewDAO.updateReviewLike(params);
	}

//	public LikeVo reviewIsExistLike(LikeVo bean) throws Exception {
//		return reviewDAO.reviewIsExistLike(bean);
//	}

	@Override
	public void reviewNewLike(LikeVo bean) throws Exception {
		reviewDAO.reviewNewLike(bean);
	}

	@Override
	public int deleteReviewLike(LikeVo bean) throws Exception {
		return reviewDAO.deleteReviewLike(bean);
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

	// ��ü ����Ʈ
	/*
	 * public List<ReviewVo> writeList(int offset, int noOfRecords) throws Exception
	 * { return reviewDAO.writeList(offset, noOfRecords); }
	 */


	@Override
	public int updateReviewComment(CommentVo commentVo) {
		return reviewDAO.updateReviewComment(commentVo);
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
	public List<ReviewVo> listReviewSearchCri(SearchCriteria cri) throws Exception {
		return reviewDAO.selectReviewSearch(cri);
	}

	@Override
	public List<ReviewVo> selectReviewCriteria(Criteria cri) throws Exception {
		return reviewDAO.selectReviewCriteria(cri);
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
