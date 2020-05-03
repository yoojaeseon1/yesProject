package com.bit.yes.model.entity;

import java.sql.Date;

public class ReserveListVo {

	private int reserveIndex;
	private int reviewIndex;
	private String branchID;
	private String branchName;
	private String clientID;
	private String name;// 고객 이름
	private String reserveTime; // 예약한 시간
	private Date checkTime; // 예약했을 때 시간
	private int numPerson;
	private String request;
	private String useState;

	public ReserveListVo() {
		// TODO Auto-generated constructor stub
	}

	public ReserveListVo(String branchID, String branchName, String clientID, String name, String reserveTime,
			Date checkTime, int numPerson, String request, String useState, int reserveIndex) {
		super();
		this.reserveIndex = reserveIndex;
		this.branchID = branchID;
		this.branchName = branchName;
		this.clientID = clientID;
		this.name = name;
		this.reserveTime = reserveTime;
		this.checkTime = checkTime;
		this.numPerson = numPerson;
		this.request = request;
		this.useState = useState;

	}
	
	

	public int getReviewIndex() {
		return reviewIndex;
	}

	public void setReviewIndex(int reviewIndex) {
		this.reviewIndex = reviewIndex;
	}

	public int getReserveIndex() {
		return reserveIndex;
	}

	public void setReserveIndex(int reserveIndex) {
		this.reserveIndex = reserveIndex;
	}

	public String getBranchID() {
		return branchID;
	}

	public void setBranchID(String branchID) {
		this.branchID = branchID;
	}

	public String getBranchName() {
		return branchName;
	}

	public void setBranchName(String branchName) {
		this.branchName = branchName;
	}

	public String getClientID() {
		return clientID;
	}

	public void setClientID(String clientID) {
		this.clientID = clientID;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getReserveTime() {
		return reserveTime;
	}

	public void setReserveTime(String reserveTime) {
		this.reserveTime = reserveTime;
	}

	public Date getCheckTime() {
		return checkTime;
	}

	public void setCheckTime(Date checkTime) {
		this.checkTime = checkTime;
	}

	public int getNumPerson() {
		return numPerson;
	}

	public void setNumPerson(int numPerson) {
		this.numPerson = numPerson;
	}

	public String getRequest() {
		return request;
	}

	public void setRequest(String request) {
		this.request = request;
	}

	public String getUseState() {
		return useState;
	}

	public void setUseState(String useState) {
		this.useState = useState;
	}

	@Override
	public String toString() {
		return "ReserveListVo [reserveIndex=" + reserveIndex + ", reviewIndex=" + reviewIndex + ", branchID=" + branchID
				+ ", branchName=" + branchName + ", clientID=" + clientID + ", name=" + name + ", reserveTime="
				+ reserveTime + ", checkTime=" + checkTime + ", numPerson=" + numPerson + ", request=" + request
				+ ", useState=" + useState + "]";
	}


}
