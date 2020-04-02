package com.bit.yes.model.entity;

import java.sql.Date;

public class LikeVo {

//	private int review_idx;
//	private String writer;
//	private boolean checked;
//	private Date click_date;
	private int reviewIndex;
	private String writer;
	private boolean checked;
	private Date clickDate;

	public LikeVo() {
		// TODO Auto-generated constructor stub
	}
	
	

	public LikeVo(int reviewIndex, String writer, boolean checked, Date clickDate) {
		super();
		this.reviewIndex = reviewIndex;
		this.writer = writer;
		this.checked = checked;
		this.clickDate = clickDate;
	}



	public int getReviewIndex() {
		return reviewIndex;
	}

	public void setReviewIndex(int reviewIndex) {
		this.reviewIndex = reviewIndex;
	}

	public String getWriter() {
		return writer;
	}

	public void setWriter(String writer) {
		this.writer = writer;
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
		return "LikeVo [reviewIndex=" + reviewIndex + ", writer=" + writer + ", checked=" + checked + ", clickDate="
				+ clickDate + "]";
	}
	
	



}
