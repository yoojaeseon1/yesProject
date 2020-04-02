package com.bit.yes.model.entity;

import java.sql.Date;
import java.util.Objects;

public class ReviewVo {

	private String branchID;
	private String clientID;
	private int reviewIndex;
	private String title;
	private String content;
	private Date registeredDate;
	private int rating;

	public ReviewVo() {
	}
	
	

	public ReviewVo(String branchID, String clientID, int reviewIndex, String title, String content,
			Date registeredDate, int rating) {
		super();
		this.branchID = branchID;
		this.clientID = clientID;
		this.reviewIndex = reviewIndex;
		this.title = title;
		this.content = content;
		this.registeredDate = registeredDate;
		this.rating = rating;
	}



	public String getBranchID() {
		return branchID;
	}

	public void setBranchID(String branchID) {
		this.branchID = branchID;
	}

	public String getClientID() {
		return clientID;
	}

	public void setClientID(String clientID) {
		this.clientID = clientID;
	}

	public int getReviewIndex() {
		return reviewIndex;
	}

	public void setReviewIndex(int reviewIndex) {
		this.reviewIndex = reviewIndex;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public String getContent() {
		return content;
	}

	public void setContent(String content) {
		this.content = content;
	}

	public Date getRegisteredDate() {
		return registeredDate;
	}

	public void setRegisteredDate(Date registeredDate) {
		this.registeredDate = registeredDate;
	}

	public int getRating() {
		return rating;
	}

	public void setRating(int rating) {
		this.rating = rating;
	}



	@Override
	public String toString() {
		return "ReviewVo [branchID=" + branchID + ", clientID=" + clientID + ", reviewIndex=" + reviewIndex + ", title="
				+ title + ", content=" + content + ", registeredDate=" + registeredDate + ", rating=" + rating + "]";
	}
	
	

	
}
