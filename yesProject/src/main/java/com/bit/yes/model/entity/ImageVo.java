package com.bit.yes.model.entity;

public class ImageVo {

	private int reviewIndex;
	private String imageName;
	
	public ImageVo() {
		// TODO Auto-generated constructor stub
	}
	
	
	public ImageVo(int reviewIndex, String imageName) {
		super();
		this.reviewIndex = reviewIndex;
		this.imageName = imageName;
	}







	public int getReviewIndex() {
		return reviewIndex;
	}

	public void setReviewIndex(int reviewIndex) {
		this.reviewIndex = reviewIndex;
	}

	public String getImageName() {
		return imageName;
	}

	public void setImageName(String imageName) {
		this.imageName = imageName;
	}

	
	@Override
	public String toString() {
		return "ImageVo [reviewIndex=" + reviewIndex + ", imageName=" + imageName + "]";
	}

	
}
