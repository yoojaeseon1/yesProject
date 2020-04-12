package com.bit.yes.model.entity;

import java.sql.Date;

public class LikeVo {

//	private int review_idx;
//	private String writer;
//	private boolean checked;
//	private Date click_date;
	private int reviewIndex;
	private String clientID;
	private boolean checked;
	private Date clickDate;

	public LikeVo() {
		// TODO Auto-generated constructor stub
	}
	
	

	public LikeVo(int reviewIndex, String clientID, boolean checked, Date clickDate) {
		super();
		this.reviewIndex = reviewIndex;
		this.clientID = clientID;
		this.checked = checked;
		this.clickDate = clickDate;
	}



	public int getReviewIndex() {
		return reviewIndex;
	}

	public void setReviewIndex(int reviewIndex) {
		this.reviewIndex = reviewIndex;
	}

	public String getClientID() {
		return clientID;
	}

	public void setClientID(String clientID) {
		this.clientID = clientID;
	}

	public boolean isChecked() {
		return checked;
	}

	public void setChecked(boolean checked) {
		this.checked = checked;
	}

	public Date getClickDate() {
		return clickDate;
	}

	public void setClickDate(Date clickDate) {
		this.clickDate = clickDate;
	}

	@Override
	public String toString() {
		return "LikeVo [reviewIndex=" + reviewIndex + ", clientID=" + clientID + ", checked=" + checked + ", clickDate="
				+ clickDate + "]";
	}
	
	



}
