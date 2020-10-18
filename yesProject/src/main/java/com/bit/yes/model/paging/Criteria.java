package com.bit.yes.model.paging;

public class Criteria {

	private int page; // current page
	private int perPageNum;

	public Criteria() {
		this.page = 1;
		this.perPageNum = 10;
	}

	public void setPage(int page) {
		if (page <= 0) {
			this.page = 1;
			return;
		}

		this.page = page;
	}

	public void setPerPageNum(int perPageNum) {

		this.perPageNum = perPageNum;

	}

	public int getPage() {
		return this.page;
	}

	public int getPageStart() { // 보여지는 게시물 중 첫 번째 게시물의 인덱스
		return (this.page - 1) * perPageNum;
	}

	public int getPerPageNum() {
		return this.perPageNum;
	}

	@Override
	public String toString() {
		return "Criteria [page=" + page + ", perPageNum=" + perPageNum + ", reviewIndex=" + "]";
	}

}
