package com.bit.yes.model.entity;

import java.sql.Date;

public class CommentVo {

	private int commentIndex;
	private int reviewIndex;
	private String comment;
	private String writer;
	private Date registeredDate;
//	private int commentIdx;
//	private int review_idx;
//	private String comment;
//	private String writer;
//	private Date regDate;

	public CommentVo() {
		// TODO Auto-generated constructor stub
	}
	

	public CommentVo(int commentIndex, int reviewIndex, String comment, String writer, Date registeredDate) {
		super();
		this.commentIndex = commentIndex;
		this.reviewIndex = reviewIndex;
		this.comment = comment;
		this.writer = writer;
		this.registeredDate = registeredDate;
	}




	public int getCommentIndex() {
		return commentIndex;
	}

	public void setCommentIndex(int commentIndex) {
		this.commentIndex = commentIndex;
	}

	public int getReviewIndex() {
		return reviewIndex;
	}

	public void setReviewIndex(int reviewIndex) {
		this.reviewIndex = reviewIndex;
	}

	public String getComment() {
		return comment;
	}

	public void setComment(String comment) {
		this.comment = comment;
	}

	public String getWriter() {
		return writer;
	}

	public void setWriter(String writer) {
		this.writer = writer;
	}

	public Date getRegisteredDate() {
		return registeredDate;
	}

	public void setRegisteredDate(Date registeredDate) {
		this.registeredDate = registeredDate;
	}

	@Override
	public String toString() {
		return "CommentVo [commentIndex=" + commentIndex + ", reviewIndex=" + reviewIndex + ", comment=" + comment
				+ ", writer=" + writer + ", registeredDate=" + registeredDate + "]";
	}
	



	
	
}
