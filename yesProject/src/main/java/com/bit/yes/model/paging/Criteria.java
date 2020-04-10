package com.bit.yes.model.paging;

public class Criteria {
	
	private int page; // current page
	private int perPageNum;
	private int reviewIndex;
	
	public Criteria() {
		this.page = 1;
		this.perPageNum = 10;
	}
	
	
	public void setPage(int page) {
		if(page <= 0) {
			this.page = 1;
			return;
		}
		
		this.page = page;
	}
	
	public void setPerPageNum(int perPageNum) {
		
		if(perPageNum <= 0 || perPageNum > 100) {
			this.perPageNum = 10;
			return;
		}
		
		this.perPageNum = perPageNum;
		
	}
	
	public int getPage() {
		return this.page;
	}
	
	public int getPageStart() { // 보여지는 페이지 번호들 중 첫 번째 번호
		return (this.page - 1) * perPageNum;
	}
	
	public int getPerPageNum() {
		return this.perPageNum;
	}
	
	public int getReviewIndex() {
		return reviewIndex;
	}

	public void setReviewIndex(int reviewIndex) {
		this.reviewIndex = reviewIndex;
	}


	@Override
	public String toString() {
		return "Criteria [page=" + page + ", perPageNum=" + perPageNum + ", reviewIndex=" + reviewIndex + "]";
	}

	
}