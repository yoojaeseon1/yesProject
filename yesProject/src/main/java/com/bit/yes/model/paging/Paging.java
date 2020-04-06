package com.bit.yes.model.paging;

public class Paging {

	int maxPost; //페이지당 표시될 게시물 최대 갯수 및 현재 게시물 갯수
	int firstPageNo; //첫번째 페이지 번호
	int prevPageNo; //이전 페이지 번호
	int startPageNo; //시작페이지
	int currentPageNo; //현재 페이지 번호
	int endPageNo; //끝 페이지
	int nextPageNo; //다음 페이지 번호
	int finalPageNo; //마지막 페이지 번호
	int numberOfRecords; //전체 레코드 수
	int sizeOfPage; //보여지는 페이지 갯수(1,2,3,4,5)
	
	public Paging(int currentPageNo, int maxPost) {
		this.currentPageNo = currentPageNo;
		this.sizeOfPage = 5; //기본 페이지를 5개로 표시함
		this.maxPost = (maxPost !=0) ? maxPost : 10;
		//게시물 최대 갯수가 0개가 아니라면 현재 게시물 갯수(maxPost)이고, 
		//만약 게시물수가 0개라면 10개 표현이다(10은 고정값)
	}

	public int getMaxPost() {
		return maxPost;
	}

	public void setMaxPost(int maxPost) {
		this.maxPost = maxPost;
	}

	public int getFirstPageNo() {
		return firstPageNo;
	}

	public void setFirstPageNo(int firstPageNo) {
		this.firstPageNo = firstPageNo;
	}

	public int getPrevPageNo() {
		return prevPageNo;
	}

	public void setPrevPageNo(int prevPageNo) {
		this.prevPageNo = prevPageNo;
	}

	public int getStartPageNo() {
		return startPageNo;
	}

	public void setStartPageNo(int startPageNo) {
		this.startPageNo = startPageNo;
	}

	public int getCurrentPageNo() {
		return currentPageNo;
	}

	public void setCurrentPageNo(int currentPageNo) {
		this.currentPageNo = currentPageNo;
	}

	public int getEndPageNo() {
		return endPageNo;
	}

	public void setEndPageNo(int endPageNo) {
		this.endPageNo = endPageNo;
	}

	public int getNextPageNo() {
		return nextPageNo;
	}

	public void setNextPageNo(int nextPageNo) {
		this.nextPageNo = nextPageNo;
	}

	public int getFinalPageNo() {
		return finalPageNo;
	}

	public void setFinalPageNo(int finalPageNo) {
		this.finalPageNo = finalPageNo;
	}

	public int getNumberOfRecords() {
		return numberOfRecords;
	}

	public void setNumberOfRecords(int numberOfRecords) {
		this.numberOfRecords = numberOfRecords;
	}

	public int getSizeOfPage() {
		return sizeOfPage;
	}

	public void setSizeOfPage(int sizeOfPage) {
		this.sizeOfPage = sizeOfPage;
	}
	// 페이징 생성
	public void makePaging() {
		if(numberOfRecords == 0)
			return;
		// 현재 페이지가 0일때
		if(currentPageNo == 0)
			setCurrentPageNo(1);
		
		// maxPost가 0이면 10으로 저장
		if(maxPost == 0)
			setMaxPost(10);
		
		int finalPage = (numberOfRecords + (maxPost -1)) / maxPost;

		if(currentPageNo > finalPage)
			setCurrentPageNo(finalPage);

		if(currentPageNo < 0)
			currentPageNo = 1;

		boolean isNowFirst = currentPageNo == 1 ? true : false;
		boolean isNowFinal = currentPageNo == finalPage ? true : false;

		int startPage = ((currentPageNo -1 ) / sizeOfPage) * sizeOfPage + 1;
		int endPage = startPage + sizeOfPage -1;

		if(endPage > finalPage)
			endPage = finalPage;
		
		setFirstPageNo(1);

		if(!isNowFirst)
			setPrevPageNo(((startPageNo -1) < 1 ? 1 : (startPage -1)));

		setStartPageNo(startPage);
		setEndPageNo(endPage);

		
		if(!isNowFinal)
			setNextPageNo(((endPage +1) > finalPage ? finalPage : (endPage +1)));

		setFinalPageNo(finalPage);
		
	}
	
	
	
	
	
}
