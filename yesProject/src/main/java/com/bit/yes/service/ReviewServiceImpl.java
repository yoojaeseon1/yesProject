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

	@Override
	public void listPage(Model model) throws Exception {
		model.addAttribute("alist", reviewDAO.reviewList());
	}

	@Override
	public List<ImageVo> listPageImage() throws Exception {

//		List<ImageVo> images = reviewDAO.reviewListImage();

//		for(int imagesI = 0; imagesI < images.size(); imagesI++) {
//			System.out.println(images.get(imagesI));
//		}

//		model.addAttribute("imageList", reviewDAO.reviewListImage());
		return reviewDAO.reviewListImage();

	}

	@Override
	public void listPageImage(Model model, Map<String, Object> params) throws Exception {
		model.addAttribute("imageList", reviewDAO.reviewListImage(params));
	}

//	public void reviewMainImage(Model model, int index) throws Exception {
//		model.addAttribute("MainImage", reviewDAO.reviewMainImage(index));
//	}
//	
//	public void reviewSubImage(Model model, int index) throws Exception {
//		model.addAttribute("subImageList", reviewDAO.reviewSubImage(index));
//	}

	@Transactional
	@Override
	public int createReview(ReviewVo bean, Map<String, Object> reserveStateMap, List<MultipartFile> images,
			String savedPath) throws Exception {

		logger.info("into createReview");

		reviewDAO.createReview(bean);
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
				reviewDAO.reviewImgUpload(imageBean);
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
			reviewDAO.reviewImgUpload(imageBean);
		}
		return reviewDAO.updateUseState(reserveStateMap);

	}

	@Override
	public void reviewAddComment(CommentVo bean) throws Exception {
		reviewDAO.reviewAddComment(bean);
	}

	@Override
	public List<CommentVo> reviewCommentList(int review_idx) throws Exception {
		return (List<CommentVo>) reviewDAO.reiviewCommentList(review_idx);
	}

	@Override
	public void reviewImgUpload(ImageVo bean) throws Exception {

		reviewDAO.reviewImgUpload(bean);

	}

	@Override
	public ReviewVo selectPage(int index) throws Exception {

		return reviewDAO.reviewSelect(index);
	}

	@Override
	public ImageVo reviewMainImage(int index) throws Exception {
		return reviewDAO.reviewMainImage(index);
	}

	public List<ImageVo> reviewSubImage(int index) throws Exception {
		return reviewDAO.reviewSubImage(index);
	}

	public ReviewVo reviewEditPage(int index) throws Exception {

		return reviewDAO.reviewSelect(index);

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
	public int deleteOne(int index) throws Exception {

		return reviewDAO.deleteReview(index);
	}

	@Override
	public int deleteImages(int index) throws Exception {
		return reviewDAO.deleteReviewImage(index);
	}

	@Override
	public int deleteComment(CommentVo bean) throws Exception {

		return reviewDAO.deleteReviewComment(bean);
	}

	@Override
	public void editOnlyText(ReviewVo bean) throws Exception {

		reviewDAO.reviewEdit(bean);

	}

	@Transactional
	@Override
	public int editIncludeFile(ReviewVo bean, List<MultipartFile> images, String savedPath) throws Exception {

		logger.info("editIncludeFile : " + bean.getReviewIndex());
		ImageVo imageBean = new ImageVo();
		imageBean.setReviewIndex(bean.getReviewIndex());

		reviewDAO.deleteReviewImage(bean.getReviewIndex());
		reviewDAO.reviewEdit(bean);

		String generatedID = UUID.randomUUID().toString();

		MultipartFile mainImage = images.get(images.size() - 1);

		String imageName = "m_" + generatedID + images.get(images.size() - 1).getOriginalFilename();
		imageBean.setImageName(imageName);
		reviewDAO.reviewImgUpload(imageBean);
		logger.info("filePath : " + (savedPath + imageName));
		mainImage.transferTo(new File(savedPath + imageName));

		if (!images.get(0).getOriginalFilename().equals("")) {
			for (int imagesI = 0; imagesI < images.size() - 1; imagesI++) {
				MultipartFile subImage = images.get(imagesI);
				generatedID = UUID.randomUUID().toString();
				imageName = generatedID + subImage.getOriginalFilename();
				imageBean.setImageName(imageName);
				reviewDAO.reviewImgUpload(imageBean);
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
	public int reviewCountLike(LikeVo bean) throws Exception {
		return reviewDAO.reviewCountLike(bean);
	}

	@Override
	public LikeVo reviewCheckLike(LikeVo bean) throws Exception {
		return reviewDAO.reviewCheckLike(bean);
	}

//	public void reviewChangeLike(LikeVo bean) throws Exception {
//		reviewDAO.reviewChangeLike(bean);
//	}

	@Override
	public void reviewChangeLike(HashMap<String, Object> params) throws Exception {
		reviewDAO.reviewChangeLike(params);
	}

//	public LikeVo reviewIsExistLike(LikeVo bean) throws Exception {
//		return reviewDAO.reviewIsExistLike(bean);
//	}

	@Override
	public void reviewNewLike(LikeVo bean) throws Exception {
		reviewDAO.reviewNewLike(bean);
	}

	@Override
	public int reviewDeleteLike(LikeVo bean) throws Exception {
		return reviewDAO.reviewDeleteLike(bean);
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
	public List<ReviewVo> listReview(Map<String, Object> params) throws Exception {

		System.out.println("into reviewService");

		return reviewDAO.listReview(params);
	}

	// �˻� ����Ʈ
//	public List<ReviewVo> writeList(int offset, int noOfRecords, String category, String keyword) throws Exception {
//		return reviewDAO.writeList(offset, noOfRecords, category, keyword);
//	}

	@Override
	public int writeGetCount() throws Exception {
		return reviewDAO.writeGetCount();
	}

	@Override
	public int writeGetCount(Map<String, Object> params) throws Exception {
		return reviewDAO.writeGetCount(params);
	}

	@Override
	public int editComment(CommentVo commentVo) {
		return reviewDAO.reviewEditComment(commentVo);
	}

	@Override
	public double loadReviewScoreAvg(String branchId) {
		return reviewDAO.loadReviewScoreAvg(branchId);
	}

	// new paging----------------

	@Override
	public List<ReviewVo> listReviewSearch(SearchCriteria cri) throws Exception {
		return reviewDAO.listReviewSearch(cri);
	}

	@Override
	public int listReviewSearchCount(SearchCriteria cri) throws Exception {
		return reviewDAO.listReviewSearchCount(cri);
	}

	@Override
	public List<ReviewVo> listBranchReview(SearchCriteria cri) throws Exception {
		return reviewDAO.listBranchReview(cri);
	}

	@Override
	public int countBranchReview(SearchCriteria cri) throws Exception {
		return reviewDAO.countBranchReview(cri);
	}

	@Override
	public List<ReviewVo> listReviewSearchCri(SearchCriteria cri) throws Exception {
		return reviewDAO.listReviewSearch(cri);
	}

	@Override
	public List<ReviewVo> listReviewCriteria(Criteria cri) throws Exception {
		return reviewDAO.listReviewCriteria(cri);
	}

	@Override
	public int countReviewPaging() throws Exception {
		return reviewDAO.listCountCriteria();
	}

	@Override
	public List<CommentVo> listCommentCriteria(Criteria cri) throws Exception {
		return reviewDAO.listCommentCriteria(cri);
	}

	@Override
	public int countCommentPaging(int reviewIndex) throws Exception {
		return reviewDAO.countCommentPaging(reviewIndex);
	}

}
