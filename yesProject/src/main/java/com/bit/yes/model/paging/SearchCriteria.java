package com.bit.yes.model.paging;

import java.sql.Date;

public class SearchCriteria extends Criteria{
	
	private String searchType;
	private String keyword;
	private int reviewIndex;
	private String branchID;
	private Date beginDate;
	private Date endDate;
	private String writer;
	
	
	public String getWriter() {
		return writer;
	}
	public void setWriter(String writer) {
		this.writer = writer;
	}
	public String getSearchType() {
		return searchType;
	}
	public void setSearchType(String searchType) {
		this.searchType = searchType;
	}
	public String getKeyword() {
		return keyword;
	}
	public void setKeyword(String keyword) {
		this.keyword = keyword;
	}
	public int getReviewIndex() {
		return reviewIndex;
	}
	public void setReviewIndex(int reviewIndex) {
		this.reviewIndex = reviewIndex;
	}
	public String getBranchID() {
		return branchID;
	}
	
	public void setBranchID(String branchID) {
		this.branchID = branchID;
	}
	
	public Date getBeginDate() {
		return beginDate;
	}
	public void setBeginDate(Date beginDate) {
		this.beginDate = beginDate;
	}
	public Date getEndDate() {
		return endDate;
	}
	public void setEndDate(Date endDate) {
		this.endDate = endDate;
	}
	
	@Override
	public String toString() {
		return "SearchCriteria [searchType=" + searchType + ", keyword=" + keyword + ", reviewIndex=" + reviewIndex
				+ ", branchID=" + branchID + ", beginDate=" + beginDate + ", endDate=" + endDate + ", writer=" + writer
				+ "]";
	}
	


}
